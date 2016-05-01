package xie.subtitle.type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xie.common.io.XFileWriter;
import xie.subtitle.Subtitle;
import xie.subtitle.line.XSubtitleLine;

public abstract class SubtitleBase implements Subtitle {

	private int index = -1;
	private List<String> lineList = null;
	protected List<XSubtitleLine> subtitleList = new ArrayList<>();

	public List<String> getLineList() {
		return lineList;
	}

	public void setLineList(List<String> lineList) {
		this.lineList = lineList;
	}

	@Override
	public void readFile(File file) throws IOException {
		List<String> lineList = XFileWriter.readList(file.getAbsolutePath());
		setLineList(lineList);
		index = 0;

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
		
	}
}
