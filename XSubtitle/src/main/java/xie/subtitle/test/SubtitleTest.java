package xie.subtitle.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import xie.common.utils.date.DateUtil;
import xie.subtitle.Subtitle;
import xie.subtitle.line.XSubtitleLine;
import xie.subtitle.type.SubtitleASS;

public class SubtitleTest {
	public static void main(String[] args) throws IOException {
		Subtitle subtitle = new SubtitleASS();
//		File file = new File("F:\\AnimeShotSite\\anime\\2015\\心灵想要大声呼喊。\\第二季\\资源\\内挂字幕\\[Kamigami] Kyoukai no Rinne 2 - 01 [1920x1080 x265 Ma10p AAC (Chs,Cht,Jpn)]_track3_chi.ass");
		File file = new File("F:\\AnimeShotSite\\anime\\2015\\心灵想要大声呼喊。\\字幕\\[Kamigami] Kokoro ga Sakebitagatterunda - Movie [BD 1920x1080 HEVC-yuv420p10 FLAC].Kamigami-chs.ass");
		subtitle.readFile(file);

		List<XSubtitleLine> list = subtitle.getSubtitleLineList();
		for (XSubtitleLine subtitleLine : list) {
			// System.out.println(subtitleLine.getTextLine());
			System.out.println(DateUtil.formatTime(subtitleLine.getStartTime(), 2) + ", " + DateUtil.formatTime(subtitleLine.getEndTime(), 2) + ", " + subtitleLine.getText() + ", " + subtitleLine.getTextEffect());
		}
	}
}
