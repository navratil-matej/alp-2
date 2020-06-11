package contamulation.ui;

import java.util.Scanner;

import contamulation.app.behavior.Architecture;
import contamulation.app.behavior.Behavior;
import contamulation.app.behavior.Job;
import contamulation.app.simulation.Simulation;
import contamulation.app.world.Building;
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

		boolean exit = false;
		while(!exit)
		{
			switch(sc.nextLine())
			{
			case "1": menuAdd(sc);    break;
			case "2": menuRemove(sc); break;
			case "3": menuList(sc);   break;
			
			case "0": exit = true;   break;
			default: System.out.println(Lang.string(Lang.Key.INVALID_OPTION)); break;
			
			}
		}
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
			case "1":
				addToCurve(Double.class, Lang.Key.CLASS_DOUBLE, Simulation.INSTANCE.citySizeDist());
				break;
				// TODO 2
			case "3":
				addToCurve(Job.class, Lang.Key.CLASS_JOB_ID, Simulation.INSTANCE.jobDist());
				break;
				// TODO 4
			case "5":
				addToCurve(Architecture.class, Lang.Key.CLASS_ARCH_ID, Simulation.INSTANCE.archDist());
				break;
				// TODO 6
				// TODO 7
//			case "8":
//				addToCurve(Integer.class, Lang.Key.CLASS_INTEGER, Simulation.INSTANCE.disease());
//				break;	
			}
		}
	}
	
	private static void menuRemove(Scanner sc)
	{
		// TODO Auto-generated method stub
		
	}

	private static void menuList(Scanner sc)
	{
		// TODO Auto-generated method stub
		
	}
	
	private static void printEntryTypes()
	{
		System.out.println("1.  " + Lang.string(Lang.Key.CITY_SIZE_POINT));
		System.out.println("2.  " + Lang.string(Lang.Key.JOB));
		System.out.println("3.  " + Lang.string(Lang.Key.JOB_DISTRIBUTION_POINT));
		System.out.println("4.  " + Lang.string(Lang.Key.BUILDING));
		System.out.println("5.  " + Lang.string(Lang.Key.BUILDING_DISTRIBUTION_POINT));
		System.out.println("6.  " + Lang.string(Lang.Key.BEHAVIOR));
		System.out.println("7.  " + Lang.string(Lang.Key.BEHAVIOR_TIMETABLE_POINT));
		System.out.println("8.  " + Lang.string(Lang.Key.DISEASE_INCUBATION_POINT));
		System.out.println("9.  " + Lang.string(Lang.Key.DISEASE_TRANSMISSION_POINT));
		System.out.println("10. " + Lang.string(Lang.Key.DISEASE_DIAGNOSIS_POINT));
		System.out.println("0.  " + Lang.string(Lang.Key.BACK));
	}
	
	private static <T> void addToCurve(Class<T> type, String typeLangKey, Curve<T> curve)
	{
		
	}
	
	private static <T> void addToTimetable(Class<T> type, String typeLangKey, Timetable<T> timetable)
	{
		
	}
}
