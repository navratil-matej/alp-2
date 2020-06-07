package contamulation.tools.files;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import contamulation.tools.files.function.IoReadFunc;
import contamulation.tools.structs.SimTime;

public class BinReadParser implements FileReadParser
{
	private static Map<Class<?>, IoReadFunc<BinReadParser, ?>> readers = new HashMap<>();
	
	private DataInputStream file;
	
	public BinReadParser(Path path)
	{
		try
		{
			byte[] bytes = Files.readAllBytes(path);
			file = new DataInputStream(new ByteArrayInputStream(bytes));
		} catch (IOException e)
		{
			System.out.println("Faulty parser: " + path.toString());
		}
	}
	
	static
	{
		readers.put(Integer.class, (self) -> self.file.readInt());
		readers.put(Double.class, (self) -> self.file.readDouble());
		readers.put(String.class, (self) -> self.file.readUTF());
		readers.put(SimTime.class, (self) -> SimTime.fromString(self.file.readUTF()));
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
		try
		{
			return file.available() > 0;
		} catch (IOException e)
		{
			return false;
		}
	}
}
