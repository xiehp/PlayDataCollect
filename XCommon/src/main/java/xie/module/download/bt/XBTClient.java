package xie.module.download.bt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.function.Consumer;

public interface XBTClient {

	/** 准备一个bt下载 */
	void prepare(URL torrentUrl, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException, KeyManagementException, NoSuchProviderException;

	/** 准备一个bt下载 */
	void prepare(File torrentFile, File downloadPath) throws UnknownHostException, NoSuchAlgorithmException, IOException;

	/** 增加一个监视 */
	void addListener(Consumer<XBTClient> c);

	/** 开始下载 */
	void start();

	/** 停止 */
	void stop();

	/** 获得文件列表 */
	List<String> getFilenameList();

	/** 打印下载基本信息 */
	void printDownloadInfo();

	/** 获得下载进度 */
	float getCompletion();

	boolean isComplete();

	/** 让进程处于等待完成状态 */
	void waitForCompletion();
}
