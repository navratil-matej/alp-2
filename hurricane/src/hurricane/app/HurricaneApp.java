package hurricane.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hurricane.tools.MonthTools;

public class HurricaneApp
{
	private static List<Hurricane> dataset;
	private static List<Hurricane> working;
	
	public static List<Hurricane> parse(List<String> lines, String separator)
	{
		List<Hurricane> hurricanes = new ArrayList<>();
		String[] values;
		for(String line : lines)
		{
			values = line.split(separator);
			hurricanes.add(new Hurricane(
				Integer.parseInt(values[0]),
				MonthTools.parse(values[1]),
				Integer.parseInt(values[2]),
				Integer.parseInt(values[3]),
				values[4]
				));
		}
		return hurricanes;
	}

	public static List<Hurricane> getDataset()
	{
		return dataset;
	}

	public static void setDataset(List<Hurricane> dataset)
	{
		HurricaneApp.dataset = dataset;
	}

	public static List<Hurricane> getWorking()
	{
		return working;
	}

	public static void restore()
	{
		// shallow clone as Hurricane is immutable.
		working = new ArrayList<>(dataset);
	}

	public static void filter(Predicate<Hurricane> filter)
	{
		working = working.stream().filter(filter).collect(Collectors.toList());
	}

	public static void sort(Comparator<Hurricane> comparator)
	{
		working.sort(comparator);
	}

	public static void reverse()
	{
		Collections.reverse(working);
	}
}
