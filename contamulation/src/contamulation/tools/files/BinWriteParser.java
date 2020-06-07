package contamulation.tools.files;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import contamulation.tools.files.function.IoWriteFunc;
import contamulation.tools.structs.SimTime;

public class BinWriteParser implements FileWriteParser
{
	private static Map<Class<?>, IoWriteFunc<BinWriteParser, ?>> writers = new HashMap<>();
	
	private Path path;
	private DataOutputStream file;
	private ByteArrayOutputStream bytes;
	
	public BinWriteParser(Path path)
	{
		this.path = path;
		bytes = new ByteArrayOutputStream();
		file = new DataOutputStream(bytes);
	}
	
	static
	{
		writers.put(Integer.class, (self, i) -> self.file.writeInt((int) i));
		writers.put(Double.class , (self, i) -> self.file.writeDouble((double) i));
		writers.put(String.class , (self, i) -> self.file.writeUTF((String) i));
		writers.put(SimTime.class, (self, i) -> self.file.writeUTF(((SimTime) i).toString()));
	}
	
//	private String readString()
//	{
//		int num = file.getInt();
//		char[] chars = new char[num];
//		for(int i = 0; i < num; i++)
//		{
//			chars[i] = file.getChar();
//		}
//		return new String(chars);
//	}

	@Override
	public <T> boolean isSupported(Class<T> type)
	{
		return writers.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void write(Class<T> type, T item)
	{
		((IoWriteFunc<BinWriteParser, T>)writers.get(type)).accept(this, item);
	}

	@Override
	public void writeToFile() throws IOException
	{
		Files.write(path, bytes.toByteArray());
	}

	@Override
	public void nextEntry()
	{
		// no-op
	}
}
