package xie.subtitle.type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.common.date.DateUtil;
import xie.common.io.XFileWriter;
import xie.subtitle.Subtitle;
import xie.subtitle.line.XSubtitleLine;

public abstract class SubtitleBase implements Subtitle {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private int index = -1;
	/** 从文件中获取的每一行的文本 */
	private List<String> lineList = null;
	/** 经过分析获得的字幕行信息 */
	private List<XSubtitleLine> subtitleList = new ArrayList<>();

	private Map<String, List<XSubtitleLine>> checkDuplicateMap = new HashMap<String, List<XSubtitleLine>>();

	/** 包含某种属性文字 */
	private String filterInclude;

	/** 排除某种属性文字 */
	private String filterRemove;

	public List<String> getLineList() {
		return lineList;
	}

	public void setLineList(List<String> lineList) {
		this.lineList = lineList;
	}

	public String getFilterInclude() {
		return filterInclude;
	}

	public void setFilterInclude(String filterInclude) {
		this.filterInclude = filterInclude;
	}

	public String getFilterRemove() {
		return filterRemove;
	}

	public void setFilterRemove(String filterRemove) {
		this.filterRemove = filterRemove;
	}

	protected void addSubtitleLine(XSubtitleLine subtitleLine) {
		if (isNotAvalible(subtitleLine)) {
			logger.info("无效字幕行：" + DateUtil.formatTime(subtitleLine.getStartTime(), 2) + "," + DateUtil.formatTime(subtitleLine.getEndTime(), 2) + "," + subtitleLine.getText());
			return;
		}

		if (isDuplicate(subtitleLine)) {
			logger.info("重复字幕行：" + DateUtil.formatTime(subtitleLine.getStartTime(), 2) + "," + DateUtil.formatTime(subtitleLine.getEndTime(), 2) + "," + subtitleLine.getText());
			return;
		}

		subtitleList.add(subtitleLine);
	}

	/**
	 * 判断是否是无效字幕行<br>
	 * 1:字幕持续时间超过1秒的，认为有效<br>
	 * 2:根据字数不同，判断相差时间，在需要最少时间内， 则认为是无效的，每个字至少需要50毫秒<br>
	 * 
	 * @param subtitleLine
	 * @return
	 */
	private boolean isNotAvalible(XSubtitleLine subtitleLine) {
		if (subtitleLine == null || subtitleLine.getText() == null) {
			return true;
		}

		long absTime = Math.abs(subtitleLine.getStartTime() - subtitleLine.getEndTime());
		if (absTime > 1000) {
			// 超过1秒的，认为有效
			return false;
		}

		// 根据字数不同，判断相差时间，在需要最少时间内， 则认为是无效的
		String text = subtitleLine.getText() + "";
		text = text.replaceAll("[\\s\\pP\\p{Punct}\\p{Sc}]", "");
		long requiredMinTime = text.length() * 50; // 每个字至少需要50毫秒
		if (absTime < requiredMinTime) {
			return true;
		}

		return false;
	}

	private boolean isDuplicate(XSubtitleLine subtitleLine) {
		String text = subtitleLine.getText();
		if (text == null) {
			return false;
		}

		text = text.trim();

		List<XSubtitleLine> list = checkDuplicateMap.get(text);
		if (list == null) {
			list = new ArrayList<XSubtitleLine>();
			list.add(subtitleLine);
			checkDuplicateMap.put(text, list);
			return false;
		} else {
			boolean duplicateFlg = false;
			for (XSubtitleLine subtitleLine2 : list) {
				if (isDuplicate(subtitleLine, subtitleLine2)) {
					duplicateFlg = true;
					break;
				}
			}
			list.add(subtitleLine);
			return duplicateFlg;
		}
	}

