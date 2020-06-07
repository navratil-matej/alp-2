package contamulation.app.simulation;

import java.util.Random;

import contamulation.app.Disease;
import contamulation.app.behavior.Job;
import contamulation.app.world.Building;
import contamulation.app.world.City;
import contamulation.app.world.Country;
import contamulation.app.world.Person;
import contamulation.tools.ArrayTools;
import contamulation.tools.structs.Curve;

/**
 * The main class of the application, where simulation information is stored,
 * and simulation can be ran from.
 * @author des
 *
 */
public class Simulation
{
	public static final Simulation INSTANCE = new Simulation();
	
	private Country country;
	private Disease disease;
	private Random random;
	private Registry registry;
	
	private Curve<Job> jobs;
	private Curve<Building> buildings;
	private Curve<Double> citySizes;
	
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
	
	public Curve<Building> buildingDist()
	{
		return buildings;
	}
	
	public Curve<Double> citySizeDist()
	{
		return citySizes;
	}
	
	public void createWorld(int population, int nCities, int nBuildings)
	{
		Person[] people = new Person[population];
		Job[] jobArr = new Job[population];
		City[] cities = new City[nCities];
		double[] size = new double[nCities];
		double sizesum = 0;
		for(int i = 0; i < population; i++)
		{
			people[i] = new Person(jobs.get((i * 1.0) / population));
		}
		for(int i = 0; i < nCities; i++)
		{
			cities[i] = new City();
			size[i] = sizesum;
			sizesum += citySizes.get((i * 1.0) / nCities);
		}
		for(int i = 0; i < nBuildings; i++)
		{
			Building b = buildings.get((i * 1.0) / nBuildings);
			int j = ArrayTools.binsearchLessEqual(size, sizesum * random.nextDouble());
			cities[j].build(b);
		}
		ArrayTools.shuffle(people, random());
	}
}
