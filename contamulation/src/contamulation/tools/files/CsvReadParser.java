package contamulation.tools.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import contamulation.app.behavior.Architecture;
import contamulation.app.behavior.Behavior;
import contamulation.app.behavior.Job;
import contamulation.app.simulation.Registry;
import contamulation.app.simulation.Simulation;
import contamulation.tools.structs.SimTime;

/**
 * Comma separated value (.csv) wrapper for FileReadParser.
 * @author des
 *
 */
public class CsvReadParser implements FileReadParser
{
	private static Map<Class<?>, Function<CsvReadParser, ?>> readers = new HashMap<>();
	
	private String[] file;
	private String[] line;
	private int fileIndex;
	private int lineIndex;
	
	public CsvReadParser(Path path)
	{
		try
		{
			List<String> lines = Files.readAllLines(path);
			file = new String[lines.size()];
			lines.toArray(file);
			line = file[0].split(",");
			fileIndex = 0;
			lineIndex = 0;
		} catch (IOException e)
		{
			System.out.println("Faulty parser: " + path.toString());
		}
	}
	
	static
	{
		readers.put(Integer.class, (self) -> Integer.parseInt(self.readNext()));
		readers.put(Double .class, (self) -> Double.parseDouble(self.readNext()));
		readers.put(String .class, CsvReadParser::readNext);
		readers.put(SimTime.class, (self) -> SimTime.fromString(self.readNext()));

		final Registry reg = Simulation.INSTANCE.registry();
		readers.put(Architecture.class, (self) -> reg.getArch    (self.readNext()));
		readers.put(Job         .class, (self) -> reg.getJob     (self.readNext()));
		readers.put(Behavior    .class, (self) -> reg.getBehavior(self.readNext()));
	}
	
	private String readNext()
	{
		String next = line[lineIndex];
		lineIndex++;
		if(lineIndex >= line.length)
		{
			fileIndex++;
			lineIndex = 0;
			if(fileIndex < file.length)
			{
				line = file[fileIndex].split(",");
			}
		}
		return next; 
	}

	@Override
	public <T> boolean isSupported(Class<T> type)
	{
		return readers.containsKey(type);
	}

	@Override
	public <T> T read(Class<T> type)
	{
		return type.cast(readers.get(type).apply(this));
	}
	
	@Override
	public boolean hasNext()
	{
		return fileIndex < file.length;
	}
}
