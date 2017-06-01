package xie.module.download;

import java.io.File;
import java.net.URL;
import java.util.function.Consumer;

import xie.module.download.bt.XBTClient;
import xie.module.download.bt.XBTUtils;

/**
 * Created by xie on 2017/6/1.
 */
public class DownloadTest {
	public static void main(String[] args) throws Exception {

//		XBTClient xbtClient = XBTUtils.downloadWithTorrent(
//				new File("D:\\work\\temp\\bttest\\[comicat.org]【動漫國字幕組】★04月新番[Room Mate][08][720P][繁體][MP4].torrent"),
//				new File("D:\\work\\temp\\bttest"));
//
//		xbtClient.addListener(new Consumer<XBTClient>() {
//			@Override
//			public void accept(XBTClient t) {
//				System.out.println("Consumer:" + t.getCompletion());
//			}
//		});
		
		URL url = new URL("https://cache.kamigami.org/wp-content/uploads/2017/04/Kamigami-Attack-on-Titan-S2-08-1080p-x265-Ma10p-AAC.mkv.torrent");
//		File url = new File("D:\\work\\temp\\bttest\\[comicat.org]【動漫國字幕組】★04月新番[Room Mate][08][720P][繁體][MP4].torrent");
		XBTClient xbtClient = XBTUtils.downloadWithTorrent(
				url,
				new File("D:\\work\\temp\\bttest"));

		xbtClient.addListener(new Consumer<XBTClient>() {
			@Override
			public void accept(XBTClient t) {
				System.out.println("Consumer:" + t.getCompletion());
			}
		});
		
	}
}
