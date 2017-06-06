package xie.function.mkvtool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.common.io.XFileUtils;
import xie.common.utils.XPrintUtils;
import xie.module.command.XCommandFactory;
import xie.module.command.impl.XWindowsCommand;

public class MkvextractCmdUtils {

	private static Logger logger = LoggerFactory.getLogger(MkvextractCmdUtils.class);

	/**
	 * 将字幕文件提取出来<br>
	 * 文件名为：{{去除扩展名的文件名}}_track{{no}}_{{语言}}.{{编码类型}}<br>
	 * <br>
	 * mkvextract tracks "%%c" 2:"%%c_track3.ass" 3:"%%c_track4.ass" 4:"%%c_track5.ass"<br>
	 * <br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC).mkv -> <br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC)_track4_chi.ass<br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC)_track5_chi.ass<br>
	 * 
	 */
	public static void extract(String videoFile) {
		extract(new File(videoFile));
	}

	/**
	 * 将字幕文件提取出来<br>
	 * 文件名为：{{去除扩展名的文件名}}_track{{no}}_{{语言}}.{{编码类型}}<br>
	 * <br>
	 * mkvextract tracks "%%c" 2:"%%c_track3.ass" 3:"%%c_track4.ass" 4:"%%c_track5.ass"<br>
	 * <br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC).mkv -> <br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC)_track4_chi.ass<br>
	 * [AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC)_track5_chi.ass<br>
	 * 
	 */
	public static void extract(File videoFile) {
		List<String> list = getExtractCmdList(videoFile);

		XWindowsCommand xWindowsCommand = XCommandFactory.createWindowsInstance();

		for (String cmd : list) {
			logger.info("执行cmd命令：{}", cmd);
			List<String> consoleInfoList = xWindowsCommand.runCmdAndGetResult(cmd);
			XPrintUtils.infoList(logger, consoleInfoList, "Progress");
		}
	}

	public static List<String> getExtractCmdList(String videoFile) {
		return getExtractCmdList(new File(videoFile));
	}

	public static List<String> getExtractCmdList(File videoFile) {

		String videoNamePath = videoFile.getAbsolutePath();
		String videoPath = videoFile.getParentFile().getAbsolutePath();
		String videoName = videoFile.getName();

		String videoNameNoExt = XFileUtils.getNameRemoveExt(videoName);

		List<MkvTrackInfo> mkvInfoList = MkvinfoCmdUtils.getSubtitleInfo(videoFile);

		List<String> cmdList = new ArrayList<>();
		if (mkvInfoList.size() > 0) {
			String cmd = String.format("mkvextract tracks \"%s\" ", videoNamePath);
			for (MkvTrackInfo mkvTrackInfo : mkvInfoList) {
				// 生成字幕文件名 {{去除扩展名的文件名}}_track{{no}}_{{语言}}.{{编码类型}}
				String ext = "ass";
				if (mkvTrackInfo.getFileType() == null || !mkvTrackInfo.getFileType().toUpperCase().contains("ASS")) {
					// TODO 非ass类型的，先使用txt
					ext = "txt";
				}
				String subtitleName = String.format("%s_track%d_%s.%s", videoNameNoExt, mkvTrackInfo.getTrackNo(), mkvTrackInfo.getLang(), ext);

				// 生成字幕的完整路径+文件名
				File subtitleFile = new File(videoPath, subtitleName);

				// cmd + mkvId:"字幕完整路径"
				cmd = String.format(cmd + " %d:\"%s\" ", mkvTrackInfo.getMkvId(), subtitleFile.getAbsolutePath());
			}
			cmdList.add(cmd);
		} else {
			logger.info("{}没有字幕文件", videoNamePath);
		}

		return cmdList;
	}

	public static void main(String[] args) {
		XPrintUtils.printList(getExtractCmdList("M:\\AnimeShotSite\\anime\\J\\进击的巨人\\第二季\\自动下载资源\\[Kamigami] Attack on Titan S2 - 07 [1080p x265 Ma10p AAC].mkv"));
		XPrintUtils.printList(getExtractCmdList("M:\\AnimeShotSite\\anime\\M\\美少女战士\\crystal\\[AWS]Sailor Moon Crystal Ⅲ - 28(BD.1080p.FLAC).mkv"));
		extract("M:\\AnimeShotSite\\anime\\J\\进击的巨人\\第二季\\自动下载资源\\[Kamigami] Attack on Titan S2 - 07 [1080p x265 Ma10p AAC].mkv");
		extract("M:\\AnimeShotSite\\anime\\M\\美少女战士\\crystal\\[AWS]Sailor Moon Crystal Ⅲ - 28(BD.1080p.FLAC).mkv");

	}

}
