package contamulation.app.behavior;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.CsvReadParser;
import contamulation.tools.files.FileReadParser;

public class Job
{
	private String identifier;
	private String building;
	
	public Job(String identifier, String building)
	{
		this.identifier = identifier;
		this.building   = building;
	}

	public static List<Job> fromListFile(Path path)
	{
		List<Job> jobs = new ArrayList<>();
		FileReadParser in = null;
		if(path.toString().endsWith(".jobs.csv"))
			in = new CsvReadParser(path);
		if(path.toString().endsWith(".jobs.bin"))
			in = new BinReadParser(path);
		if(in == null)
			throw new InvalidPathException(path.toString(),
				"specified file is not a jobs file."); // TODO custom extensions
		while(in.hasNext())
		{
			jobs.add(new Job(in.read(String.class), in.read(String.class)));
		}
		return jobs;
	}
}
