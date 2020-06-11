package contamulation.api;

import contamulation.app.simulation.SimulationConfig;

/**
 * Denotes an object which can be loaded from a sim.properties file.
 * @author des
 *
 * @param <T> The type extending this iface.
 */
public interface SimSerializable<T extends SimSerializable<T>>
{
	public void write(SimulationConfig cfg);
	
	public void read(SimulationConfig cfg);
}
