package eleven.backend;

import eleven.common.Symbol;

public enum RegularSymbols implements Symbol
{
	HEARTS   ("♡"),
	SPADES   ("♠"),
	DIAMONDS ("♢"),
	CLUBS    ("♣"),
	;

	String disp;
	
	private RegularSymbols(String disp)
	{
		this.disp = disp;
	}
	
	@Override
	public String toString()
	{
		return disp;
	}
}