	private boolean isDuplicate(XSubtitleLine subtitleLine1, XSubtitleLine subtitleLine2) {
		// 同时为null或者地址相同
		if (subtitleLine1 == subtitleLine2) {
			return true;
		}

		String text1 = subtitleLine1.getText();
		String text2 = subtitleLine2.getText();

		if (text1 == null || text2 == null) {
			// null认为不相同
			return false;
		}

		text1 = text1.trim();
		text2 = text2.trim();

		// 文本不同， 则不重复
		if (!text1.equals(text2)) {
			return false;
		}

		// 文本相同，时间完全相同，认为重复
		if (subtitleLine1.getStartTime() == subtitleLine2.getStartTime() && subtitleLine1.getEndTime() == subtitleLine2.getEndTime()) {
			return true;
		}

		// 文本相同，时间不完全相同的情况下
		{
			// 根据字数不同，判断相差时间，在需要最少时间内， 则认为重复
			long requiredMinTime = text1.length() * 100; // 每个字至少需要100毫秒
			if (Math.abs(subtitleLine2.getStartTime() - subtitleLine1.getStartTime()) < requiredMinTime) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void readFile(File file) throws IOException {
		List<String> lineList = XFileWriter.readList(file.getAbsolutePath());
		setLineList(lineList);
		index = 0;

		if (lineList == null) {
			logger.error("文件不存在，" + file.getAbsolutePath());
			return;
		}
		if (lineList.size() < 10) {
			logger.warn("文件行数过少，当前行数，" + lineList.size() + ", 文件：" + file.getAbsolutePath());
			return;
		}

		logger.info("文件读取成功，总行数：{}", lineList.size());
		initData();
	}

	protected abstract void initData();

	@Override
	public List<XSubtitleLine> getSubtitleLineList() {
		return subtitleList;
	}

	@Override
	public void initPorcessInfo() {
		index = 0;
	}

	@Override
	public int nextIndex() {
		if (index == -1) {
			return -1;
		}

		index++;
		if (index >= lineList.size()) {
			index = -1;
		}

		return index;
	}

	@Override
	public String nextLine() {
		nextIndex();
		if (index == -1) {
			return null;
		}
		return lineList.get(index);
	}

	@Override
	public XSubtitleLine nextSubtitle() {
		String line = null;
		while (true) {
			line = nextLine();
			if (line == null) {
				break;
			}

			if (isDialogue(line)) {
				return new XSubtitleLine(line);
			}
		}
		return null;
	}

	@Override
	public int getNextIndex() {
		if (index == -1) {
			return -1;
		}

		int nextIndex = index + 1;
		if (nextIndex >= lineList.size()) {
			nextIndex = -1;
		}

		return nextIndex;
	}

	@Override
	public String getNextLine() {
		getNextIndex();
		if (index == -1) {
			return null;
		}
		return lineList.get(index);
	}

	@Override
	public XSubtitleLine getNextSubtitle() {
		String line = null;
		int nextIndex = index;
		while (true) {
			nextIndex++;
			if (nextIndex >= lineList.size()) {
				break;
			}

			line = lineList.get(nextIndex);
			if (isDialogue(line)) {
				return new XSubtitleLine(line);
			}
		}

		return null;
	}

	@Override
	public int getNowIndex() {
		if (index >= lineList.size()) {
			index = -1;
		}
		return index;
	}

	@Override
	public String getNowLine() {
		int nowIndex = getNowIndex();
		if (nowIndex > -1) {
			return lineList.get(nowIndex);
		} else {
			return null;
		}
	}

	@Override
	public XSubtitleLine getNowSubtitle() {
		String line = getNowLine();
		if (line == null) {
			return null;
		}

		if (isDialogue(line)) {
			return new XSubtitleLine(line);
		}

		return null;
	}

	@Override
	public boolean isDialogue(String line) {
		if (line == null) {
			return false;
		}
		return line.matches(".*[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}.*");
	}

	public static void main(String[] args) {
		SubtitleASS subtitleASS = new SubtitleASS();
		System.out.println(subtitleASS.isDialogue("asd0:21:22asd"));
		System.out.println(subtitleASS.isDialogue("asdas 10:21:22 asd"));
		System.out.println(subtitleASS.isDialogue("0:2:22"));
		System.out.println(subtitleASS.isDialogue("as"));

		String str = "!!！？？!!!!%*）%￥！KTV去	符号  标号！  ！当然,，。!!..**半角";
		System.out.println(str);
		String str1 = str.replaceAll("[\\pP\\p{Punct}]", "");
		System.out.println("str1:" + str1);

		String str2 = str.replaceAll("[//pP]", "");
		System.out.println("str2:" + str2);

		String str3 = str.replaceAll("[//p{P}]", "");
		System.out.println("str3:" + str3);

		String str4 = str.replaceAll("[\\p{Punct}]", "");
		System.out.println("str4:" + str4);

		String str5 = str.replaceAll("[\\s\\pP\\p{Punct}\\p{Sc}]", "");
		System.out.println("str5:" + str5);

	}
}
