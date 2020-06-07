package eleven.backend;

import eleven.common.Value;

public enum RegularValues implements Value
{
	ACE       ("A" ),
	NUMBER_2  ("2" ),
	NUMBER_3  ("3" ),
	NUMBER_4  ("4" ),
	NUMBER_5  ("5" ),
	NUMBER_6  ("6" ),
	NUMBER_7  ("7" ),
	NUMBER_8  ("8" ),
	NUMBER_9  ("9" ),
	NUMBER_10 ("10"),
	JACK      ("J" ),
	QUEEN     ("Q" ),
	KING      ("K" ),
	;

	String disp;
	
	private RegularValues(String disp)
	{
		this.disp = disp;
	}
	
	@Override
	public String toString()
	{
		return disp;
	}

}
