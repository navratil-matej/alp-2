package contamulation.app.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompleteLogBuilder
{
	private List<HourlyLog> hourly;
	private List<DailyLog> daily;
	
	public CompleteLogBuilder()
	{
		hourly = new ArrayList<HourlyLog>();
		daily  = new ArrayList<DailyLog >();
	}
	
	public void pushHour(HourlyLog hourlyLog)
	{
		hourly.add(hourlyLog);
		if(hourly.size() % 24 == 0)
		{
			final Optional<DailyLog> prev = daily.isEmpty() ? Optional.empty() : Optional.of(getLastDaily());
			final HourlyLog[] last = new HourlyLog[24]; // TODO magic
			hourly.subList(hourly.size() - 24, hourly.size()).toArray(last);
			daily.add(new DailyLog(prev, last));
		}
	}

	public Optional<HourlyLog> maybeGetLastHourly()
	{
		return hourly.isEmpty() ? Optional.empty() : Optional.of(getLastHourly());
	}

	public HourlyLog getLastHourly()
	{
		return hourly.get(hourly.size() - 1);
	}

	public DailyLog getLastDaily()
	{
		return daily.get(daily.size() - 1);
	}
	
	public CompleteLog build()
	{
		List<DailyLog> copy = new ArrayList<>(daily);
		return new CompleteLog(copy);
	}
}
