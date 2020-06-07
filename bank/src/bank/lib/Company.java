package bank.lib;

public class Company extends Client
{
	public Company(String name)
	{
		super(name);
	}

	@Override
	public String getName()
	{
		return "firma " + name;
	}

}
