package contamulation.app.behavior;

import java.util.Map;

import contamulation.tools.structs.Curve;
import contamulation.tools.structs.Timetable;

public class Behavior
{
	protected String identifier;
	protected String action;
	protected String target;
	protected Timetable<Double> rates;
	protected Curve<Integer> hours;
	protected Map<BehaviorState, Double> flags;
	
	public Behavior(String identifier, String action, String target,
		Timetable<Double> rates, Curve<Integer> hours, Map<BehaviorState, Double> flags)
	{
		super();
		this.identifier = identifier;
		this.action = action;
		this.target = target;
		this.rates = rates;
		this.hours = hours;
		this.flags = flags;
	}
	
	
}
