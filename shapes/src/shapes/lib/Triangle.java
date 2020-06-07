package shapes.lib;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;
import static java.lang.Math.sqrt;

public class Triangle implements Shape
{
	private double a;
	private double b;
	private double c;
	
	public Triangle(double a, double b, double c)
	{
		if (a + b <= c || b + c <= a || c + a <= b)
			throw new IllegalArgumentException("Not a triangle: " + a + ", " + b + ", " + c);
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public double area()
	{
		final double s = s();
		return sqrt(s * (s - a) * (s - b) * (s - c));
	}
	
	private double s()
	{
		return (a + b + c) / 2;
	}
	
	public static Triangle at(double x1, double y1, double x2, double y2, double x3, double y3)
	{
		return new Triangle(
			hypot(abs(x1 - x2), abs(y1 - y2)),
			hypot(abs(x2 - x3), abs(y2 - y3)),
			hypot(abs(x3 - x1), abs(y3 - y1)));
	}
}
