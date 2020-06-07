package contamulation.app.simulation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import contamulation.api.SimSerializable;
import contamulation.app.behavior.Behavior;
import contamulation.app.behavior.Job;
import contamulation.app.world.Building;

public class Registry implements SimSerializable<Registry>
{
	protected Map<String, Building> buildings;
	protected Map<String, Behavior> behaviors;
	protected Map<String, Job>      jobs;
	
	public Map<String, Building> buildings()
	{
		return buildings;
	}
	
	public Map<String, Behavior> behaviors()
	{
		return behaviors;
	}
	
	public Map<String, Job> jobs()
	{
		return jobs;
	}
	
	public Building building(String id)
	{
		return buildings.get(id);
	}
	
	public Behavior behavior(String id)
	{
		return behaviors.get(id);
	}
	
	public Job job(String id)
	{
		return jobs.get(id);
	}

	@Override
	public void write(Properties sim)
	{
		Path bui = Paths.get(sim.getProperty("buildings"));
		Path beh = Paths.get(sim.getProperty("behaviors"));
		Path job = Paths.get(sim.getProperty("jobs"));
	}

	@Override
	public void read(Properties sim)
	{
		// TODO Auto-generated method stub
		
	}
}
