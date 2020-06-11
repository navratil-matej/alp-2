package contamulation.app.behavior;

import contamulation.app.world.Person;

public interface BehaviorFlag
{
	public String id();
	
	public boolean matches(Behavior behavior, Person person);
}
