package bank;
import java.util.ArrayList;
import java.util.List;

import bank.lib.Account;
import bank.lib.Client;
import bank.lib.Company;
import bank.lib.Person;

public class Main
{
	public static void main(String[] args)
	{
		List<Client> clients = new ArrayList<Client>();
		clients.add(new Person("Pekar"));
		clients.add(new Person("Svecova"));
		clients.add(new Company("Skoda"));
		clients.get(0).addAccount(new Account(1000));
		clients.get(0).addAccount(new Account(500));
		clients.get(1).addAccount(new Account(1200));
		clients.get(2).addAccount(new Account(120));
		
		for(Client c : clients)
			System.out.println(c.getName() + ": " + c.getTotal());
	}
}
