package calendar.lib;

public class Calendar
{
	private static final int[] MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	private int month;
	private int year;
	
	public Calendar(int year, int month)
	{
		this.year = year;
		this.month = month;
	}
	
	public final boolean isLeapYear()
	{
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}
	
	public final int daysInMonth()
	{
		return MONTH_DAYS[month - 1] + (month == 2 && isLeapYear() ? 1 : 0);
	}
	
	private final static int weekdayIndex(int year, int month, int day)
	{
		final int q = day;
		final int m = (month + 9) % 12 + 3;
		final int y = month <= 2 ? year - 1 : year;
		final int k = y % 100;
		final int j = y / 100;
		final int h = (q + 13 * (m + 1) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
		return (h + 5) % 7 + 1;
	}
	
	public final String asTable()
	{
		int begin = weekdayIndex(year, month, 1) - 1;
		int end = begin + daysInMonth() - 1;
		int rows = ((end - 1) / 7) + 1;
		
		StringBuilder sb = new StringBuilder();
		sb.append("|Mo|Tu|We|Th|Fr|Sa|Su|");
		for(int i = 0; i < rows * 7; i++)
		{
			if(i % 7 == 0)
				sb.append("\n|");
			if(i >= begin && i <= end)
				sb.append(String.format("%2d|", i - begin + 1));
			else
				sb.append("  |");
		}
		return sb.toString();
	}
}
