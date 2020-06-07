package cmd.commands;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import cmd.api.Command;

public class DirSizeCommand extends DirCommand
{
	@Override
	public String keyword()
	{
		return "-s";
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
	
//	protected int pathArg()
//	{
//		return 1;
//	}

	@Override
	protected Stream<Path> list(Path path, String arg)
	{
		long size = Long.parseLong(arg);
		return super.list(path, arg).filter(
			f -> f.toFile().length() > size);
	}
}
