package contamulation.app.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class City
{
	protected Map<String, List<Building>> buildings;
	protected City next;
	
	public void build(Building building)
	{
		if(!buildings.containsKey(building.type()))
		{
			buildings.put(building.type(), new ArrayList<Building>());
		}
		buildings.get(building.type()).add(building);
	}
	
	public void setNeighbor(City city)
	{
		next = city;
	}
	
	public Building seekFree(String type, boolean job)
	{
		return seekFree(type, job, 10);
	}
	
	public Building seekFree(String type, boolean job, int patience)
	{
		if(buildings.containsKey(type))
			for(Building b : buildings.get(type))
				if(b.canBeEntered(job))
					return b;
		if(patience == 0)
			return null;
		return next.seekFree(type, job, patience - 1);
	}
}
