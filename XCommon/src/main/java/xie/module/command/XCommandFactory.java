package xie.module.command;

import xie.module.command.impl.XWindowsCommand;
import xie.module.command.option.XOption;
import xie.module.command.option.impl.XWindowsOption;

public class XCommandFactory {
	public static XCommand<?> createInstance() {
		// TODO 根据环境创建cmd实例
		return new XWindowsCommand();
	}

	public static XWindowsCommand createWindowsInstance() {
		return new XWindowsCommand();
	}

	public static XOption createOption(String name) {
		return new XWindowsOption(name);
	}
}
