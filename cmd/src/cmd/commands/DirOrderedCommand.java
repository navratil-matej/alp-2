package cmd.commands;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import cmd.api.Command;

public class DirOrderedCommand extends DirCommand
{
	@Override
	public String keyword()
	{
		return "-o";
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
	protected Stream<Path> list(Path path, String arg)
	{
		return super.list(path, arg).sorted();
	}
}
