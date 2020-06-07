package polynoms.lib;

import polynoms.util.ArrayUtil;

public class Polynom
{
	/**
	 * coefficients of terms x^n (power operator) where n is the array index. For example:<br>
	 * [3, 1, 5] = 3*x^0 + 1*x^1 + 5*x^2
	 */
	double[] coef;
	
	private Polynom(double...coef)
	{
		this.coef = ArrayUtil.copy(coef);
	}

	/**
	 * Creates an instance of Polynom with given coefficients in incremental order, for example:<br>
	 * ofIncr(3, 1, 5) -> 3*x^0 + 1*x^1 + 5*x^2
	 */
	public static Polynom ofIncr(double...coef)
	{
		return new Polynom(coef);
	}

	/**
	 * Creates an instance of Polynom with given coefficients in standard (decremental) order, for example:<br>
	 * of(3, 1, 5) -> 3*x^2 + 1*x^1 + 5*x^0
	 */
	public static Polynom of(double...coef)
	{
		ArrayUtil.reverse(coef);
		return new Polynom(coef);
	}
	
	public double coefAt(int index)
	{
		return coef[index];
	}
	
	public int degree()
	{
		return coef.length - 1;
	}
	
//	public Polynom subtract(Polynom other)
//	{
//		double[] next = new double[coef.length];
//		for(int i = 0; i < next.length; i++)
//		{
//			next[i] = coef[i] - other.coef[i];
//		}
//		return ofIncr(next);
//	}
	
	public Polynom derivative()
	{
		double[] next = new double[coef.length - 1];
		for(int i = 0; i < next.length; i++)
		{
			next[i] = (i+1) * coef[i+1];
		}
		return ofIncr(next);
	}
	
	public Polynom scaled(double k)
	{
		double[] next = new double[coef.length];
		for(int i = 0; i < next.length; i++)
		{
			next[i] = k * coef[i];
		}
		return ofIncr(next);
	}
	
	public double valueFor(double x)
	{
		double out = coef[coef.length - 1];
		for(int i = coef.length - 2; i >= 0; i--)
		{
			out = coef[i] + x * out;
		}
		return out;
	}

	@Override
	public String toString()
	{
		String out = "";
		for(int i = 0; i < coef.length; i++)
		{
			out = format(coef[i], i) + out;
		}
		return out;
	}
	
	private static final String format(double a, int b)
	{
		if(a == 0)
			return "";
		if(b == 0)
			return String.format("%.2f", a);
		if(a == 1)
			return "x^" + b + " + ";
		if(a == -1)
			return "-x^" + b + " + ";
		return String.format("%.2fx^%d + ", a, b);
		
	}
}
