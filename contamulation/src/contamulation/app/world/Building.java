package contamulation.app.world;

import java.util.ArrayList;
import java.util.List;

import contamulation.api.TimeSensitive;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

/**
 * A struct representing a building which can be entered and exited by people.
 * @author des
 *
 */
public class Building implements TimeSensitive
{
	private String type;
	private List<Room> rooms;
	private int roomCount;
	private double contact;
	
	public Building(String type, int roomCount, int roomJobCap, int roomOutCap, double contact)
	{
		this.type = type;
		this.rooms = new ArrayList<Room>();
		this.contact = contact;
		for(int i = 0; i < roomCount; i++)
			rooms.add(new Room(roomJobCap / roomCount, roomOutCap / roomCount, contact));
	}
	
	public static Building fromParser(FileReadParser parser)
	{
		final String id = parser.read(String.class);
		final int capJob = parser.read(Integer.class);
		final int capOut = parser.read(Integer.class);
		final int roomCo = (capJob + capOut) / parser.read(Integer.class);
		final double contact = parser.read(Double.class);
		return new Building(id, roomCo, capJob, capOut, contact);
	}
	
	public void toParser(FileWriteParser parser)
	{
		parser.write(String .class, type);
		parser.write(Integer.class, capacityJob());
		parser.write(Integer.class, capacityOuter());
		parser.write(Integer.class, (capacityJob() + capacityOuter()) / roomCount);
		parser.write(Double .class, contact);
	}

	public Building cloneBuilding()
	{
		return new Building(type, roomCount, capacityJob(), capacityOuter(), contact);
	}
	
	/**
	 * The total number of workers who can enter the building for work related purposes.
	 * @return The capacity for workers
	 */
	public int capacityJob()
	{
		return rooms.get(0).capacityJob() * roomCount;
	}

	/**
	 * The total number of visitors who can enter the building for work related purposes.
	 * @return The capacity for visitors
	 */
	public int capacityOuter()
	{
		return rooms.get(0).capacityOuter() * roomCount;
	}
	
	public boolean canBeEntered(boolean job)
	{
		for(Room room : rooms)
			if(room.canBeEntered(job))
				return true;
		return false;
	}
	
	public void enteredBy(Person person, boolean job)
	{
		for(Room room : rooms)
			if(room.canBeEntered(job))
			{
				room.enteredBy(person, job);
				return;
			}
	}
	
	public void leftBy(Person person, boolean job)
	{
		for(Room room : rooms)
			if(room.isPresent(person))
			{
				room.leftBy(person, job);
				return;
			}
	}
	
	@Override
	public void nextHour()
	{
		for(Room room : rooms)
			room.nextHour();
	}

	public String type()
	{
		return type;
	}
}
