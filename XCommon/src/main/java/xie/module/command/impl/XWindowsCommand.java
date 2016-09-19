package xie.module.command.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import xie.module.command.XCommandBase;
import xie.module.command.XCommandFactory;
import xie.module.command.option.XOption;
import xie.module.command.option.impl.XWindowsOption;

public class XWindowsCommand extends XCommandBase<XWindowsOption> {

	@Override
	public Process runCmd(String cmd) {
		Process process = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			process = runtime.exec(cmd);
			// 将调用结果打印到控制台上
			InputStream in = process.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.defaultCharset());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			process.waitFor();
		} catch (Exception e) {
			System.out.println("Error!");
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

		// xWindowsCommand.aaa();
	}

}
