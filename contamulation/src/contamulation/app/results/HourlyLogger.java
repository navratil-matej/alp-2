package contamulation.app.results;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import contamulation.app.world.Person;

public class HourlyLogger
{
	private int susceptible;
	private int infected;
	private int recovered;
	
	public HourlyLogger(int susceptible, int infected, int recovered)
	{
		super();
		this.susceptible = susceptible;
		this.infected = infected;
		this.recovered = recovered;
	}
	
	public static HourlyLogger combine(List<HourlyLogger> logs)
	{
		int susceptible = 0;
		int infected    = 0;
		int recovered   = 0;
		for(HourlyLogger log : logs)
		{
			susceptible += log.getSusceptible();
			infected    += log.getInfected();
			recovered   += log.getRecovered();
		}
		return new HourlyLogger(susceptible, infected, recovered);
	}
	
	public void logPerson(Person person)
	{
		if(person.isInfectious())
		{
			infected++;
		}
		else if(person.isSusceptible())
		{
			susceptible++;
		}
		else
		{
			recovered++;
		}
	}
	
	public HourlyLog build(Optional<HourlyLog> prev)
	{
		return new HourlyLog(prev, getSusceptible(), getInfected(), getRecovered());
	}
	
	public static HourlyLogger combine(HourlyLogger...logs)
	{
		return combine(Arrays.asList(logs));
	}

	public int getSusceptible()
	{
		return susceptible;
	}

	public int getInfected()
	{
		return infected;
	}

	public int getRecovered()
	{
		return recovered;
	}
}
