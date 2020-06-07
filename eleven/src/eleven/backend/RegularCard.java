package eleven.backend;

import eleven.common.Card;

public class RegularCard implements Card<RegularValues, RegularSymbols>
{
	private RegularValues  value;
	private RegularSymbols symbol;

	public RegularCard(RegularValues value, RegularSymbols symbol)
	{
		super();
		this.value = value;
		this.symbol = symbol;
	}

	@Override
	public RegularValues value()
	{
		return value;
	}

	@Override
	public RegularSymbols getSymbol()
	{
		return symbol;
	}

}
