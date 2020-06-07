package fractions.lib;

public final class Fractions
{
	private Fractions()
	{
	}
	
	public static Fraction add(Fraction a, Fraction b)
	{
		final long left = a.getNumerator() * b.getDenominator();
		final long right = b.getNumerator() * a.getDenominator();
		final long numerator = left + right;
		final long denominator = a.getDenominator() * b.getDenominator();
		final long common = gcd(numerator, denominator);
		return new Fraction(numerator / common, denominator / common);
	}
	
	public static Fraction multiply(Fraction a, Fraction b)
	{
		final long numerator = a.getNumerator() * b.getNumerator();
		final long denominator = a.getDenominator() * b.getDenominator();
		final long common = gcd(numerator, denominator);
		return new Fraction(numerator / common, denominator / common);
	}
	
	public static Fraction subtract(Fraction a, Fraction b)
	{
		return add(a, b.negative());
	}
	
	public static Fraction divide(Fraction a, Fraction b)
	{
		return add(a, b.inverted());
	}

	private static long gcd(long a, long b)
	{
		return b == 0 ? a : gcd(b, a % b);
	}
}
