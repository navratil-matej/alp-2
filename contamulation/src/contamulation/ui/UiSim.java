package contamulation.ui;

import java.io.PrintStream;
import java.util.Scanner;

import contamulation.app.results.CompleteLog;
import contamulation.app.results.DailyLog;
import contamulation.app.results.HourlyLog;
import contamulation.app.simulation.Simulation;
import contamulation.tools.Lang;

public class UiSim
{
	public static void open(Scanner sc)
	{
		Simulation.INSTANCE.read(Simulation.INSTANCE.getConfig());
		Simulation.INSTANCE.createWorld();
		System.out.println("Running:");
		CompleteLog log = Simulation.INSTANCE.run(5, (h) -> {}, (d) -> printDay(d, System.out));
		log.write(Simulation.INSTANCE.getConfig());
		browse(sc, log);
	}
	
	private static void browse(Scanner sc, CompleteLog log)
	{
		boolean exit = false;
		while(!exit)
		{
			System.out.println("1. " + Lang.string(Lang.Key.LIST_TOP_DAYS));
			System.out.println("2. " + Lang.string(Lang.Key.SORT_BY_NEW_INFECTED));
			System.out.println("3. " + Lang.string(Lang.Key.SORT_BY_NEW_RECOVERED));
			System.out.println("4. " + Lang.string(Lang.Key.SORT_BY_DAILY_FACTOR));
			System.out.println("5. " + Lang.string(Lang.Key.RESTORE));
			System.out.println("0. " + Lang.string(Lang.Key.BACK));
			switch(sc.nextLine())
			{
			case "1": printDays(sc, log); break;
			case "2": log.getDailyView().sort(DailyLog.BY_NEW_INFECTED ); break;
			case "3": log.getDailyView().sort(DailyLog.BY_NEW_RECOVERED); break;
			case "4": log.getDailyView().sort(DailyLog.BY_GROWTH_RATE  ); break;
			case "5": log.restore(); break;

			case "0": exit = true; break;
			default: System.out.println(Lang.string(Lang.Key.INVALID_OPTION)); break;
			}
		}
	}
	
	private static void printDays(Scanner sc, CompleteLog log)
	{
		System.out.println(Lang.string(Lang.Key.DAILY_LOG_HEADER));
		for(int i = 0; i < 20; i++)
		{
			if(i < log.getDailyView().size())
			{
				System.out.println(log.getDailyView().get(i).toString());
			}
		}
	}

	public static void printDay(DailyLog daily, PrintStream...printStreams)
	{
		final int width = 98;
		final int total = daily.getTotal();  
		final int rec = daily.getRecovered() * width / total;
		final int inf = daily.getInfected() * width / total;
		final int sus = width - inf - rec;
		final String bar = String.format("[%s%s%s] %5d-R %5d-I %5d-S%n",
			new String(new char[rec]).replace('\0', '.'),
			new String(new char[inf]).replace('\0', '#'),
			new String(new char[sus]).replace('\0', ' '),
			daily.getRecovered(), daily.getInfected(), daily.getSusceptible());
		for(PrintStream stream : printStreams)
			stream.print(bar);
	}

	public static void printHour(HourlyLog hourly, PrintStream...printStreams)
	{
		final int width = 98;
		final int total = hourly.getTotal();  
		final int rec = hourly.getRecovered() * width / total;
		final int inf = hourly.getInfected() * width / total;
		final int sus = width - inf - rec;
		final String bar = String.format("[%s%s%s] %5d-R %5d-I %5d-S%n",
			new String(new char[rec]).replace('\0', '.'),
			new String(new char[inf]).replace('\0', '#'),
			new String(new char[sus]).replace('\0', ' '),
			hourly.getRecovered(), hourly.getInfected(), hourly.getSusceptible());
		for(PrintStream stream : printStreams)
			stream.print(bar);
	}
}
