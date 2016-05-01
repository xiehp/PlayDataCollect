package xie.subtitle.line;

import java.util.List;

public class XSubtitleLine {

	public XSubtitleLine(String textLine) {
		this.textLine = textLine;
	}

	/** 字幕里有效字幕的某一行文本行 */
	private String textLine;

	/** 字幕原始文件该字幕行的排序 */
	private int lineIndex;

	/** 字幕所在层级 */
	private String layer;

	/** 开始时间 */
	private long startTime;

	/** 结束时间 */
	private long endTime;

	private String style;

	private String name;

	private String marginL;

	private String marginR;

	private String marginV;

	private String effect;

	private List<String> textEffect;

	private String text;

	public String getTextLine() {
		return textLine;
	}

	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}

	public int getLineIndex() {
		return lineIndex;
	}

	public void setLineIndex(int lineIndex) {
		this.lineIndex = lineIndex;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarginL() {
		return marginL;
	}

	public void setMarginL(String marginL) {
		this.marginL = marginL;
	}

	public String getMarginR() {
		return marginR;
	}

	public void setMarginR(String marginR) {
		this.marginR = marginR;
	}

	public String getMarginV() {
		return marginV;
	}

	public void setMarginV(String marginV) {
		this.marginV = marginV;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public List<String> getTextEffect() {
		return textEffect;
	}

	public void setTextEffect(List<String> textEffect) {
		this.textEffect = textEffect;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
