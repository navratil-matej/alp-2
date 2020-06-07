package cmd.app;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class State
{
	private Path workDir;
	private boolean exit;
	
	private State(Path workDir)
	{
		this.workDir = workDir;
		exit = false;
	}

	public static State ofDir(Path workDir)
	{
		return new State(workDir);
	}
	
	public boolean traversePath(Path relative)
	{
		Path next = workDir.resolve(relative);
		if(Files.exists(next, LinkOption.NOFOLLOW_LINKS) && next.toFile().isDirectory())
		{
			workDir = next.normalize();
			return true;
		}
		System.out.println("directory not found: " + relative);
		return false;
	}
	
	public Path workingDirectory()
	{
		return workDir;
	}

	public void exit()
	{
		exit = true;
	}

	public boolean isExit()
	{
		return exit;
	}
}
