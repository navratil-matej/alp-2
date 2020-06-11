package contamulation.tools.structs;

public class Address
{
	private static final int LIVING_BUILDING_INDEX = -1;
	private int cityIndex;
	private int buildingIndex;
	private int roomIndex;
	private boolean job;
	
	private Address(int cityIndex, int buildingIndex, int roomIndex, boolean job)
	{
		super();
		this.cityIndex = cityIndex;
		this.buildingIndex = buildingIndex;
		this.roomIndex = roomIndex;
		this.job = job;
	}
	
	public static Address ofLiving(int city, int room)
	{
		return new Address(city, LIVING_BUILDING_INDEX, room, false);
	}
	
	public static Address ofBuilding(int city, int building, int room, boolean job)
	{
		return new Address(city, building, room, job);
	}
	
	public boolean isHousing()
	{
		return getBuildingIndex() == LIVING_BUILDING_INDEX;
	}
	
	public boolean isWorking()
	{
		return job;
	}

	public int getCityIndex()
	{
		return cityIndex;
	}

	public int getBuildingIndex()
	{
		return buildingIndex;
	}

	public int getRoomIndex()
	{
		return roomIndex;
	}

	@Override
	public String toString()
	{
		return String.format("%5d %5d %5d %1d", cityIndex, buildingIndex, roomIndex, isWorking() ? 1 : 0);
	}
}
