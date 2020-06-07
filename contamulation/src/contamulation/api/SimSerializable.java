package contamulation.api;

import java.util.Properties;

/**
 * Denotes an object which can be loaded from a sim.properties file.
 * @author des
 *
 * @param <T> The type extending this iface.
 */
public interface SimSerializable<T extends SimSerializable<T>>
{
	public void write(Properties sim);
	
	public void read(Properties sim);
}
