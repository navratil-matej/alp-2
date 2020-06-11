package contamulation.app.behavior;

import java.nio.file.Path;
import java.util.Map;

import contamulation.api.SimSerializable;
import contamulation.app.simulation.Simulation;
import contamulation.app.simulation.SimulationConfig;
import contamulation.app.world.Person;
import contamulation.tools.FlagTools;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;
import contamulation.tools.structs.Curve;
import contamulation.tools.structs.SimTime;
import contamulation.tools.structs.Timetable;

public class Behavior implements SimSerializable<Behavior>
{
	private String identifier;
	private String action;
	private String target;

	private String ratesName;
	private String hoursName;
	private Timetable<Double> rates;
	private Curve<Integer>    hours;
	
	private Map<BehaviorFlag, Double> flags;
	
	public Behavior(String identifier, String action, String target,
		String ratesName, String hoursName, Map<BehaviorFlag, Double> flags)
	{
		super();
		this.identifier = identifier;
		this.action = action;
		this.target = target;
		this.ratesName = ratesName;
		this.hoursName = hoursName;
		this.rates = new Timetable<>();
		this.hours = new Curve<>();
		this.flags = flags;
	}
	
	public String id()
	{
		return identifier;
	}

	@Override
	public void write(SimulationConfig cfg)
	{
		final Path dir = cfg.getPath(SimulationConfig.BEHV_DIR);
		final String ext = cfg.get(SimulationConfig.BEHV_EXT); 
		Path rat = dir.resolve(ratesName + ".timetable" + ext);
		Path dur = dir.resolve(hoursName + ".curve"     + ext);
		rates.toFile(Double .class, rat);
		hours.toFile(Integer.class, dur);
	}

	@Override
	public void read(SimulationConfig cfg)
	{
		final Path dir = cfg.getPath(SimulationConfig.BEHV_DIR);
		final String ext = cfg.get(SimulationConfig.BEHV_EXT); 
		Path rat = dir.resolve(ratesName + ".timetable" + ext);
		Path dur = dir.resolve(hoursName + ".curve"     + ext);
		rates = Timetable.fromFile(Double .class, rat);
		hours =     Curve.fromFile(Integer.class, dur);
	}

	/**
	 * Method that serialises the behavior to the behaviors list. !! DOES NOT SERIALISE CURVE FILES.
	 * Use {@link Behavior#write} for that.
	 * @param writer writer to write properties into.
	 */
	public void toParser(FileWriteParser writer)
	{
		writer.write(String.class, identifier);
		writer.write(String.class, action);
		writer.write(String.class, target);
		writer.write(String.class, ratesName);
		writer.write(String.class, hoursName);
		writer.write(String.class, FlagTools.serializeMany(flags));
	}

	/**
	 * Method that reads the behavior from the behaviors list. Use {@link Behavior#read} to
	 * read curve and timetable objects.
	 * @param reader reader to reade properties from.
	 */
	public static Behavior fromParser(FileReadParser reader)
	{
		String identifier = reader.read(String.class);
		String action     = reader.read(String.class);
		String target     = reader.read(String.class);
		String ratesName  = reader.read(String.class);
		String hoursName  = reader.read(String.class);
		String flags      = reader.read(String.class);
		return new Behavior(identifier, action, target, ratesName, hoursName,
			FlagTools.parseMany(BehaviorFlag.class, Double.class, flags));
	}

	public Timetable<Double> getChanceTimetable()
	{
		return rates;
	}

	public Curve<Integer> getDurationCurve()
	{
		return hours;
	}

	public void begin(Person person)
	{
		// TODO switch action
		if(person.getLocation() != null)
		{
			person.go(Simulation.INSTANCE.country().getCity(person.getLocation())
				.seekFree(target, false));
			person.busyFor(hours.get(Simulation.INSTANCE.random().nextDouble()));
		}
	}

	public double getChance(Person person, SimTime time)
	{
		double base = rates.get(time);
		for(Map.Entry<BehaviorFlag, Double> flag : flags.entrySet())
			if(flag.getKey().matches(this, person))
				base *= flag.getValue();
		return base;
	}
}
