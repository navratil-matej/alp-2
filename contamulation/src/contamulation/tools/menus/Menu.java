package contamulation.tools.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu
{
	private Item[] items;
	private boolean close;
	
	public Menu(Item[] items)
	{
		this.items = items;
	}
	
	public void close()
	{
		close(null);
	}
	
	public void close(Scanner sc)
	{
		close = true;
	}
	
	public void show(Scanner sc)
	{
		int i = -1;
		close = false;
		while (i < 0 || i >= items.length)
		{
			for(Item item : items)
				System.out.println(item);
			try
			{
				i = sc.nextInt() - 1;
			}
			catch (InputMismatchException e) { }
		}
		items[i].select(sc);
		if(!close)
			show(sc);
	}
}
