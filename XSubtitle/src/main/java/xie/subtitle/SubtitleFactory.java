package xie.subtitle;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.subtitle.type.SubtitleASS;

public class SubtitleFactory {
	private static final Logger logger = LoggerFactory.getLogger(SubtitleFactory.class);

	public static Subtitle createSubtitle(File file) throws IOException {
		if (file == null) {
			logger.error("文件不能为null");
			return null;
		}

		String fileName = file.getName();
		Subtitle subtitle = null;
		if (fileName.toLowerCase().endsWith(".ass")) {
			subtitle = new SubtitleASS();
			subtitle.readFile(file);
		} else {
			logger.warn("不支持的文件名，无法创建字幕。file:{}", fileName);
		}
		
		return subtitle;
	}
}
