package contamulation.app.world;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import contamulation.api.SimLoggable;
import contamulation.api.TimeSensitive;
import contamulation.app.results.HourlyLogger;
import contamulation.app.simulation.Simulation;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.Curve;
import contamulation.tools.structs.SimTime;

public class Room implements TimeSensitive, SimLoggable
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

	/**
	 * Adds person to this room as specified by address.
	 * @param person Person to add.
	 * @param address Address to add to, given for the isWorking field.
	 */
	public void addPerson(Person person, Address address)
	{
		people.add(person);
		if(address.isWorking())
			currentJob++;
		else
			currentOuter++;
	}

	/**
	 * Removes person from this room as specified by address.
	 * @param person Person to remove.
	 * @param address Address to remove from, given for the isWorking field.
	 */
	public void removePerson(Person person, Address address)
	{
		people.remove(person);
		if(address.isWorking())
			currentJob--;
		else
			currentOuter--;
	}
	
	/**
	 * Checks whether this room has space for a person.
	 * @param address Address to remove check, given for the isWorking field.
	 * @return whether this room has space for a person.
	 */
	public boolean hasRoom(Address address)
	{
		return hasRoom(address.isWorking());
	}

	
	/**
	 * Checks whether this room has space for a person.
	 * @param job Whether to look for a work place or visit place.
	 * @return whether this room has space for a person.
	 */
	public boolean hasRoom(boolean job)
	{
		if(job)
			return currentJob < capacityJob;
		else
			return currentOuter < capacityOuter;
	}
	
	@Override
	public void nextHour(SimTime time)
	{
		final Simulation sim =Simulation.INSTANCE; 
		final Random rand = sim.random();
		final Curve<Double> transmission = sim.disease().getTransmissionChanceCurve();
		for(Person a : people)
		{
			a.nextHour(time);
			if(a.isInfectious())
			{
				for(Person b : people)
				{                         // TODO magic
					final double chance = (1.0/24) * contact * a.getJob().getContact() * b.getJob().getContact(); 
					if(rand.nextDouble() < transmission.get(a.getInfectionProgress()) * chance)
						a.transmit(b);
				}
			}
		}
	}

	@Override
	public void probe(HourlyLogger logger)
	{
		for(Person p : people)
			logger.logPerson(p);
	}
}
