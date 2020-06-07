package contamulation.app.world;

import contamulation.api.TimeSensitive;
import contamulation.app.behavior.BehaviorState;
import contamulation.app.behavior.InfectionState;
import contamulation.app.behavior.Job;
import contamulation.app.simulation.Simulation;

public class Person implements TimeSensitive
{
	protected InfectionState infection = InfectionState.SUSCEPTIBLE;
	protected BehaviorState  behavior  = BehaviorState.FREE;
	
	protected Job job;
	protected Building homeBuilding;
	protected Building  jobBuilding;
	
	private static final int WORK_HOUR_START  = 7 ;
	private static final int WORK_HOUR_END    = 15;
	private static final int SLEEP_HOUR_START = 23;
	private static final int SLEEP_HOUR_END   = 6 ;
	
	public Person(Job job)
	{
		this.job = job;
	}
	
	public boolean susceptible()
	{
		return infection.susceptible();
	}
	
	public boolean infectious()
	{
		return infection.infectious();
	}
	
	public void catchDisease()
	{
		double chance = Simulation.INSTANCE.disease().getSymptoms();
		boolean symptoms = Simulation.INSTANCE.random().nextDouble() < chance; 
		infection = symptoms ? InfectionState.INFECTIOUS : InfectionState.SYMPTOMLESS;
	}
	
	public boolean transmit(Person other)
	{
		if(infectious() && other.susceptible())
		{
			other.catchDisease();
			return true;
		}
		return false;
	}

	@Override
	public void nextHour()
	{
		tryBehavior();
		if(infection == InfectionState.INFECTIOUS && Simulation.INSTANCE.random().nextDouble() < 0.1)
		{
			
		}
		// TODO Auto-generated method stub
	}

	private void tryBehavior()
	{
		// TODO Auto-generated method stub
		
	}
}
