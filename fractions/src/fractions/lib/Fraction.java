package fractions.lib;

public class Fraction
{
	private final long numerator;
	private final long denominator;
	
	public Fraction(long numerator, long denominator)
	{
		super();
		if(denominator == 0)
			throw new ArithmeticException("Zero denominator.");
		if(denominator < 0)
		{
			numerator *= -1;
			denominator *= -1;
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public double eval()
	{		
		return ((double)numerator) / denominator;
	}
	
	public Fraction inverted()
	{
		return new Fraction(denominator, numerator);
	}
	
	public Fraction negative()
	{
		return new Fraction(-numerator, denominator);
	}

	public long getNumerator()
	{
		return numerator;
	}

	public long getDenominator()
	{
		return denominator;
	}

	@Override
	public String toString()
	{
		return numerator + " / " + denominator;
	}

	private long gcd(long a, long b)
	{
		return b == 0 ? a : gcd(b, a % b);
	}
}
