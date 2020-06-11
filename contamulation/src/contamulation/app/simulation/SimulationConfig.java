package contamulation.app.simulation;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Java Properties wrapper with default values and compile time properties.
 * @author des
 *
 */
public class SimulationConfig
{
	private Path workingDir;
	private Properties properties;
	
	private static Set<SimulationProperty<?>> propertySet = new HashSet<>();
	
	public static final SimulationProperty<Integer> POPULATION_SIZE
		= register(new SimulationProperty<Integer>("population-size", 10000));
	public static final SimulationProperty<Integer> CITY_COUNT
		= register(new SimulationProperty<Integer>("city-count", 20));
	public static final SimulationProperty<Integer> BUILDING_COUNT
		= register(new SimulationProperty<Integer>("building-count", 100));
	public static final SimulationProperty<Integer> INITIAL_INFECTED
		= register(new SimulationProperty<Integer>("initial-infected", 5));
	public static final SimulationProperty<String> RELATION_COUNT_FILE
		= register(new SimulationProperty<String>("relation-count-file", "relation-count.curve.csv"));
	public static final SimulationProperty<String> CITY_SIZES_FILE
		= register(new SimulationProperty<String>("city-dist-file", "city-sizes.curve.csv"));
	public static final SimulationProperty<String> DISEASE_DIR
		= register(new SimulationProperty<String>("disease-dir", "disease"));
	public static final SimulationProperty<String> DISEASE_EXT
		= register(new SimulationProperty<String>("disease-ext", ".csv"));
	public static final SimulationProperty<String> JOBS_LIST_FILE
		= register(new SimulationProperty<String>("job-list-file", "list.jobs.csv"));
	public static final SimulationProperty<String> JOBS_DIST_FILE
		= register(new SimulationProperty<String>("job-distribution-file", "jobs-distribution.curve.csv"));
	public static final SimulationProperty<String> ARCH_LIST_FILE
		= register(new SimulationProperty<String>("arch-list-file", "list.architectures.csv"));
	public static final SimulationProperty<String> ARCH_DIST_FILE
		= register(new SimulationProperty<String>("arch-distribution-file", "architectures-distribution.curve.csv"));
	public static final SimulationProperty<String> BEHV_LIST_FILE
		= register(new SimulationProperty<String>("behavior-list-file", "list.behaviors.csv"));
	public static final SimulationProperty<String> BEHV_DIR
		= register(new SimulationProperty<String>("behavior-dir", "behavior"));
	public static final SimulationProperty<String> BEHV_EXT//RESULTS_FILE
		= register(new SimulationProperty<String>("behavior-ext", ".csv"));
	public static final SimulationProperty<String> RESULTS_FILE
		= register(new SimulationProperty<String>("results-file", "results.csv"));
	
	public SimulationConfig(Path workingDir, Properties properties)
	{
		this.properties = properties;
		this.workingDir = workingDir;
	}
	
	public Path getWorkingDir()
	{
		return workingDir;
	}
	
	public <T> T get(SimulationProperty<T> property)
	{
		return property.read(properties);
	}
	
	public Path getPath(SimulationProperty<String> property)
	{
		return workingDir.resolve(property.read(properties));
	}
	
	public <T> T set(SimulationProperty<T> property, T value)
	{
		return property.read(properties);
	}
	
	private static <T> SimulationProperty<T> register(SimulationProperty<T> property)
	{
		propertySet.add(property);
		return property;
	}
}
