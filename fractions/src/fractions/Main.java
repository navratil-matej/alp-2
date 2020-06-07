package fractions;
import fractions.lib.Fraction;
import fractions.lib.Fractions;

public class Main
{
	public static void main(String[] args)
	{
		Fraction a = new Fraction(-1, 6);
		Fraction b = new Fraction(2, 9);
		Fraction c = new Fraction(3, 1);
		
		System.out.println(Fractions.add(a, b));
		System.out.println(Fractions.add(b, c));
		System.out.println(Fractions.add(c, a));
		System.out.println(Fractions.multiply(a, b));
		System.out.println(Fractions.multiply(b, c));
		System.out.println(Fractions.multiply(c, a));
	}
}
