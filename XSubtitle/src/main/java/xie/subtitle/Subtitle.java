package xie.subtitle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import xie.subtitle.line.XSubtitleLine;

public interface Subtitle {

	public static final String SUBTITLE_TYPE_ASS = "ASS";
	public static final String SUBTITLE_TYPE_SRT = "SRT";
	public static final String SUBTITLE_TYPE_LRC = "LRC";

	void readFile(File file) throws IOException;

	// void saveFile() throws IOException;
	//
	// void saveAs(File file, String subtitleType) throws IOException;
	
	void initPorcessInfo();
	
	int nextIndex();
	
	String nextLine();
	
	XSubtitleLine nextSubtitle();
	
	int getNextIndex();
	
	String getNextLine();
	
	XSubtitleLine getNextSubtitle();
	
	int getNowIndex();
	
	String getNowLine();
	
	XSubtitleLine getNowSubtitle();
	
	boolean isDialogue(String line);
	
	List<XSubtitleLine> getSubtitleLineList();
}
