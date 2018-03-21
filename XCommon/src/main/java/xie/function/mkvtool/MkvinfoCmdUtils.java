package xie.function.mkvtool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.common.utils.utils.XPrintUtils;
import xie.common.utils.utils.XRegularUtils;
import xie.module.command.XCommandFactory;
import xie.module.command.impl.XWindowsCommand;

public class MkvinfoCmdUtils {

	private static final Logger log = LoggerFactory.getLogger(MkvinfoCmdUtils.class);

	/**
	 * 获取视频文件信息中，字幕轨道的编号<br>
	 * 编号为mkvtool提取工具中用到的编号<br>
	 */
	public static List<MkvTrackInfo> getSubtitleNumber(String videoFilePath) {
		return getSubtitleInfo(new File(videoFilePath));
	}

	/**
	 * 获取视频文件信息中，字幕轨道的编号<br>
	 * 编号为mkvtool提取工具中用到的编号<br>
	 */
	public static List<MkvTrackInfo> getSubtitleInfo(File videoFile) {
		XWindowsCommand xWindowsCommand = XCommandFactory.createWindowsInstance();
		List<String> consoleInfoList = xWindowsCommand.runCmdAndGetResult("mkvinfo " + "\"" + videoFile.getAbsolutePath() + "\"");

		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add(0, "填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");
		consoleInfoList.add("填充数据");

		List<MkvTrackInfo> mkvInfoList = new ArrayList<>();
		for (int i = 0; i < consoleInfoList.size(); i++) {
			String line = consoleInfoList.get(i);

			// + 轨道类型：subtitles
			if (line != null && line.contains("轨道类型") && line.contains("subtitles")) {
				MkvTrackInfo mkvInfo = new MkvTrackInfo();
				mkvInfo.setType("subtitles");

				// 轨道编号：5 (用于 mkvmerge & mkvextract 的轨道 ID：4)
				// 提取用于 mkvmerge & mkvextract 的轨道 ID
				int index = i - 3;
				String result = find(videoFile, consoleInfoList, index, "轨道编号.*ID.*([0-9]+)", "用于 mkvmerge & mkvextract 的轨道 ID");
				if (result != null) {
					mkvInfo.setMkvId(Integer.valueOf(result));
				} else {
					log.error("没有找到ID，跳过该数据");
					continue;
				}

				// 提取轨道编号
				result = find(videoFile, consoleInfoList, index, "轨道编号.*([0-9]+).*ID.*", "轨道编号");
				if (result != null) {
					mkvInfo.setTrackNo(Integer.valueOf(result));
				}

				// 提取语言（ + 语言：chi）
				result = find(videoFile, consoleInfoList, index, "语言：(.*)\\s*", "语言");
				if (result != null) {
					mkvInfo.setLang(result);
				}

				// | + 名称：简体中文
				result = find(videoFile, consoleInfoList, index, "名称：(.*)\\s*", "名称");
				if (result != null) {
					mkvInfo.setLangName(result);
				}

				// | | + 编码格式 ID：S_TEXT/ASS
				result = find(videoFile, consoleInfoList, index, "编码格式 ID：(.*)\\s*", "编码格式");
				if (result != null) {
					mkvInfo.setFileType(result);
				}

				// | + 编码格式私有数据，长度 1699
				result = find(videoFile, consoleInfoList, index, "长度 ([0-9]+).*", "编码格式私有数据，长度");
				if (result != null) {
					mkvInfo.setSize(Long.valueOf(result));
				}

				if (mkvInfo.getLang() == null && "英文".equals(mkvInfo.getLangName())) {
					mkvInfo.setLang("eng");
				}

				mkvInfoList.add(mkvInfo);
			}
		}

		return mkvInfoList;
	}

	private static String find(File videoFile, List<String> consoleInfoList, int index, String reg, String targetName) {
		String result = null;
		for (int i = index; i < index + 15 && i < consoleInfoList.size(); i++) {
			result = XRegularUtils.findOnceAndFirstGroup(consoleInfoList.get(i), reg);
			if (result != null) {
				break;
			}
		}
		if (result == null) {
			log.error("未找到字幕类型track对应的[{}], 文件{}， 查找开始行:{}", targetName, videoFile.getAbsolutePath(), consoleInfoList.get(index));
		}
		return result;
	}

	public static void main(String[] args) {
		List<MkvTrackInfo> list = MkvinfoCmdUtils.getSubtitleNumber("M:\\AnimeShotSite\\anime\\J\\进击的巨人\\第二季\\自动下载资源\\[Kamigami] Attack on Titan S2 - 06 [1080p x265 Ma10p AAC].mkv");
		XPrintUtils.printList(list);
		list = MkvinfoCmdUtils.getSubtitleNumber("M:\\AnimeShotSite\\anime\\M\\美少女战士\\crystal\\[AWS]Sailor Moon Crystal Ⅲ - 27(BD.1080p.FLAC).mkv");
		XPrintUtils.printList(list);
	}
}
