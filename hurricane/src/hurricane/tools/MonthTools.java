package hurricane.tools;

import java.time.Month;

public final class MonthTools
{
	public static Month parse(String month)
	{
		for(Month m : Month.values())
		{
			if(m.toString().toLowerCase().startsWith(month.toLowerCase()))
				return m;
		}
		throw new IllegalArgumentException("Not a month: " + month);
	}
	
	public static String asString(Month month)
	{
		return month.toString().substring(0,1) + month.toString().substring(1,3).toLowerCase(); 
	}
}
