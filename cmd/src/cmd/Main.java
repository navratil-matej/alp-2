package cmd;

import java.util.Scanner;

import cmd.app.CmdApp;
import cmd.ui.Ui;

public class Main
{
	public static void main(String[] args)
	{
		CmdApp.initBuiltin();
		Ui.run(new Scanner(System.in));
	}
}
