package contamulation.app.world;

import java.util.ArrayList;
import java.util.List;

import contamulation.api.SimLoggable;
import contamulation.api.TimeSensitive;
import contamulation.app.behavior.Architecture;
import contamulation.app.results.HourlyLogger;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.SimTime;

/**
 * A struct representing a building which can be entered and exited by people.
 * @author des
 *
 */
public class Building implements TimeSensitive, SimLoggable
{
	private int cityIndex;
	private int index;
	
	private Architecture arch;
	private List<Room> rooms;
	
	public Building(City city, int index, Architecture arch)
	{
		this.cityIndex = city.index();
		this.index = index;
		this.arch = arch;
		this.rooms = new ArrayList<Room>();
		for(int i = 0; i < arch.roomCount(); i++)
			rooms.add(new Room(
				arch.capacityJob()   / arch.roomCount(),
				arch.capacityOuter() / arch.roomCount(),
				arch.contact()));
	}
	
	public int index()
	{
		return index;
	}
	
	public int getCityIndex()
	{
		return cityIndex;
	}
	
	/**
	 * The total number of workers who can enter the building for work related purposes.
	 * @return The capacity for workers
	 */
	public int capacityJob()
	{
		return arch.capacityJob();
	}

	/**
	 * The total number of visitors who can enter the building for work related purposes.
	 * @return The capacity for visitors
	 */
	public int capacityOuter()
	{
		return arch.capacityOuter();
	}
	
	/**
	 * Checks whether specified room has a free place.
	 * @param address Address to check.
	 * @return whether specified room has a free place.
	 */
	public boolean hasRoom(Address address)
	{
		return rooms.get(address.getRoomIndex()).hasRoom(address);
	}

	/**
	 * Adds person to a room in this building as specified by address.
	 * @param person Person to add.
	 * @param address Address to add to.
	 */
	public void addPerson(Person person, Address address)
	{
		rooms.get(address.getRoomIndex()).addPerson(person, address);
	}
	
	/**
	 * Removes person from a room in this building as specified by address.
	 * @param person Person to remove.
	 * @param address Address to remove from.
	 */
	public void removePerson(Person person, Address address)
	{
		rooms.get(address.getRoomIndex()).removePerson(person, address);
	}
	
	@Override
	public void nextHour(SimTime time)
	{
		for(Room room : rooms)
			room.nextHour(time);
	}

	public Architecture getArchitecture()
	{
		return arch;
	}

	@Override
	public void probe(HourlyLogger logger)
	{
		for(Room r : rooms)
			r.probe(logger);
	}

	/**
	 * Looks for an available room in this building.
	 * @param job Whether to look for a job position.
	 * @return address Address of a found room, or null.
	 */
	public Address seekFree(boolean job)
	{
		for(int i = 0; i < rooms.size(); i++)
			if(rooms.get(i).hasRoom(job))
				return Address.ofBuilding(cityIndex, index, i, job);
		return null;
	}
}
