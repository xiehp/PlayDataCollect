package xie.subtitle;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.common.string.XStringUtils;
import xie.subtitle.line.XSubtitleLine;
import xie.subtitle.type.SubtitleASS;

public class SubtitleFactory {
	private static final Logger logger = LoggerFactory.getLogger(SubtitleFactory.class);

	public static Subtitle createSubtitle(File file, String filterRemove, String filterInclude) throws IOException {

		if (file == null) {
			logger.error("文件不能为null");
			return null;
		}

		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getAbsolutePath());
			return null;
		}

		String fileName = file.getName();
		Subtitle subtitle = null;
		if (fileName.toLowerCase().endsWith(".ass")) {
			subtitle = new SubtitleASS();
			if (XStringUtils.isNotBlank(filterInclude)) {
				subtitle.setFilterInclude(filterInclude);
			}
			if (XStringUtils.isNotBlank(filterRemove)) {
				subtitle.setFilterRemove(filterRemove);
			}
			subtitle.readFile(file);
		} else {
			logger.warn("不支持的文件名，无法创建字幕。file:{}", fileName);
		}

		return subtitle;
	}

	public static Subtitle createSubtitle(File file) throws IOException {
		return createSubtitle(file, null, null);
	}

	public static void main(String[] args) throws IOException {
		Subtitle subtitle = SubtitleFactory.createSubtitle(new File("M:\\AnimeShotSite\\anime\\B\\白色相簿\\第1季\\字幕\\[SumiSora][White_Album][BDRip][01][1920x1080][x264_flac].big5.ass"),
				null, "Style=(OP)|(IN)");
//		Subtitle subtitle = SubtitleFactory.createSubtitle(new File("M:\\AnimeShotSite\\anime\\B\\白色相簿\\第1季\\字幕\\[SumiSora][White_Album][BDRip][01][1920x1080][x264_flac].big5.ass"),
//				"Style#(ED)|(IN)|(OP)", null);
		System.out.println(subtitle.getSubtitleLineList().size());
		for (XSubtitleLine xSubtitleLine : subtitle.getSubtitleLineList()) {
			System.out.println(xSubtitleLine.getTextLine());
		}
		for (XSubtitleLine xSubtitleLine : subtitle.getSubtitleLineList()) {
			//System.out.println(xSubtitleLine.getText());
		}
		
		
	}
}
