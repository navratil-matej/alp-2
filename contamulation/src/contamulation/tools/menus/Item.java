package contamulation.tools.menus;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Item
{
	private int index;
	private Supplier<String> name;
	private Consumer<Scanner> action;
	
	public Item(int index, Supplier<String> name, Consumer<Scanner> action)
	{
		super();
		this.index = index;
		this.name = name;
		this.action = action;
	}
	
	@Override
	public String toString()
	{
		return index + ". " + name;
	}
	
	public void select(Scanner sc)
	{
		action.accept(sc);
	}
}
