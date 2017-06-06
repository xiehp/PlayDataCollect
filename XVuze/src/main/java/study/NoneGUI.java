package study;

import java.io.File;

import org.gudy.azureus2.core3.config.COConfigurationManager;
import org.gudy.azureus2.core3.config.impl.TransferSpeedValidator;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.impl.DownloadManagerAdapter;
import org.gudy.azureus2.plugins.PluginManager;
import org.gudy.azureus2.plugins.PluginManagerDefaults;

import com.aelitis.azureus.core.AzureusCore;
import com.aelitis.azureus.core.AzureusCoreFactory;

public class NoneGUI {
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("az.factory.internat.bundle", "org.gudy.azureus2.ui.none.internat.MessagesBundle");

		COConfigurationManager.initialise();

		if (System.getProperty("azureus.low.resource.mode", "false").equals("true")) {

			System.out.println("Low resource mode enabled");

			COConfigurationManager.setParameter("Start In Low Resource Mode", true);
			COConfigurationManager.setParameter("DHT.protocol.version.min", 51);

			COConfigurationManager.setParameter(TransferSpeedValidator.AUTO_UPLOAD_ENABLED_CONFIGKEY, false);
			COConfigurationManager.setParameter(TransferSpeedValidator.AUTO_UPLOAD_SEEDING_ENABLED_CONFIGKEY, false);

			COConfigurationManager.setParameter("dht.net.cvs_v4.enable", false);
			COConfigurationManager.setParameter("dht.net.main_v6.enable", false);

			COConfigurationManager.setParameter("network.tcp.read.select.time", 500);
			COConfigurationManager.setParameter("network.tcp.read.select.min.time", 500);
			COConfigurationManager.setParameter("network.tcp.write.select.time", 500);
			COConfigurationManager.setParameter("network.tcp.write.select.min.time", 500);
			COConfigurationManager.setParameter("network.tcp.connect.select.time", 500);
			COConfigurationManager.setParameter("network.tcp.connect.select.min.time", 500);

			COConfigurationManager.setParameter("network.udp.poll.time", 100);

			COConfigurationManager.setParameter("network.utp.poll.time", 100);

			COConfigurationManager.setParameter("network.control.read.idle.time", 100);
			COConfigurationManager.setParameter("network.control.write.idle.time", 100);

			COConfigurationManager.setParameter("diskmanager.perf.cache.enable", true);
			COConfigurationManager.setParameter("diskmanager.perf.cache.size", 4);
			COConfigurationManager.setParameter("diskmanager.perf.cache.enable.read", false);

			COConfigurationManager.setParameter("peermanager.schedule.time", 500);

			PluginManagerDefaults defaults = PluginManager.getDefaults();

			defaults.setDefaultPluginEnabled(PluginManagerDefaults.PID_BUDDY, false);
			defaults.setDefaultPluginEnabled(PluginManagerDefaults.PID_SHARE_HOSTER, false);
			defaults.setDefaultPluginEnabled(PluginManagerDefaults.PID_RSS, false);
			defaults.setDefaultPluginEnabled(PluginManagerDefaults.PID_NET_STATUS, false);

		}

		String download_dir = System.getProperty("azureus.folder.download", "");

		if (download_dir.length() > 0) {

			File dir = new File(download_dir);

			dir.mkdirs();

			System.out.println("Download directory set to '" + dir + "'");

			COConfigurationManager.setParameter("Default save path", dir.getAbsolutePath());
		}

		String torrent_dir = System.getProperty("azureus.folder.torrent", "");

		if (torrent_dir.length() > 0) {

			File dir = new File(torrent_dir);

			dir.mkdirs();

			System.out.println("Torrent directory set to '" + dir + "'");

			COConfigurationManager.setParameter("Save Torrent Files", true);

			COConfigurationManager.setParameter("General_sDefaultTorrent_Directory", dir.getAbsolutePath());
		}

		AzureusCore core = AzureusCoreFactory.create();

		core.start();

		for(int i=0; i<5; i++) {
			System.out.println(core.getGlobalManager().getDownloadManagers().size());
			core.getGlobalManager().getDownloadManagers().forEach(dm -> {
				dm.addListener(new DownloadManagerAdapter() {
					@Override
					public void stateChanged(DownloadManager manager, int state) {
						System.out.print("stateChanged:" + state);
					}
				});
				System.out.println("getTorrentFileName: " + dm.getTorrentFileName());
				System.out.println("getStats().getPercentDoneExcludingDND: " + dm.getStats().getPercentDoneExcludingDND());
				System.out.println("getStats().getShareRatio: " + dm.getStats().getShareRatio());
				System.out.println();
			});
			
			Thread.sleep(1000);
		}

		
		
		core.stop();
	}
}
