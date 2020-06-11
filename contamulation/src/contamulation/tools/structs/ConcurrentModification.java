package contamulation.tools.structs;

import contamulation.app.world.Person;

/**
 * Represents the action of moving from one place to another, inside a foreach loop.
 * @author des
 *
 */
public class ConcurrentModification
{
	private Address from;
	private Address to;
	private Person person;
	
	public ConcurrentModification(Address from, Address to, Person person)
	{
		super();
		this.from = from;
		this.to = to;
		this.person = person;
	}

	public Address getFrom()
	{
		return from;
	}

	public Address getTo()
	{
		return to;
	}

	public Person getPerson()
	{
		return person;
	}
}
