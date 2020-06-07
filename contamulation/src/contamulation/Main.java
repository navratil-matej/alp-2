package contamulation;

import contamulation.tools.Lang;
import contamulation.ui.Ui;

public class Main
{
	public static void main(String[] args)
	{
		// TODO not in prod env --
		try
		{
			Lang.Generator.generate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// --
		
		if(args.length == 0)
		{
			Lang.set(Lang.Bundle.EN_US);
			Ui.run();
		}
		else
		{
			selectEntryPoint(args);
		}
	}
	
	private static void selectEntryPoint(String[] args)
	{
		
	}
}
