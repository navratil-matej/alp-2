package cmd.api;

import java.util.Map;

import cmd.app.State;

public interface Command
{
	public String keyword();
	
	public Map<String, Command> flags();
	
	public void init();
	
	public boolean execute(State state, String[] args);
}
