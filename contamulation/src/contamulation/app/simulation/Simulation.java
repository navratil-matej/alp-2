package contamulation.app.simulation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;
import java.util.function.Consumer;

import javax.swing.text.html.HTMLEditorKit.Parser;

import contamulation.api.Parsers;
import contamulation.api.SimSerializable;
import contamulation.app.Disease;
import contamulation.app.behavior.Architecture;
import contamulation.app.behavior.Job;
import contamulation.app.results.CompleteLog;
import contamulation.app.results.DailyLog;
import contamulation.app.results.HourlyLog;
import contamulation.app.world.Building;
import contamulation.app.world.City;
import contamulation.app.world.Country;
import contamulation.app.world.Person;
import contamulation.tools.ArrayTools;
import contamulation.tools.files.FileWriteParser;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.Curve;
import contamulation.tools.structs.SimTime;

/**
 * The main class of the application, where simulation information is stored,
 * and simulation can be ran from.
 * @author des
 *
 */
public class Simulation implements SimSerializable<Simulation>
{
	public static final Simulation INSTANCE = new Simulation();
	
	private Country country;

	private SimulationConfig cfg;
	private Random random;
	private Disease  disease  = new Disease() ;
	private Registry registry = new Registry();
	
	private Curve<Job> jobs;
	private Curve<Architecture> archs;
	private Curve<Double> citySizes;
	
	public SimulationConfig getConfig()
	{
		return cfg;
	}
	
	public Country country()
	{
		return country;
	}
	
	public Disease disease()
	{
		return disease;
	}
	
	public Random random()
	{
		return random;
	}
	
	public Registry registry()
	{
		return registry;
	}
	
	public Curve<Job> jobDist()
	{
		return jobs;
	}
	
	public Curve<Architecture> archDist()
	{
		return archs;
	}
	
	public Curve<Double> citySizeDist()
	{
		return citySizes;
	}
	
	/**
	 * Runs the simulation.
	 * @param weeks The number of weeks to run for.
	 * @param hourly Hourly callback.
	 * @param daily Daily callback.
	 */
	public CompleteLog run(int weeks, Consumer<HourlyLog> hourly, Consumer<DailyLog> daily)
	{
		for(int i = 0; i < weeks * 168; i++) // TODO magic number
		{
			final int hour = i % 24;
			final int day  = i / 24 % 7;
			final int week = i / 168;
			country().nextHour(new SimTime(day, hour));
			country().probe();
			hourly.accept(country().getLastHourlyLog());
			if(hour == 23)
				daily.accept(country().getLastDailyLog());
		}
		return country.getLog();
	}
	
	/**
	 * Creates the simulated world returned by country.
	 */
	public void createWorld()
	{
		country = new Country();
		random = new Random();
		final int population = cfg.get(SimulationConfig.POPULATION_SIZE );
		final int nCities    = cfg.get(SimulationConfig.CITY_COUNT      );
		final int nBuildings = cfg.get(SimulationConfig.BUILDING_COUNT  );
		final int nInfected  = cfg.get(SimulationConfig.INITIAL_INFECTED);
		Person[] people = new Person[population];
		double[] size = new double[nCities];
		double sizesum = 0;
		
		// -- city setup
		for(int i = 0; i < nCities; i++)
		{
			size[i] = sizesum;
			sizesum += citySizes.get((i * 1.0) / nCities);
		}
		for(int i = 0; i < nCities; i++)
		{
			country.buildCity((int)(population * size[i] / sizesum));
			if(i > 0)
				country.getCity(i-1).setNeighbor(country.getCity(i));
		}
		country.getCity(nCities-1).setNeighbor(country.getCity(0));
		// -- building setup
		for(int i = 0; i < nBuildings; i++)
		{
			Architecture b = archs.get((i * 1.0) / nBuildings);
			int cityIndex = ArrayTools.binsearchLessEqual(size, sizesum * random.nextDouble());
			country.getCity(cityIndex).build(b);
		}
		// -- jobs setup
		for(int i = 0; i < population; i++)
		{
			people[i] = new Person(jobs.get((i * 1.0) / population));
		}
		ArrayTools.shuffle(people, random());
		// -- work setup
		for(int i = 0; i < population; i++)
		{
			int cityIndex = ArrayTools.binsearchLessEqual(size, i * sizesum / population);
			Address addr = country.getCity(cityIndex).seekFree(people[i].getJob().getArchitecture(), true);
			people[i].setWork(addr);
			people[i].goWork();
			country.applyConcurrent();
		}
		// -- homes setup
		for(int i = 0; i < population; i++)
		{
			int cityIndex = ArrayTools.binsearchLessEqual(size, i * sizesum / population);
			Address addr = country.getCity(cityIndex).seekFreeLiving();
			people[i].setHome(addr);
			people[i].goHome();
			country.applyConcurrent();
		}
		// -- init infection
		for(int i = 0; i < nInfected; i++)
			people[(int)(population * random.nextDouble())].catchDisease();
	}

	@Override
	public void write(SimulationConfig cfg)
	{
		registry.write(cfg);
		disease .write(cfg);
		Path jobs = cfg.getPath(SimulationConfig.JOBS_DIST_FILE );
		Path arch = cfg.getPath(SimulationConfig.ARCH_DIST_FILE );
		Path city = cfg.getPath(SimulationConfig.CITY_SIZES_FILE);
		this.jobs     .toFile(Job         .class, jobs);
		this.archs    .toFile(Architecture.class, arch);
		this.citySizes.toFile(Double      .class, city);
	}

	@Override
	public void read(SimulationConfig cfg)
	{
		registry.read(cfg);
		disease .read(cfg);
		Path jobs = cfg.getPath(SimulationConfig.JOBS_DIST_FILE );
		Path arch = cfg.getPath(SimulationConfig.ARCH_DIST_FILE );
		Path city = cfg.getPath(SimulationConfig.CITY_SIZES_FILE);
		this.jobs      = Curve.fromFile(Job         .class, jobs);
		this.archs     = Curve.fromFile(Architecture.class, arch);
		this.citySizes = Curve.fromFile(Double      .class, city);
	}

	public void loadConfig(Path workingDir, Properties properties)
	{
		cfg = new SimulationConfig(workingDir, properties);
	}
}
