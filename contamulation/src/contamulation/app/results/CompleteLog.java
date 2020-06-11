package contamulation.app.results;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import contamulation.api.Parsers;
import contamulation.api.SimSerializable;
import contamulation.app.simulation.SimulationConfig;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class CompleteLog implements SimSerializable<CompleteLog>
{
	private List<DailyLog>  dailyData;
	private List<HourlyLog> hourlyData;
	private List<DailyLog>  dailyView;
	private List<HourlyLog> hourlyView;
	
	public CompleteLog(List<DailyLog> log)
	{
		dailyData  = log;
		hourlyData = log.stream()
			.flatMap(i -> Arrays.stream(i.getHourly()))
			.collect(Collectors.toList());
		dailyView  = new ArrayList<>(dailyData );
		hourlyView = new ArrayList<>(hourlyData);
	}
	
	/**
	 * Mutably sorts the daily view.
	 */
	public void sortDaily(Comparator<DailyLog> comparator)
	{
		dailyView.sort(comparator);
	}

	/**
	 * Mutably sorts the hourly view.
	 */
	public void sortHourly(Comparator<HourlyLog> comparator)
	{
		hourlyView.sort(comparator);
	}
	
	public List<DailyLog> getDailyView()
	{
		return dailyView;
	}
	
	public List<HourlyLog> getHourlyView()
	{
		return hourlyView;
	}
	
	/**
	 * Restores original sort order.
	 */
	public void restore()
	{
		dailyView  = new ArrayList<>(dailyData );
		hourlyView = new ArrayList<>(hourlyData);
	}

	@Override
	public void write(SimulationConfig cfg)
	{
		Path path = cfg.getPath(SimulationConfig.RESULTS_FILE);
		FileWriteParser writer = Parsers.writerFor(path);
		for(DailyLog d : dailyData)
			d.toParser(writer);
	}

	@Override
	public void read(SimulationConfig cfg)
	{
		Path path = cfg.getPath(SimulationConfig.RESULTS_FILE);
		FileReadParser reader = Parsers.readerFor(path);
		Optional<DailyLog> prev = Optional.empty();
		while(reader.hasNext())
		{
			dailyData.add(DailyLog.fromParser(reader, prev));
			prev = Optional.of(dailyData.get(dailyData.size() - 1));
		}
	}
}
