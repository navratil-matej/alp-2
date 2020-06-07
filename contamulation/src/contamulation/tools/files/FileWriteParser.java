package contamulation.tools.files;

import java.io.IOException;

public interface FileWriteParser
{
	public <T> void write(Class<T> type, T item);
	
	public <T> boolean isSupported(Class<T> type);
	
	public void nextEntry();
	
	public void writeToFile() throws IOException;
}
