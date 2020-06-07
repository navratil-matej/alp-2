package contamulation.tools.files;

/**
 * An interface designed to read data in any arbitrary format.
 * @author des
 *
 */
public interface FileReadParser
{
	/**
	 * Reads the next T, provided it's possible to read a T - see hasNext, and moves onto
	 * the next item in the file contents.
	 * @param <T> The type to read, provided generically.
	 * @param type The type to read, for runtime operations.
	 * @return The item of type T that was read.
	 */
	public <T> T read(Class<T> type);
	
	/**
	 * Checks whether type T is supported by this read parser.
	 * @param <T> The type to read, provided generically.
	 * @param type The type to read, for runtime operations.
	 * @return true if the type is supported by this parser.
	 */
	public <T> boolean isSupported(Class<T> type);
	
	/**
	 * Checks whether there are still data to be read.
	 * @return true if there are data to read.
	 */
	public boolean hasNext();
}
