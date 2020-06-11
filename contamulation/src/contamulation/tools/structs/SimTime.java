package contamulation.tools.structs;

public class SimTime implements Comparable<SimTime>
{
	int day;
	int hour;
	
	public SimTime(int day, int hour)
	{
		this.day = day;
		this.hour = hour;
	}
	
	public static SimTime fromString(String simTime)
	{
		final String[] dayhour = simTime.split("@");
		final int day  = intOf(dayhour[0]);
		final int hour = Integer.parseInt(dayhour[1]);
		if(day < 0 || day >= 7 || hour < 0 || hour >= 24)
			throw new IllegalArgumentException(simTime + 
				" is not a valid SimTime"); // TODO custom extensions
		return new SimTime(day, hour);
	}
	
	public static SimTime fromOrdinal(int ord)
	{
		return new SimTime(ord / 24, ord % 24);
	}
	
	public int ordinal()
	{
		return 24 * day + hour;
	}
	
	public String toString()
	{
		return String.format("%s@%02d", stringOf(day), hour);
	}
	
	private static String stringOf(int day)
	{
		switch(day)
		{
		case  0: return "Mon";
		case  1: return "Tue";
		case  2: return "Wed";
		case  3: return "Thu";
		case  4: return "Fri";
		case  5: return "Sat";
		case  6: return "Sun";
		default: return "???";
		}
	}
	
	private static int intOf(String day)
	{
		switch(day)
		{
		case "Mon": return  0;
		case "Tue": return  1;
		case "Wed": return  2;
		case "Thu": return  3;
		case "Fri": return  4;
		case "Sat": return  5;
		case "Sun": return  6;
		default   : return -1;
		}
	}

	@Override
	public int compareTo(SimTime other)
	{
		return ordinal() - other.ordinal();
	}

	public int getHour()
	{
		return hour;
	}

	public int getDay()
	{
		return day;
	}
}
