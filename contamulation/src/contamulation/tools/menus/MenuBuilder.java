package contamulation.tools.menus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuBuilder
{
	private List<Item> items;
	
	public MenuBuilder() {}
	
	public MenuBuilder with(Supplier<String> name, Consumer<Scanner> action)
	{
		items.add(new Item(items.size() + 1, name, action));
		return this;
	}
	
	public Menu build()
	{
		return new Menu(items.toArray(new Item[0]));
	}
	
	public void show(Scanner sc)
	{
		int i = -1;
		while (i < 0 || i >= items.size())
		{
			items.forEach(item -> System.out.println(item));			
			try
			{
				i = sc.nextInt() - 1;
			}
			catch (InputMismatchException e) { }
		}
		items.get(i).select(sc);
	}
}
