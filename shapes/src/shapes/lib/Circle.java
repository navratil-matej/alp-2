package shapes.lib;

public class Circle implements Shape
{
	public double r;

	protected Circle(double r)
	{
		this.r = r;
	}
	
	@Override
	public double area()
	{
		return Math.PI * r * r;
	}
	
	public static Circle ofRadius(double r)
	{
		return new Circle(r);
	}
	
	public static Circle ofDiameter(double d)
	{
		return new Circle(d / 2);
	}

}
