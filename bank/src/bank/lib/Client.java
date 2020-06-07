package bank.lib;

import java.util.ArrayList;
import java.util.List;

public abstract class Client
{
	protected String name;
	protected List<Account> accounts;
	
	protected Client(String name)
	{
		this.name = name;
		accounts = new ArrayList<>(1);
	}
	
	public void addAccount(Account account)
	{
		accounts.add(account);
	}
	
	public int getTotal()
	{
		return accounts.stream().mapToInt(a -> a.getMoney()).sum();
	}
	
	public abstract String getName();
}
