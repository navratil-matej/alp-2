package contamulation.app.results;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class DailyLog
{
	private static final int DAYS_IN_WEEK = 7;
	private static final int HOURS_IN_DAY = 24;
	
	private int week;
	private int day;
	
	private HourlyLog[] hourly;
	
	public static final Comparator<DailyLog> BY_NEW_INFECTED =
		(a, b) -> -a.sumNewInfected() + b.sumNewInfected();
	public static final Comparator<DailyLog> BY_NEW_RECOVERED =
		(a, b) -> -a.sumNewRecovered() + b.sumNewRecovered();
	public static final Comparator<DailyLog> BY_GROWTH_RATE =
		(a, b) -> (int)(-a.growthRate() + b.growthRate());
	
	public DailyLog(Optional<DailyLog> prev, HourlyLog[] hourly)
	{
		if(hourly.length != HOURS_IN_DAY)
		{
			throw new IllegalArgumentException("Hourly log not containing 24 hours.");
		}
		this.hourly = hourly;
		
		if (prev.isPresent())
		{
			day = prev.get().getDay() + 1;
			week = prev.get().getWeek();
			if(day > DAYS_IN_WEEK)
			{
				day = 1;
				week++;
			}
		}
		else
		{
			day = 1;
			week = 0;
		}
	}
	
	public int getTotal()
	{
		return hourly[0].getTotal();
	}
	
	public int getSusceptible()
	{
		return hourly[HOURS_IN_DAY - 1].getSusceptible();
	}
	
	public int getInfected()
	{
		return hourly[HOURS_IN_DAY - 1].getInfected();
	}
	
	public int getRecovered()
	{
		return hourly[HOURS_IN_DAY - 1].getRecovered();
	}
	
	public int sumNewInfected()
	{
		return Arrays.stream(hourly).mapToInt(h -> h.getNewInfected()).sum();
	}
	
	public int sumNewRecovered()
	{
		return Arrays.stream(hourly).mapToInt(h -> h.getNewRecovered()).sum();
	}
	
	public double growthRate()
	{
		final double delta = (sumNewInfected() - sumNewRecovered()) * 1.0;
		final double prev = getInfected() - delta;
		return (prev + delta) / prev;
	}
	
	public int ordinal()
	{
		return 7 * week + day;
	}

	public int getWeek()
	{
		return week;
	}

	public int getDay()
	{
		return day;
	}

	public HourlyLog[] getHourly()
	{
		return hourly;
	}

	public void toParser(FileWriteParser writer)
	{
		// TODO write day log specific info
		for(HourlyLog h : hourly)
			h.toParser(writer);
	}

	public static DailyLog fromParser(FileReadParser reader, Optional<DailyLog> prev)
	{
		HourlyLog[] all = new HourlyLog[HOURS_IN_DAY];
		Optional<HourlyLog> prevHourly = Optional.empty();
		if(prev.isPresent())
			prevHourly = Optional.of(prev.get().getHourly()[HOURS_IN_DAY - 1]);
		for(int i = 0; i < HOURS_IN_DAY; i++)
		{
			all[i] = HourlyLog.fromParser(reader, prevHourly);
			prevHourly = Optional.of(all[i]);
		}
		return new DailyLog(prev, all);
	}

	@Override
	public String toString()
	{
		return String.format("w%02d d%d | %8d | %8d | %8d | %8d | %8d | %8.3f",
			week, day, getSusceptible(), getInfected(), getRecovered(),
			sumNewInfected(), sumNewRecovered(), growthRate());
	}
}
