package xie.module.command.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.module.command.XCommandBase;
import xie.module.command.XCommandFactory;
import xie.module.command.option.XOption;
import xie.module.command.option.impl.XWindowsOption;

public class XWindowsCommand extends XCommandBase<XWindowsOption> {
	final private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Process runCmd(String cmd) {
		Process process = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			process = runtime.exec("cmd /c " + cmd);
			// 将调用结果打印到控制台上
			InputStream in = process.getInputStream();
			InputStream errorIn = process.getErrorStream();

			new Thread(new Runnable() {
				public void run() {
					try {
						InputStreamReader inputStreamReader = new InputStreamReader(errorIn, "utf-8");
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							logger.error(line);
						}
					} catch (Exception e) {
						logger.error("进程错误流读取发生错误", e);
					}
				}
			}).start(); // 启动单独的线程来清空p.getInputStream()的缓冲区

			InputStreamReader inputStreamReader = new InputStreamReader(in, "gb2312");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				logger.info(line);
			}
			in.close();
			process.waitFor();
		} catch (Exception e) {
			logger.error("进程错误", e);
		}

		return process;
	}

	@Override
	public XOption createOption(String name) {
		return new XWindowsOption(name);
	}

	public void aaa() throws IOException {
		// 创建系统进程
		ProcessBuilder pb = new ProcessBuilder("tasklist /M");
		Process p = pb.start();
		BufferedReader out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream()), Charset.forName("GB2312")));
		BufferedReader err = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getErrorStream())));
		System.out.println("Window 系统进程列表");
		String ostr;

		while ((ostr = out.readLine()) != null)
			System.out.println(ostr);
		String estr = err.readLine();
		if (estr != null) {
			System.out.println("\nError Info");
			System.out.println(estr);
		}
	}

	public static void main(String[] args) throws IOException {
		XWindowsCommand xWindowsCommand = (XWindowsCommand) XCommandFactory.createInstance();
		// xWindowsCommand.runCmd("ping baidu.com");
		xWindowsCommand.runCmd("tasklist ");

		xWindowsCommand.runCmd(
				"ffmpeg -ss 1067 -t 5 -i \"E:\\AnimeShotSite\\anime\\C\\超时空要塞\\Δ\\[dmhy][Macross_Delta][25][x264_aac][GB_BIG5][1080P_mkv].mkv\" -s 394x216 -f gif -r 10 \"E:\\AnimeShotSite\\gif\\C\\超时空要塞\\Δ\\25\\超时空要塞Δ 第25集 17分47秒_5秒ggg.gif\" ");
		// xWindowsCommand.aaa();
	}

}
