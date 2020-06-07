package hurricane.app;

import java.time.Month;
import java.time.YearMonth;

import hurricane.tools.MonthTools;

public class Hurricane
{
	private int year;
	private Month month;
	private int pressure;
	private int velocity;
	private String name;
	
	public Hurricane(int year, Month month, int pressure, int velocity, String name)
	{
		super();
		this.year = year;
		this.month = month;
		this.pressure = pressure;
		this.velocity = velocity;
		this.name = name;
	}

	public int year()
	{
		return year;
	}

	public Month month()
	{
		return month;
	}
	
	public YearMonth yearMonth()
	{
		return YearMonth.of(year, month);
	}
	
	public int category()
	{
		int kph = velocityKph();
		if(kph <= 153)
			return 1;
		if(kph <= 177)
			return 2;
		if(kph <= 208)
			return 3;
		if(kph <= 251)
			return 4;
		return 5;
	}

	public int pressure()
	{
		return pressure;
	}

	public int velocity()
	{
		return velocity;
	}

	public int velocityKph()
	{
		return (int)(velocity * 1.852);
	}

	public String name()
	{
		return name;
	}

	public String formatted()
	{
		return String.format("%4d %s %4d %4d %s", year, MonthTools.asString(month),
			pressure, velocity, name);
	}
}
