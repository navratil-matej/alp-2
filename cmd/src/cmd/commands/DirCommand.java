package cmd.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cmd.api.Command;
import cmd.app.State;

public class DirCommand implements Command
{
	private Map<String, Command> nested = new HashMap<>();

	@Override
	public String keyword()
	{
		return "dir";
	}

	@Override
	public Map<String, Command> flags()
	{
		return nested;
	}

	@Override
	public void init()
	{
		nested.clear();
		addNested(new DirOrderedCommand());
		addNested(new DirExtCommand());
		addNested(new DirSizeCommand());
		addNested(new DirRecurrentCommand());
	}

	@Override
	public boolean execute(State state, String[] args)
	{
		String arg = args.length > 0 ? args[0] : "";
		for(Path sub : list(state.workingDirectory(), arg).collect(Collectors.toList()))
		{
			System.out.println(sub.getFileName().toString());
		}
		return true;
//		Path path = Paths.get(args[pathArg()]);
//		for(Path sub : list(path, args[0]).collect(Collectors.toList()))
//		{
//			System.out.println();
//		}
//		return true;
	}
	
//	protected int pathArg()
//	{
//		return 0;
//	}
	
	protected Stream<Path> list(Path path, String arg)
	{
		try
		{
			return Files.list(path);
		} catch (IOException e)
		{
			return Stream.empty();
		}
	}

	private void addNested(Command cmd)
	{
		nested.put(cmd.keyword(), cmd);
	}
}
