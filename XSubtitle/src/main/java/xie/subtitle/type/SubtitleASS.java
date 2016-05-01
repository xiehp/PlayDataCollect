package xie.subtitle.type;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xie.common.date.XTimeUtils;
import xie.common.utils.XRegularUtils;
import xie.subtitle.line.LineASS;

public class SubtitleASS extends SubtitleBase {

	public static final String TIME_FORMAT = "HH:mm:ss.SS";
	public static final String REG_TEXT_CODE = "\\{.*?\\\\.*?\\}";

	Map<String, String> paramMap = new HashMap<>();

	/** 字幕行列表 */
	List<String> textLineList = new ArrayList<>();

	/** 字幕行的ASS格式化方式 */
	String[] textLineFormat;

	@Override
	protected void initData() {
		boolean sriptInfoFlg = false; // 是否进入Sript Info 部分
		// boolean AegisubProjectGarbage = false; // 是否进入Aegisub Project Garbage部分
		boolean V4StylesFlg = false; // 是否进入V4+ Styles部分

		boolean eventsFlg = false; // 是否进入Events部分

		List<String> lineList = getLineList();
		if (lineList == null) {
			return;
		}

		for (int i = 0; i < lineList.size(); i++) {
			String line = lineList.get(i);
			if (line == null) {
				continue;
			}
			line = line.trim();

			if (isSectionFlg(line)) {
				sriptInfoFlg = false;
				V4StylesFlg = false;
				eventsFlg = false;
				if (line.contains("Sript Info")) {
					sriptInfoFlg = true;
				} else if (line.contains("V4+ Styles")) {
					V4StylesFlg = true;
				} else if (line.contains("Events")) {
					eventsFlg = true;
				}
			}

			String[] keyValue = getTrimedKeyValue(line);
			if (keyValue != null) {
				String key = keyValue[0];
				String value = keyValue[1];
				paramMap.put(key, value);

				if (eventsFlg) {
					if ("Dialogue".equals(key) && isDialogue(value)) {
						textLineList.add(value);
					} else if (key.equals("Format")) {
						textLineFormat = value.split(",");
						for (int j = 0; j < textLineFormat.length; j++) {
							if (textLineFormat[j] != null) {
								textLineFormat[j] = textLineFormat[j].trim();
							} else {
								textLineFormat[j] = "";
							}
						}
					}
				} else if (sriptInfoFlg) {
					// TODO ASS sriptInfoFlg
				} else if (V4StylesFlg) {
					// TODO ASS V4StylesFlg
				}
			}
		}

		if (textLineFormat != null) {
			int lineIndex = -1;
			for (String testLine : textLineList) {
				lineIndex++;
				try {
					LineASS lineASS = new LineASS(testLine);
					lineASS.setLineIndex(lineIndex);
					boolean hasStartFlg = false;
					boolean hasEndFlg = false;
					boolean hasTextFlg = false;

					// 将字幕行分解成最大format的数量
					String[] textLineValues = testLine.split(",", textLineFormat.length);
					int lenth = textLineValues.length;
					for (int i = 0; i < textLineFormat.length; i++) {
						if (i >= lenth) {
							break;
						}

						String textLineValue = textLineValues[i].trim();
						if (textLineFormat[i].equals("Layer")) {
							lineASS.setLayer(textLineValue);
						} else if (textLineFormat[i].equals("Start")) {
							lineASS.setStartTime(XTimeUtils.parseFromTimeStr(textLineValue, TIME_FORMAT));
							hasStartFlg = true;
						} else if (textLineFormat[i].equals("End")) {
							lineASS.setEndTime(XTimeUtils.parseFromTimeStr(textLineValue, TIME_FORMAT));
							hasEndFlg = true;
						} else if (textLineFormat[i].equals("Style")) {
							lineASS.setStyle(textLineValue);
						} else if (textLineFormat[i].equals("Name")) {

						} else if (textLineFormat[i].equals("MarginL")) {

						} else if (textLineFormat[i].equals("MarginR")) {

						} else if (textLineFormat[i].equals("MarginV")) {

						} else if (textLineFormat[i].equals("Effect")) {

						} else if (textLineFormat[i].equals("Text")) {
							String text = textLineValue.replaceAll(REG_TEXT_CODE, "").trim();
							text = text.replace("\\n", " ");
							text = text.replace("\\N", " ");
							text = text.replace("\\h", " ");
							lineASS.setText(text);
							lineASS.setTextEffect(XRegularUtils.find(textLineValue, REG_TEXT_CODE));
							hasTextFlg = true;
						}
					}
					if (hasStartFlg && hasEndFlg && hasTextFlg) {
						subtitleList.add(lineASS);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(XRegularUtils.find("{\\blur4}{\\an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{asd\\blur4}{\\an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{asd\\blur4}  {an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{\\blur4}{\\pos(944.855,104.142)}不要", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{\\fs35\\bord0\\c&HDDB6AC&\\frz353.6\\pos(824.001,482)}心愛的來信", REG_TEXT_CODE));
		System.out.println("{\\fs35\\bord0\\c&HDDB6AC&\\frz353.6\\pos(824.001,482)}心愛的來信".replaceAll(REG_TEXT_CODE, ""));
		System.out.println("{\\blur4}{\\pos(944.855,104.142)}不要".replaceAll(REG_TEXT_CODE, ""));

		System.out.println("0,0:23:38.50,0:23:39.74,兔子第一季中文藍光,,0,0,0,,{\\blur4}{\\pos(944.855,104.142)}不要".split(",", 10)[9]);
	}

	private String[] getTrimedKeyValue(String line) {
		String[] result = null;
		int colonIndex = line.indexOf(":");
		if (colonIndex > 0) {
			result = new String[2];
			result[0] = line.substring(0, colonIndex).trim();
			result[1] = colonIndex + 1 >= line.length() ? "" : line.substring(colonIndex + 1).trim();
		}

		return result;
	}

	private boolean isSectionFlg(String trimedLine) {
		return trimedLine.matches("\\[.+\\]");
	}

	@Override
	public boolean isDialogue(String line) {
		return super.isDialogue(line);
	}
}
