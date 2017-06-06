package xie.module.command;

import java.util.List;

import xie.module.command.option.XOption;

/**
 * 执行cmd命令
 */
public interface XCommand<X extends XOption> {

	/**
	 * 执行cmd,返回运行的进程
	 * @param cmd
	 * @return Process
	 */
	Process runCmd(String cmd);

	/**
	 * 执行cmd,将控制台输出信息放入printInfoList，返回运行的进程
	 * @param cmd
	 * @param printInfoList
	 * @return Process
	 */
	Process runCmd(String cmd, List<String> printInfoList);

	/**
	 * 执行cmd,将控制台输出信息放入printInfoList，返回printInfoList
	 * @param cmd
	 * @return List
	 */
	List<String> runCmdAndGetResult(String cmd);

	@SuppressWarnings("unchecked")
	Process runCmd(String cmd, X... options);

	XOption createOption(String name);
}
