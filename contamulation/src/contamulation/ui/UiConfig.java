package contamulation.ui;

import java.util.Scanner;

import contamulation.app.simulation.Simulation;
import contamulation.app.simulation.SimulationConfig;
import contamulation.app.simulation.SimulationProperty;
import contamulation.tools.Lang;

public class UiConfig
{
	public static void open(Scanner sc)
	{
		boolean exit = false;
		while(!exit)
		{
			System.out.println(Lang.string(Lang.Key.SELECT_CONFIG));
			System.out.println("1. " + Lang.string(Lang.Key.CONFIG_POPULATION_SIZE));
			System.out.println("2. " + Lang.string(Lang.Key.CONFIG_CITY_COUNT));
			System.out.println("3. " + Lang.string(Lang.Key.CONFIG_SELECT_FILE));
			
			System.out.println("0. " + Lang.string(Lang.Key.BACK));
			switch(sc.nextLine())
			{
			case "1": popSize(sc); break;
			case "2": cityCount(sc); break;
//			case "3": UiEdit.open(sc); break;			
	
			case "0": exit = true; break;
			}
		}
	}
	
	private static void popSize(Scanner sc)
	{
		boolean exit = false;
		while(!exit)
		{
			System.out.println(Lang.string(Lang.Key.CONFIG_ENTER_POPULATION_SIZE));
			String answer = sc.nextLine();
			try
			{
				Simulation.INSTANCE.getConfig()
					.set(SimulationConfig.POPULATION_SIZE, Integer.parseInt(answer));
				exit = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println(Lang.string(Lang.Key.NUMBER_FORMAT_EXCEPTION));
			}
		}
	}
	
	private static void cityCount(Scanner sc)
	{
		boolean exit = false;
		while(!exit)
		{
			System.out.println(Lang.string(Lang.Key.CONFIG_ENTER_CITY_COUNT));
			String answer = sc.nextLine();
			try
			{
				Simulation.INSTANCE.getConfig()
					.set(SimulationConfig.POPULATION_SIZE, Integer.parseInt(answer));
				exit = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println(Lang.string(Lang.Key.NUMBER_FORMAT_EXCEPTION));
			}
		}
	}
	
	private static void selectFile(Scanner sc)
	{
		boolean exit = false;
		SimulationProperty<String> property = null;
		while(!exit && property == null)
		{
			System.out.println(Lang.string(Lang.Key.SELECT_FILE_TYPE));
			printFileTypes();
			switch(sc.nextLine())
			{
//			case "1": propKey = SimulationConfig // TODO
			case "2": property = SimulationConfig.JOBS_LIST_FILE; break;
			case "3": property = SimulationConfig.JOBS_DIST_FILE; break;
			case "4": property = SimulationConfig.ARCH_LIST_FILE; break;
			case "5": property = SimulationConfig.ARCH_DIST_FILE; break;
			case "6": property = SimulationConfig.BEHV_LIST_FILE; break;
			case "7": property = SimulationConfig.BEHV_DIR; break;
			case "8": property = SimulationConfig.DISEASE_DIR; break;
			case "0": exit = true; break;
			default: System.out.println(Lang.string(Lang.Key.INVALID_OPTION)); break;
			}
		}
	}
	
	private static void printFileTypes()
	{
		System.out.println("1. " + Lang.string(Lang.Key.CITY_SIZE_FILE));
		System.out.println("2. " + Lang.string(Lang.Key.JOB_FILE));
		System.out.println("3. " + Lang.string(Lang.Key.JOB_DISTRIBUTION_FILE));
		System.out.println("4. " + Lang.string(Lang.Key.BUILDING_FILE));
		System.out.println("5. " + Lang.string(Lang.Key.BUILDING_DISTRIBUTION_FILE));
		System.out.println("6. " + Lang.string(Lang.Key.BEHAVIOR_FILE));
		System.out.println("7. " + Lang.string(Lang.Key.BEHAVIOR_DIR));
		System.out.println("8. " + Lang.string(Lang.Key.DISEASE_DIR));
		System.out.println("0. " + Lang.string(Lang.Key.BACK));
	}
}
