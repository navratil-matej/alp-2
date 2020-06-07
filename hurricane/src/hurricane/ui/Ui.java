package hurricane.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

import hurricane.app.Hurricane;
import hurricane.app.HurricaneApp;
import hurricane.tools.MonthTools;

public final class Ui
{
	public static void run(Scanner sc) throws IOException
	{
		System.out.println("Before continuing, please select working dataset.");
		System.out.println("Entire line will be read as one parameter; Spaces should work just fine.");
		String str = sc.nextLine();
		Path path = Paths.get(str);
		while(!path.toFile().exists())
		{
			System.out.println("Invalid file; check for typos and ensure you have necessary access permissions.");
			System.out.println("Also check the file is of the required format.");
			System.out.println("Before continuing, please select working dataset.");

			str = sc.nextLine();
			path = Paths.get(str);
		}
		List<String> content = Files.readAllLines(path);
		HurricaneApp.setDataset(HurricaneApp.parse(content, "\t"));
		HurricaneApp.restore();
		
		boolean exit = false;
		while(!exit)
		{
			System.out.println();
			System.out.println("Enter your choice:");
			System.out.println("1. list matching hurricanes.");
			System.out.println("2. filter hurricanes by time period.");
			System.out.println("3. filter hurricanes by name RegEx.");
			System.out.println("4. order hurricanes by velocity.");
			System.out.println("5. order hurricanes by date.");
			System.out.println("6. order hurricanes by name.");
			System.out.println("7. reverse hurricane order.");
			System.out.println("8. restore all hurricanes.");
			System.out.println("0. exit.");
			
			switch(sc.nextLine())
			{
			case "1": list(); break;
			case "2": filterTime(sc); break;
			case "3": filterRegex(sc); break;
			case "4": orderVelocity(); break;
			case "5": orderDate(); break;
			case "6": orderName(); break;
			case "7": reverse(); break;
			case "8": restore(); break;
			case "0": exit = true; break;
			default:  System.out.println("Invalid option"); break;
			}
		}
	}
	
	private static void filterTime(Scanner sc)
	{
		System.out.println("Enter desired range in format YYYY MMM YYYY MMM .");
		System.out.println("Entire line will be read and split on spaces.");
		String str = sc.nextLine();
		String[] split = str.split(" +");
		YearMonth a = YearMonth.of(Integer.parseInt(split[0]), MonthTools.parse(split[1]));
		YearMonth b = YearMonth.of(Integer.parseInt(split[2]), MonthTools.parse(split[3]));
		HurricaneApp.filter((h) -> h.yearMonth().compareTo(a) >= 0 && h.yearMonth().compareTo(b) <= 0);
	}
	
	private static void filterRegex(Scanner sc)
	{
		System.out.println("Enter desired regular expression.");
		System.out.println("Entire line will be read as one parameter; Spaces should work just fine.");
		String str = sc.nextLine();
		HurricaneApp.filter((h) -> h.name().matches(".*" + str + ".*"));
	}
	
	private static void list()
	{
		for(Hurricane h : HurricaneApp.getWorking())
			System.out.println(h.formatted());
	}
	
	private static void orderDate()
	{
		HurricaneApp.sort((a, b) -> a.yearMonth().compareTo(b.yearMonth()));
	}
	
	private static void orderVelocity()
	{
		HurricaneApp.sort((a, b) -> a.velocity() - b.velocity());
	}
	
	private static void orderName()
	{
		HurricaneApp.sort((a, b) -> Collator.getInstance().compare(a.name(), b.name()));
	}
	
	private static void reverse()
	{
		HurricaneApp.reverse();
	}
	
	private static void restore()
	{
		HurricaneApp.restore();
	}
}
