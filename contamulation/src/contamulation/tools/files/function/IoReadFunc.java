package contamulation.tools.files.function;

import java.io.IOException;
import java.util.function.Function;

/**
 * Parser function wrapping Function<Source, Item> for lambda exception catching.
 * @author des
 *
 * @param <S> Source
 * @param <I> Item
 */
@FunctionalInterface
public interface IoReadFunc<S, I> extends Function<S, I>
{
	public default I apply(S source)
	{
		try
		{
			return read(source);
		} catch (IllegalArgumentException | IOException e)
		{
			return null;
		}
	}
	
	public I read(S source) throws IOException, IllegalArgumentException;
}
