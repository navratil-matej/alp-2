package contamulation.app.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import contamulation.api.SimLoggable;
import contamulation.api.TimeSensitive;
import contamulation.app.behavior.Architecture;
import contamulation.app.results.HourlyLogger;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.SimTime;

public class City implements TimeSensitive, SimLoggable
{
	private int index;
	
	private List<Building> buildings                  = new ArrayList<>();
	private Map<String, List<Integer>> byArchitecture = new HashMap<>();
	private List<Room> housing                        = new ArrayList<>();
	private int population;
	private City next;
	
	public City(int index, int pops, int rooms)
	{
		this.index = index;
		double popsPerRoom = (1.0 * pops) / rooms + 0.02; // TODO magic
		for(int i = 0; i < rooms; i++)
			housing.add(new Room(0, (int)(popsPerRoom * (i+1)) - (int)(popsPerRoom * i), 0.4));
	}
	
	public void build(Architecture arch)
	{
		if(!byArchitecture.containsKey(arch.getId()))
		{
			byArchitecture.put(arch.getId(), new ArrayList<Integer>());
		}
		final int ind = buildings.size();
		byArchitecture.get(arch.getId()).add(ind);
		buildings.add(new Building(this, ind, arch));
	}

	public int index()
	{
		return index;
	}
	
	public void setNeighbor(City city)
	{
		next = city;
	}
	
	public Address seekFreeLiving()
	{
		for(int i = 0; i < housing.size(); i++)
			if(housing.get(i).hasRoom(false))
				return Address.ofLiving(index, i);
		return null;
	}
	
	public Address seekFree(String arch, boolean job)
	{
		return seekFree(arch, job, 10);
	}
	
	public Address seekFree(String arch, boolean job, int patience)
	{
		if(byArchitecture.containsKey(arch))
		{
			for(int i : byArchitecture.get(arch))
			{
				Address address = buildings.get(i).seekFree(job);
				if(address != null)
					return address;
			}
		}
		if(patience == 0)
			return null;
		return next.seekFree(arch, job, patience - 1);
	}
	
	public void addPerson(Person person, Address address)
	{
		if(address.isHousing())
			housing.get(address.getRoomIndex()).addPerson(person, address);
		else
			buildings.get(address.getBuildingIndex()).addPerson(person, address);
	}

	public void removePerson(Person person, Address address)
	{
		if(address.isHousing())
			housing.get(address.getRoomIndex()).removePerson(person, address);
		else
			buildings.get(address.getBuildingIndex()).removePerson(person, address);
	}

	@Override
	public void nextHour(SimTime time)
	{
		for(Building b : buildings)
			b.nextHour(time);
		for(Room r     : housing)
			r.nextHour(time);
	}

	@Override
	public void probe(HourlyLogger logger)
	{
		for(Building b : buildings)
			b.probe(logger);
		for(Room r     : housing)
			r.probe(logger);
	}
}
