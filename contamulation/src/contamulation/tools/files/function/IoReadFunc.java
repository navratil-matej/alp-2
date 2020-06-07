package contamulation.tools.files.function;

import java.io.IOException;
import java.util.function.Function;

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
