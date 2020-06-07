package contamulation.app.world;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import contamulation.api.TimeSensitive;
import contamulation.app.simulation.Simulation;

public class Room implements TimeSensitive
{
	private Set<Person> people;
	private int capacityJob;
	private int capacityOuter;
	private int currentJob;
	private int currentOuter;
	private double contact;
	
	public Room(int roomJobCap, int roomOutCap, double contact)
	{
		people = new HashSet<>();
		capacityJob = roomJobCap;
		capacityOuter = roomOutCap;
		this.contact = contact;
	}

	public void enteredBy(Person person, boolean job)
	{
		people.add(person);
		if(job)
			currentJob++;
		else
			currentOuter++;
	}
	
	public void leftBy(Person person, boolean job)
	{
		people.remove(person);
		if(job)
			currentJob--;
		else
			currentOuter--;
	}
	
	public boolean canBeEntered(boolean job)
	{
		if(job)
			return currentJob < capacityJob;
		else
			return currentOuter < capacityOuter;
	}
	
	public boolean isPresent(Person person)
	{
		return people.contains(person);
	}
	
	public int capacityJob()
	{
		return capacityJob;
	}
	
	public int capacityOuter()
	{
		return capacityOuter;
	}
	
	@Override
	public void nextHour()
	{
		final Simulation sim =Simulation.INSTANCE; 
		final Random rand = sim.random();
		final double spread = sim.disease().getTransmission() * contact;
		for(Person a : people)
		{
			for(Person b : people)
			{
				if(rand.nextDouble() < spread)
					a.transmit(b);
			}
		}
	}
}
