package xie.module.download;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.File;
import java.net.InetAddress;

/**
 * Created by xie on 2017/6/1.
 */
public class DownloadTest {
	public static void main(String[] args) throws Exception{
		// First, instantiate the Client object.
		Client client = new Client(
				// This is the interface the client will listen on (you might need something
				// else than localhost here).
				InetAddress.getLocalHost(),

				// Load the torrent from the torrent file and use the given
				// output directory. Partials downloads are automatically recovered.
				SharedTorrent.fromFile(
						new File("D:\\work\\workspace\\github\\AnimeShotSiteProject\\common\\XCommon\\src\\main\\java\\xie\\module\\download\\[comicat.org]【動漫國字幕組】★04月新番[Room Mate][08][720P][繁體][MP4].torrent"),
						new File("D:\\work\\workspace\\github\\AnimeShotSiteProject\\common\\XCommon\\src\\main\\java\\xie\\module\\download\\a")));

		// You can optionally set download/upload rate limits
		// in kB/second. Setting a limit to 0.0 disables rate
		// limits.
		client.setMaxDownloadRate(0.0);
		client.setMaxUploadRate(0.0);

		// At this point, can you either call download() to download the torrent and
		// stop immediately after...
		client.download();

		// Or call client.share(...) with a seed time in seconds:
		// client.share(3600);
		// Which would seed the torrent for an hour after the download is complete.

		// Downloading and seeding is done in background threads.
		// To wait for this process to finish, call:
		client.waitForCompletion();

		// At any time you can call client.stop() to interrupt the download.
	}
}
