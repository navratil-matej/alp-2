package cmd.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cmd.api.Command;
import cmd.app.State;

public class DirRecurrentCommand extends DirCommand
{
	@Override
	public String keyword()
	{
		return "-r";
	}

	@Override
	public Map<String, Command> flags()
	{
		return null;
	}

	@Override
	public void init()
	{
	}
	
	@Override
	public boolean execute(State state, String[] args)
	{
		recList(state.workingDirectory(), args.length > 0 ? args[0] : "", "");
		return true;
	}
	
	private void recList(Path path, String arg, String pad)
	{
		for(Path sub : list(path, arg).collect(Collectors.toList()))
		{
			System.out.println(pad + sub.getFileName().toString());
			if(sub.toFile().isDirectory())
			{
				recList(sub, arg, pad + "-");
			}
		}
	}
}
