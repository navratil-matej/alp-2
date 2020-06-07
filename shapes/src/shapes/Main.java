package shapes;

import shapes.lib.Circle;
import shapes.lib.Rectangle;
import shapes.lib.Shape;
import shapes.lib.ShapeList;
import shapes.lib.Triangle;

public class Main
{

	public static void main(String[] args)
	{
		ShapeList list = new ShapeList();
		double sum = 0;
		list.add(new Triangle(3, 4, 5));
		list.add(Triangle.at(-1, -1, 3, -1, -1, 2));
		list.add(Circle.ofRadius(1)); // TODO make this the public constructor
		list.add(Circle.ofDiameter(2));
		list.add(new Rectangle(6, 2));
		list.add(Rectangle.at(-1, -1, 5, 1));
		for(Shape s : list)
		{
			System.out.printf("%7.3f%n", s.area());
			sum += s.area();
		}
		System.out.println("-------");
		System.out.printf("%7.3f%n", sum);
		System.out.printf("%7.3f%n", list.area());
	}

}
