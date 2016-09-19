package xie.module.command.option;

public class XOptionBase implements XOption {

	private String symbol;
	private String name;
	private String[] values;

	public XOptionBase(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getValues() {
		return values;
	}

	@Override
	public void setValues(String[] values) {
		this.values = values;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
