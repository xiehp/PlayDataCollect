package xie.module.command;

import xie.module.command.option.XOption;
import xie.module.command.option.impl.XWindowsOption;

/**
 * 执行cmd命令
 */
public interface XCommand<X extends XOption> {

	Process runCmd(String cmd);

	@SuppressWarnings("unchecked")
	Process runCmd(String cmd, X... options);

	XOption createOption(String name);
}
