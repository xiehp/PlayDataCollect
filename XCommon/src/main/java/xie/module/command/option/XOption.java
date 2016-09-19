package xie.module.command.option;

/**
 * 命令的参数<br>
 * 
 */
public interface XOption {

	/**
	 * 获得命令前的符号
	 */
	String getSymbol();

	/**
	 * 获得参数的名字
	 */
	String getName();

	/**
	 * 获得参数的值
	 */
	String getValue();

	/**
	 * 获得参数的值
	 */
	String[] getValues();

	void setName(String name);

	void setValues(String[] values);

	void setSymbol(String symbol);
}
