package contamulation.app.simulation;

import java.util.Properties;

public class SimulationProperty<T>
{
	private final String key;
	private final T defaultVal;
	
	public SimulationProperty(String key, T defaultVal)
	{
		super();
		this.key = key;
		this.defaultVal = defaultVal;
	}
	
	@SuppressWarnings("unchecked")
	public T read(Properties properties)
	{
		if(properties.contains(key))
		{
			return (T) properties.get(key);
		}
		return defaultVal;
	}
}
