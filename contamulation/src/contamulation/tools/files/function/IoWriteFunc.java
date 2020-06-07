package contamulation.tools.files.function;

import java.io.IOException;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface IoWriteFunc<D, I> extends BiConsumer<D, I>
{
	@Override
	public default void accept(D destination, I item)
	{
		try
		{
			write(destination, item);
		} catch (IllegalArgumentException | IOException e)
		{
			// TODO log
		}
	}
	
	public void write(D destination, I item) throws IOException, IllegalArgumentException;
}
