package shapes.lib;

public class Square extends Rectangle
{

	public Square(double a)
	{
		super(a, a);
	}

	public static Square ofSide(double a)
	{
		return new Square(a);
	}
}
