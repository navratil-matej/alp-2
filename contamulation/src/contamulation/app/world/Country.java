package contamulation.app.world;

import java.util.ArrayList;
import java.util.List;

import contamulation.api.TimeSensitive;
import contamulation.app.results.CompleteLog;
import contamulation.app.results.CompleteLogBuilder;
import contamulation.app.results.DailyLog;
import contamulation.app.results.HourlyLog;
import contamulation.app.results.HourlyLogger;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.ConcurrentModification;
import contamulation.tools.structs.Kvp;
import contamulation.tools.structs.SimTime;

public class Country implements TimeSensitive
{
	private List<City> cities;
	private CompleteLogBuilder log = new CompleteLogBuilder();
	private List<ConcurrentModification> queue = new ArrayList<>();
	
	public Country()
	{
		cities = new ArrayList<City>();
	}
	
	/**
	 * Logs a concurrent request to move a person.
	 * @param person The person to move.
	 * @param from Original address.
	 * @param to Destination.
	 */
	public void movePerson(Person person, Address from, Address to)
	{
		queue.add(new ConcurrentModification(from, to, person));
	}
	
	public City getCity(Address address)
	{
		return cities.get(address.getCityIndex());
	}
	
	public City getCity(int index)
	{
		return cities.get(index);
	}
	
	public void buildCity(int population)
	{
		cities.add(new City(cities.size(), population, population / 3)); // TODO magic
	}

	@Override
	public void nextHour(SimTime time)
	{
		for(City city : cities)
			city.nextHour(time);
		applyConcurrent();
	}

	public void applyConcurrent()
	{
		for(ConcurrentModification m : queue)
		{
			if(m.getFrom() != null)
				cities.get(m.getFrom().getCityIndex()).removePerson(m.getPerson(), m.getFrom());
			if(m.getTo  () != null)
				cities.get(m.getTo  ().getCityIndex()).addPerson   (m.getPerson(), m.getTo  ());
		}
		queue.clear();
	}

	public HourlyLog getLastHourlyLog()
	{
		return log.getLastHourly();
	}
	
	public DailyLog getLastDailyLog()
	{
		return log.getLastDaily();
	}

	public void probe()
	{
		HourlyLogger logger = new HourlyLogger(0, 0, 0);
		for(City c : cities)
			c.probe(logger);
		log.pushHour(logger.build(log.maybeGetLastHourly()));
	}

	public CompleteLog getLog()
	{
		return log.build();
	}

}
