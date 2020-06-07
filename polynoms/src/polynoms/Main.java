package polynoms;
import polynoms.lib.Polynom;
import polynoms.lib.Polynoms;

public class Main
{
	public static void main(String[] args)
	{
		Polynom p = Polynom.of(3, 0, -1, 2, 1);
		Polynom q = Polynom.of(2, 1);
		System.out.println(p);
		System.out.println(Polynoms.multiply(p, q));
		System.out.println(Polynom.of(2*3, 2*0 + 1*3, 2*-1 + 1*0, 2*2 + 1*-1, 2*1 + 1*2, 1*2));
		System.out.println(p.valueFor(2));
		System.out.println(3*16 + 0*8 + -1*4 + 2*2 + 1*1.0);
	}
}
