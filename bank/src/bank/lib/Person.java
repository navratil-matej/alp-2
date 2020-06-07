package bank.lib;

public class Person extends Client
{
	public Person(String name)
	{
		super(name);
	}

	@Override
	public String getName()
	{
		final String prefix = name.endsWith("ova") ? "pani" : "pan"; 
		return prefix + " " + name;
	}

}
