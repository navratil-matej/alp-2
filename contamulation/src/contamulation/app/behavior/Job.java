package contamulation.app.behavior;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import contamulation.api.Parsers;
import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.CsvReadParser;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class Job
{
	private String identifier;
	private String architecture;
	private double contact;
	
	public Job(String identifier, String architecture, double contact)
	{
		this.identifier   = identifier  ;
		this.architecture = architecture;
		this.contact      = contact     ;
	}

//	public static List<Job> fromListFile(Path path)
//	{
//		List<Job> jobs = new ArrayList<>();
//		FileReadParser in = Parsers.readerFor(path);
//		if(in == null)
//			throw new InvalidPathException(path.toString(),
//				"specified file is not a jobs file."); // TODO custom extensions
//		while(in.hasNext())
//		{
//			jobs.add();
//		}
//		return jobs;
//	}

	public String getIdentifier()
	{
		return identifier;
	}

	public String getArchitecture()
	{
		return architecture;
	}

	public double getContact()
	{
		return contact;
	}

	public static Job fromParser(FileReadParser reader)
	{
		return new Job(
			reader.read(String.class),
			reader.read(String.class),
			reader.read(Double.class));
	}

	public void toParser(FileWriteParser writer)
	{
		writer.write(String.class, identifier);
		writer.write(String.class, architecture);
		writer.write(Double.class, contact);
	}
}
