package contamulation.api;

/**
 * An interface enabling simulating time development. 
 * @author des
 *
 */
@FunctionalInterface
public interface TimeSensitive
{
	/**
	 * Simulates the next hour of the simulation.
	 */
	public void nextHour();
}
