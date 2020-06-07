package cmd.app;

import java.util.HashMap;
import java.util.Map;

import cmd.api.Command;
import cmd.commands.CdCommand;
import cmd.commands.DirCommand;
import cmd.commands.HelpCommand;
import cmd.commands.MkdirCommand;
import cmd.tools.StringTools;

public class CmdApp // "[^"]*"|[^,]+
{
	private static State state;
	private static Map<String, Command> commands = new HashMap<>();

	public static State getState()
	{
		return state;
	}

	public static void setState(State state)
	{
		CmdApp.state = state;
	}

	public static boolean run(String line)
	{
		// TODO String[] split = StringTools.splitRespectQuotes(line, " ");
		String[] split = line.split(" +");
		return tryRunNested(null, commands, split);
	}
	
	public static void initBuiltin()
	{
		add(new CdCommand());
		add(new DirCommand());
		add(new MkdirCommand());
		add(new HelpCommand());
		
		for(Command c : commands.values())
			c.init();
	}
	
	private static void add(Command c)
	{
		commands.put(c.keyword(), c);
	}
	
	private static boolean tryRunNested(Command cmd, Map<String, Command> cmds, String[] args)
	{
		if(args.length > 0 && cmds != null && cmds.containsKey(args[0]))
		{
			final String[] next = new String[args.length - 1];
			System.arraycopy(args, 1, next, 0, next.length);
			final Command nested = cmds.get(args[0]);
			return tryRunNested(nested, nested.flags(), next);
		}
		else if(cmd != null)
		{
			return cmd.execute(state, args);
		}
		else
		{
			commands.get("help").execute(state, args); // TODO hardcoded
			return false;
		}
	}
}
