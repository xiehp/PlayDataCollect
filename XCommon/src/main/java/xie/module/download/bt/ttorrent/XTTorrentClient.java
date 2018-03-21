package xie.module.download.bt.ttorrent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import xie.common.utils.string.XUrlUtils;
import xie.common.utils.utils.XWaitTime;
import xie.module.download.XDownloadUtils;
import xie.module.download.bt.XBTClient;

public class XTTorrentClient implements XBTClient {

	public class XClient extends Client {

		XTTorrentClient xtTorrentClient;

		XWaitTime waitTime = new XWaitTime(60000);

		public XClient(InetAddress address, SharedTorrent torrent, XTTorrentClient xtTorrentClient) throws UnknownHostException, IOException {
			super(address, torrent);
			this.xtTorrentClient = xtTorrentClient;
		}

		public XTTorrentClient getXTTorrentClient() {
			return xtTorrentClient;
		}

		public void printInfo() {
			super.info();
		}

		@Override
		public synchronized void info() {
			if (waitTime.isTimeout()) {
				super.info();
				waitTime.resetNowtime();
			}
		}
	}

	private XClient client;

	@Override
	public void prepare(URL torrentUrl, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException, KeyManagementException, NoSuchProviderException {
		String fileName = XUrlUtils.getFileName(torrentUrl);
		File torrentFile = new File(downloadPath, fileName);

		XDownloadUtils.download(torrentUrl, torrentFile, 1200 * 1000, 9000 * 1000);

		prepare(torrentFile, downloadPath);
	}

	@Override
	public void prepare(File torrentFile, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException {
		// First, instantiate the Client object.
		client = new XClient(
				// This is the interface the client will listen on (you might need something
				// else than localhost here).
				InetAddress.getLocalHost(),

				// Load the torrent from the torrent file and use the given
				// output directory. Partials downloads are automatically recovered.
				SharedTorrent.fromFile(
						torrentFile,
						downloadPath),

				this);

		// You can optionally set download/upload rate limits
		// in kB/second. Setting a limit to 0.0 disables rate
		// limits.
		client.setMaxDownloadRate(0.0);
		client.setMaxUploadRate(0.0);
	}

	@Override
	public void addListener(Consumer<XBTClient> c) {
		// You can track the progress of the download and the state of the torrent by registering an Observer on your Client instance. The observer is updated every time a piece of the download completes:
		client.addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object data) {
				XClient client = (XClient) observable;
				c.accept(client.getXTTorrentClient());
			}
		});
	}

	@Override
	public void start() {
		// At this point, can you either call download() to download the torrent and
		// stop immediately after...
		//client.download();
		client.share(1800);
	}

	@Override
	public void stop() {
		client.stop();
	}

	@Override
	public List<String> getFilenameList() {
		return client.getTorrent().getFilenames();
	}

	@Override
	public void printDownloadInfo() {
		client.info();
	}

	@Override
	public float getCompletion() {
		return client.getTorrent().getCompletion();
	}

	@Override
	public boolean isComplete() {
		return client.getTorrent().isComplete();
	}

	@Override
	public void waitForCompletion() {
		// Downloading and seeding is done in background threads.
		// To wait for this process to finish, call:
		client.waitForCompletion();
	}

}
