package cmd.ui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import cmd.app.CmdApp;
import cmd.app.State;

public final class Ui
{
	public static void run(Scanner sc)
	{
		System.out.println("Before continuing, please select working directory.");
		System.out.println("Entire line will be read as one parameter; Spaces should work just fine.");
		String str = sc.nextLine();
		Path path = Paths.get(str);
		while(!path.toFile().exists() || !path.toFile().isDirectory())
		{
			System.out.println("Invalid directory; check for typos and ensure you have necessary access permissions.");
			System.out.println("Before continuing, please select working directory.");

			str = sc.nextLine();
			path = Paths.get(str);
		}
		
		CmdApp.setState(State.ofDir(path));
		
		while(!CmdApp.getState().isExit())
		{
			CmdApp.run(sc.nextLine());
//			switch()
//			{
//			case "1": list(); break;
//			case "2": filterTime(sc); break;
//			case "3": filterRegex(sc); break;
//			case "4": orderVelocity(); break;
//			case "5": orderDate(); break;
//			case "6": orderName(); break;
//			case "7": reverse(); break;
//			case "8": restore(); break;
//			case "0": exit = true; break;
//			default:  System.out.println("Invalid option"); break;
//			}
		}
	}
}
