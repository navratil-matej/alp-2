package contamulation.tools.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import contamulation.tools.files.function.IoWriteFunc;
import contamulation.tools.structs.SimTime;

/**
 * Comma separated value (.csv) wrapper for FileWriteParser.
 * @author des
 *
 */
public class CsvWriteParser implements FileWriteParser
{
	//not needed as I'm just calling tostring on everything
//	private static Map<Class<?>, IoWriteFunc<CsvWriteParser, ?>> writers = new HashMap<>();
	
	private List<String> lines;
	private Path path;
	
	public CsvWriteParser(Path path)
	{
		this.path = path;
		lines = new ArrayList<>();
		lines.add("");
	}
	
//	static
//	{
//		// TODO don't
//		writers.put(Integer.class, (self, i) -> self.lines.add(i.toString()));
//		writers.put(Double.class,  (self, i) -> self.lines.add(i.toString()));
//		writers.put(String.class,  (self, i) -> self.lines.add(i.toString()));
//		writers.put(SimTime.class, (self, i) -> self.lines.add(i.toString()));
//	}
	
	private void append(String str)
	{
		final int index = lines.size() - 1;
		final String prev = lines.get(index);
		if(prev != "")
			str = "," + str;
		lines.set(index, prev + str);
	}

	@Override
	public <T> boolean isSupported(Class<T> type)
	{
		return true;
//		return writers.containsKey(type);
	}

	@Override
	public <T> void write(Class<T> type, T item)
	{
		append(item.toString());
	}

	@Override
	public void writeToFile() throws IOException
	{
		Files.write(path, lines);
	}

	@Override
	public void nextEntry()
	{
		lines.add("");
	}
}
