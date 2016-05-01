package xie.subtitle.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import xie.subtitle.Subtitle;
import xie.subtitle.line.XSubtitleLine;
import xie.subtitle.type.SubtitleASS;

public class SubtitleTest {
	public static void main(String[] args) throws IOException {
		Subtitle subtitle = new SubtitleASS();
		File file = new File("F:\\AnimeShotSite\\anime\\2016\\高校舰队（青春波纹）\\第一季\\字幕\\[HaSub&Airota] Haifuri - 01 [1080].cht.ass");
		subtitle.readFile(file);

		
		List<XSubtitleLine>  list = subtitle.getSubtitleLineList();
		for  (XSubtitleLine subtitleLine : list) {
//			System.out.println(subtitleLine.getTextLine());
			System.out.println(subtitleLine.getStartTime());
			System.out.println(subtitleLine.getEndTime());
			System.out.println(subtitleLine.getTextEffect());
			System.out.println(subtitleLine.getText());
		}
	}
}
