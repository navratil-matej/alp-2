package contamulation.tools.structs;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import contamulation.api.Parsers;
import contamulation.tools.ArrayTools;
import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.BinWriteParser;
import contamulation.tools.files.CsvReadParser;
import contamulation.tools.files.CsvWriteParser;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

/**
 * Normalised curve mapping the 0.0 to 1.0 space to objects.
 * @author des
 *
 * @param <T> The object type to which the normalised space is mapped to.
 */
public class Curve<T>
{
	private List<Double> x;
	private List<T> y;
	
	public Curve()
	{
		x = new ArrayList<>();
		y = new ArrayList<>();
	}
	
	public Curve(List<Double> x, List<T> y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Load the curve from a file. Uses convenience parser classes.
	 * @param <R> the type that y elements of the curve take.
	 * @param type the type that y elements of the curve take.
	 * @param path The path to load the curve from.
	 * @return The loaded curve.
	 */
	public static <R> Curve<R> fromFile(Class<R> type, Path path)
	{
		List<Double> x = new ArrayList<>();
		List<R     > y = new ArrayList<>();
		FileReadParser in = Parsers.readerFor(path);
		if(in == null)
			throw new InvalidPathException(path.toString(),
				"specified file is not a curve file.");
		while(in.hasNext())
		{
			x.add(in.read(Double.class));
			y.add(in.read(type));
		}
		return new Curve<R>(x, y);
	}
	
	/**
	 * Write the curve to a file. Uses convenience parser classes.
	 * @param type the type that y elements of the curve take, for runtime use.
	 * @param path The path to load the curve from.
	 */
	public void toFile(Class<T> type, Path path)
	{
		FileWriteParser out = Parsers.writerFor(path);
		if(out == null)
			throw new InvalidPathException(path.toString(),
				"specified file is not a curve file.");
		for(Kvp<Double, T> kvp : list())
		{
			out.write(Double.class, kvp.key);
			out.write(type, kvp.val);
		}
	}
	
	/**
	 * Add and replace an element at the point x.
	 * @param x The x point to add.
	 * @param y The y point to add.
	 * @return Whether an element was replaced.
	 */
	public boolean add(double x, T y)
	{
		if(this.x.contains(x))
		{
			int i = Collections.binarySearch(this.x, x);
			this.y.set(i, y);
			return true;
		}
		int i = ArrayTools.binsearchLessEqual(this.x, x);
		this.x.add(i+1, x);
		this.y.add(i+1, y);
		return false;
	}
	
	/**
	 * Remove an element corresponding to the x point x.
	 * @param x The point to remove at
	 * @return Whether an element was present.
	 */
	public boolean remove(double x)
	{
		if(this.x.contains(x))
		{
			int i = Collections.binarySearch(this.x, x);
			this.x.remove(i);
			this.y.remove(i);
			return true;
		}
		return false;
	}
	
	public List<Kvp<Double, T>> list()
	{
		List<Kvp<Double, T>> list = new ArrayList<>();
		for(int i = 0; i < x.size(); i++)
			list.add(new Kvp<>(x.get(i), y.get(i)));
		return list;
	}
	
	public T get(double x)
	{
	    return y.get(ArrayTools.binsearchLessEqual(this.x, x));
	}
}
