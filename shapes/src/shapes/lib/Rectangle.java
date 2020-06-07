package shapes.lib;

import static java.lang.Math.abs;

public class Rectangle implements Shape
{
	private double w;
	private double h;
	
	public Rectangle(double w, double h)
	{
		this.w = w;
		this.h = h;
	}

	@Override
	public double area()
	{
		return w * h;
	}
	
	public static Rectangle ofSize(double width, double height)
	{
		return new Rectangle(width, height);
	}
	
	public static Rectangle at(double x1, double y1, double x2, double y2)
	{
		return new Rectangle(abs(x2 - x1), abs(y2 - y1));
	}

}
