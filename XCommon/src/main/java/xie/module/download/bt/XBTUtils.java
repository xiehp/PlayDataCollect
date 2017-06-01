package xie.module.download.bt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import xie.module.download.bt.ttorrent.XTTorrentClient;

/**
 * Created by xie on 2017/6/1.
 */
public class XBTUtils {

	public static XBTClient downloadWithTorrent(URL torrentUrl, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException, KeyManagementException, NoSuchProviderException {

		XBTClient xbtClient = new XTTorrentClient();
		xbtClient.prepare(torrentUrl, downloadPath);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(xbtClient.getFilenameList());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		xbtClient.start();

		return xbtClient;
	}

	public static XBTClient downloadWithTorrent(File torrentFile, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException {

		XBTClient xbtClient = new XTTorrentClient();
		xbtClient.prepare(torrentFile, downloadPath);
		xbtClient.start();

		return xbtClient;
	}
}
