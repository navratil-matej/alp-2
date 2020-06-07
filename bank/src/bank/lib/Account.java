package bank.lib;

public class Account
{
	private int money;
	
	public Account()
	{
		this(0);
	}
	
	public Account(int amount)
	{
		this.money = amount;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public void put(int amount)
	{
		this.money += amount;
	}
	
	public boolean take(int amount)
	{
		if(this.money >= amount)
		{
			this.money -= amount;
			return true;
		}
		return false;
	}
}
