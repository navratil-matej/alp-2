package contamulation.ui;

import java.util.Scanner;

import contamulation.app.simulation.Simulation;
import contamulation.tools.Lang;
import contamulation.tools.structs.Curve;
import contamulation.tools.structs.Timetable;

public final class UiEdit
{
	public static void open(Scanner sc)
	{
		System.out.println(Lang.string(Lang.Key.SELECT_OPERATION));
		System.out.println("1." + Lang.string(Lang.Key.OPERATION_ADD));
		System.out.println("2." + Lang.string(Lang.Key.OPERATION_REMOVE));
		System.out.println("3." + Lang.string(Lang.Key.OPERATION_LIST));
		System.out.println("0." + Lang.string(Lang.Key.BACK));
	}
	
	private static void menuAdd(Scanner sc)
	{
		boolean exit = false;
		while(!exit)
		{
			System.out.println(Lang.string(Lang.Key.SELECT_ENTRY_TYPE));
			printEntryTypes();
			switch(sc.nextLine())
			{
//			case "1": addToCurve(Double.class, Lang.Key.CLASS_DOUBLE, )
			}
		}
	}
	
	private static void printEntryTypes()
	{
		System.out.println("1. " + Lang.string(Lang.Key.CITY_SIZE_POINT));
		System.out.println("2. " + Lang.string(Lang.Key.JOB));
		System.out.println("3. " + Lang.string(Lang.Key.JOB_DISTRIBUTION_POINT));
		System.out.println("4. " + Lang.string(Lang.Key.BUILDING));
		System.out.println("5. " + Lang.string(Lang.Key.BUILDING_DISTRIBUTION_POINT));
		System.out.println("6. " + Lang.string(Lang.Key.BEHAVIOR));
		System.out.println("7. " + Lang.string(Lang.Key.BEHAVIOR_TIMETABLE_POINT));
		System.out.println("8. " + Lang.string(Lang.Key.DISEASE_INCUBATION_POINT));
		System.out.println("9. " + Lang.string(Lang.Key.DISEASE_TRANSMISSION_POINT));
		System.out.println("0. " + Lang.string(Lang.Key.BACK));
	}
	
	private static <T> void addToCurve(Class<T> type, String typeLangKey, Curve<T> curve)
	{
		
	}
	
	private static <T> void addToTimetable(Class<T> type, String typeLangKey, Timetable<T> timetable)
	{
		
	}
}
