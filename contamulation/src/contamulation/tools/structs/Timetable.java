package contamulation.tools.structs;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import contamulation.api.Parsers;
import contamulation.tools.ArrayTools;
import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.BinWriteParser;
import contamulation.tools.files.CsvReadParser;
import contamulation.tools.files.CsvWriteParser;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class Timetable<T>
{
	private List<Double> times;
	private List<T>      items;
	
	public Timetable()
	{
		times = new ArrayList<>();
		items = new ArrayList<>();
	}
	
	public Timetable(List<SimTime> times, List<T> items)
	{
		// Encoded as doubles for convenient binary less-equal search
		this.times = times.stream()
			.mapToDouble(st -> (double) st.ordinal())
			.boxed().collect(Collectors.toList());
		this.items = items;
	}
	
	public static <R> Timetable<R> fromFile(Class<R> type, Path path)
	{
		List<SimTime> times = new ArrayList<>();
		List<R>       items = new ArrayList<>();
		FileReadParser in = Parsers.readerFor(path);
		if(in == null)
			throw new InvalidPathException(path.toString(),
				"specified file is not a timetable file.");
		while(in.hasNext())
		{
			times.add(in.read(SimTime.class));
			items.add(in.read(type));
		}
		return new Timetable<R>(times, items);
	}
	
	/**
	 * Write the timetable to a file. Uses convenience parser classes.
	 * @param type the type that y elements of the curve take, for runtime use.
	 * @param path The path to load the curve from.
	 */
	public void toFile(Class<T> type, Path path)
	{
		FileWriteParser out = Parsers.writerFor(path);
		if(out == null)
			throw new InvalidPathException(path.toString(),
				"specified file is not a curve file.");
		for(Kvp<SimTime, T> kvp : list())
		{
			out.write(SimTime.class, kvp.key);
			out.write(type, kvp.val);
		}
	}
	
	private List<Kvp<SimTime, T>> list()
	{
		List<Kvp<SimTime, T>> list = new ArrayList<>();
		for(int i = 0; i < times.size(); i++)
			list.add(new Kvp<>(SimTime.fromOrdinal((int) (double) times.get(i)), items.get(i)));
		return list;
			
	}

	public T get(SimTime time)
	{
		final int i = ArrayTools.binsearchLessEqual(times, time.ordinal());
		return items.get(i);
	}
}