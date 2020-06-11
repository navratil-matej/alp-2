package contamulation.app.simulation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import contamulation.api.Parsers;
import contamulation.api.SimSerializable;
import contamulation.app.behavior.Architecture;
import contamulation.app.behavior.Behavior;
import contamulation.app.behavior.Job;
import contamulation.app.world.Building;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class Registry implements SimSerializable<Registry>
{
	protected Map<String, Architecture> architectures = new HashMap<>();
	protected Map<String, Behavior> behaviors         = new HashMap<>();
	protected Map<String, Job>      jobs              = new HashMap<>();
	
	public Map<String, Architecture> arch()
	{
		return architectures;
	}
	
	public Map<String, Behavior> behaviors()
	{
		return behaviors;
	}
	
	public Map<String, Job> jobs()
	{
		return jobs;
	}
	
	public Architecture getArch(String id)
	{
		return architectures.get(id);
	}
	
	public Behavior getBehavior(String id)
	{
		return behaviors.get(id);
	}
	
	public Job getJob(String id)
	{
		return jobs.get(id);
	}

	@Override
	public void write(SimulationConfig cfg)
	{
		// TODO
		Path arc = cfg.getPath(SimulationConfig.ARCH_LIST_FILE);
		Path beh = cfg.getPath(SimulationConfig.BEHV_LIST_FILE);
		Path job = cfg.getPath(SimulationConfig.JOBS_LIST_FILE);
		FileWriteParser arcWriter = Parsers.writerFor(arc);
		FileWriteParser behWriter = Parsers.writerFor(beh);
		FileWriteParser jobWriter = Parsers.writerFor(job);
		for(Architecture a : architectures.values())
			a.toParser(arcWriter);
		for(Behavior b : behaviors.values())
		{
			b.toParser(behWriter);
			b.write(cfg);
		}
	}

	@Override
	public void read(SimulationConfig cfg)
	{
		// TODO
		Path arc = cfg.getPath(SimulationConfig.ARCH_LIST_FILE);
		Path beh = cfg.getPath(SimulationConfig.BEHV_LIST_FILE);
		Path job = cfg.getPath(SimulationConfig.JOBS_LIST_FILE);
		FileReadParser arcReader = Parsers.readerFor(arc);
		FileReadParser behReader = Parsers.readerFor(beh);
		FileReadParser jobReader = Parsers.readerFor(job);
		while(arcReader.hasNext())
		{
			Architecture a = Architecture.fromParser(arcReader);
			architectures.put(a.getId(), a);
		}
		while(behReader.hasNext())
		{
			Behavior b = Behavior.fromParser(behReader);
			b.read(cfg);
			behaviors.put(b.id(), b);
		}
		while(jobReader.hasNext())
		{
			Job j = Job.fromParser(jobReader);
			jobs.put(j.getIdentifier(), j);
		}
			
	}
}
