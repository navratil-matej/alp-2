package cmd.commands;

import java.util.HashMap;
import java.util.Map;

import cmd.api.Command;
import cmd.app.State;

public class HelpCommand implements Command
{
	@Override
	public String keyword()
	{
		return "help";
	}

	@Override
	public Map<String, Command> flags()
	{
		return new HashMap<>();
	}

	@Override
	public void init()
	{
	}

	@Override
	public boolean execute(State state, String[] args)
	{
		System.out.println("hi that didn't work");
		return true;
	}

}
