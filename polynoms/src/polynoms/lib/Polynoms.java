package polynoms.lib;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class Polynoms
{
	private Polynoms()
	{
	}
	
	public static Polynom add(Polynom a, Polynom b)
	{
		// swap if a is lower degree
		if(a.degree() < b.degree())
		{
			final Polynom swap = a;
			b = a;
			a = swap;
		}
		double[] out = new double[a.degree() + 1];
		for(int i = 0; i < out.length; i++)
		{
			out[i] = a.coefAt(i) + b.degree() <= i ? b.coefAt(i) : 0;
		}
		return Polynom.ofIncr(out);
	}
	
	public static Polynom subtract(Polynom a, Polynom b)
	{
		return add(a, b.scaled(-1));
	}
	
	public static Polynom multiply(Polynom a, Polynom b)
	{
		double[] out = new double[a.degree() + b.degree() + 1];
		for(int i = 0; i < out.length; i++)
		{
			final int from = max(0, i - b.degree()); 
			final int to   = min(i, a.degree());
			for(int j = from; j <= to; j++)
			{
				out[i] += a.coefAt(j) * b.coefAt(i - j);
			}
		}
		return Polynom.ofIncr(out);
	}
}
