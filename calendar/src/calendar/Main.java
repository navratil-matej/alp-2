package calendar;

import calendar.lib.Calendar;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println(new Calendar(2020, 02).asTable());
		System.out.println();
		System.out.println(new Calendar(2020, 03).asTable());
		System.out.println();
		System.out.println(new Calendar(2020, 04).asTable());
		System.out.println();
		System.out.println(new Calendar(2020, 05).asTable());
	}
}
