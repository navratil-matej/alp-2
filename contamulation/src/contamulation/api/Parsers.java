package contamulation.api;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.BinWriteParser;
import contamulation.tools.files.CsvReadParser;
import contamulation.tools.files.CsvWriteParser;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public final class Parsers
{
	private static final Map<String, Function<Path, FileWriteParser>> writers;
	private static final Map<String, Function<Path, FileReadParser >> readers;
	
	static
	{
		writers = new HashMap<>();
		readers = new HashMap<>();
		registerFileType(".csv", CsvWriteParser::new, CsvReadParser::new);
		registerFileType(".bin", BinWriteParser::new, BinReadParser::new);
	}
	
//	public static FileWriteParser writerFor(Path path)
//	{
//		return writerFor(path.toString());
//	}
	
	public static void registerFileType(String extension,
		Function<Path, FileWriteParser> writerFactory,
		Function<Path, FileReadParser > readerFactory)
	{
		writers.put(extension, writerFactory);
		readers.put(extension, readerFactory);
	}

	public static FileWriteParser writerFor(Path path)
	{
		for(Entry<String, Function<Path, FileWriteParser>> kvp : writers.entrySet())
			if(path.toString().endsWith(kvp.getKey()))
				return kvp.getValue().apply(path);
		return null;
	}

	public static FileReadParser readerFor(Path path)
	{
		for(Entry<String, Function<Path, FileReadParser >> kvp : readers.entrySet())
			if(path.toString().endsWith(kvp.getKey()))
				return kvp.getValue().apply(path);
		return null;
	}
}
