package contamulation.api;

import contamulation.app.results.HourlyLog;
import contamulation.app.results.HourlyLogger;

/**
 * An object which can be probed to construct an {@link HourlyLog}
 * @author des
 */
@FunctionalInterface
public interface SimLoggable
{
	public void probe(HourlyLogger logger);
}
