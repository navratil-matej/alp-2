package contamulation.app.results;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;
import contamulation.tools.structs.SimTime;

public class HourlyLog implements Comparable<HourlyLog>
{
	private static final int HOURS_IN_WEEK = 168;

	public static final Comparator<HourlyLog> BY_NEW_CASES = compareInt(HourlyLog::getNewInfected);
	public static final Comparator<HourlyLog> BY_NEW_RECOVERED = compareInt(HourlyLog::getNewRecovered);

	private int week;
	private SimTime time;

	private int susceptible;
	private int infected;
	private int recovered;

	private int newInfected;
	private int newRecovered;

	public HourlyLog(Optional<HourlyLog> prev, int sus, int inf, int rec)
	{
		susceptible = sus;
		infected = inf;
		recovered = rec;
		if (prev.isPresent())
		{
			newRecovered = rec - prev.get().getRecovered();
			newInfected = inf - prev.get().getInfected() + newRecovered;
		}
		else
		{
			newInfected = inf;
			newRecovered = rec;
		}
	}

	public int ordinal()
	{
		return HOURS_IN_WEEK * week + ordinal();
	}

	@Override
	public int compareTo(HourlyLog other)
	{
		return Integer.compare(ordinal(), other.ordinal());
	}

	private static Comparator<HourlyLog> compareInt(Function<HourlyLog, Integer> getter)
	{
		return (a, b) -> getter.apply(a) - getter.apply(b);
	}

	public int getWeek()
	{
		return week;
	}

	public SimTime getTime()
	{
		return time;
	}

	public int getSusceptible()
	{
		return susceptible;
	}

	public int getInfected()
	{
		return infected;
	}

	public int getRecovered()
	{
		return recovered;
	}

	public int getNewInfected()
	{
		return newInfected;
	}

	public int getNewRecovered()
	{
		return newRecovered;
	}

	
	public int getTotal()
	{
		return getInfected() + getSusceptible() + getRecovered();
	}

	public void toParser(FileWriteParser writer)
	{
		writer.write(Integer.class, susceptible);
		writer.write(Integer.class, infected);
		writer.write(Integer.class, recovered);
	}

	public static HourlyLog fromParser(FileReadParser reader, Optional<HourlyLog> prev)
	{
		int susceptible = reader.read(Integer.class);
		int infected    = reader.read(Integer.class);
		int recovered   = reader.read(Integer.class);
		return new HourlyLog(prev, susceptible, infected, recovered); 
	}

}
