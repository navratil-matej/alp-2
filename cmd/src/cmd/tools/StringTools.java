package cmd.tools;

public final class StringTools
{
	private static final String MATCH = "\"%s(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)\"";
	public static String[] splitRespectQuotes(String str, String delim)
	{
		return str.split(String.format(MATCH, delim));
	}
}
