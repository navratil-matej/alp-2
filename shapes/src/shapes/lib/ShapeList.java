package shapes.lib;

import java.util.ArrayList;

public class ShapeList extends ArrayList<Shape> implements Shape
{
	private static final long serialVersionUID = 7451691008986126165L;

	@Override
	public double area()
	{
		return this.stream().mapToDouble(s -> s.area()).sum();
	}

}
