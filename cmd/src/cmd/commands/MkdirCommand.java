package cmd.commands;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import cmd.api.Command;
import cmd.app.State;

public class MkdirCommand implements Command
{
	private Map<String, Command> nested = new HashMap<>();

	@Override
	public String keyword()
	{
		return "cd";
	}

	@Override
	public Map<String, Command> flags()
	{
		return nested;
	}

	@Override
	public void init()
	{
	}

	@Override
	public boolean execute(State state, String[] args)
	{
		return state.traversePath(Paths.get(args[0]));
	}
}
