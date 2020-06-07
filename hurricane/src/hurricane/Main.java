package hurricane;

import java.io.IOException;
import java.util.Scanner;

import hurricane.ui.Ui;

public class Main
{
	public static void main(String[] args)
	{
		// TODO args
		try
		{
			Ui.run(new Scanner(System.in));
		} catch (IOException e)
		{
			System.out.println("Error reading file. Program will now exit.");
		}
	}
}
