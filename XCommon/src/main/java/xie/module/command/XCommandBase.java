package xie.module.command;

import xie.module.command.option.XOption;

public abstract class XCommandBase<X extends XOption> implements XCommand<X> {

	@SuppressWarnings("unchecked")
	@Override
	public Process runCmd(String cmd, X... options) {
		// TODO Auto-generated method stub
		return null;
	}
}
