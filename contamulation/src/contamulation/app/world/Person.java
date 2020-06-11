package contamulation.app.world;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import contamulation.api.TimeSensitive;
import contamulation.app.behavior.Behavior;
import contamulation.app.behavior.BehaviorState;
import contamulation.app.behavior.InfectionState;
import contamulation.app.behavior.Job;
import contamulation.app.simulation.Simulation;
import contamulation.tools.structs.Address;
import contamulation.tools.structs.SimTime;

public class Person implements TimeSensitive
{
	private InfectionState infection = InfectionState.SUSCEPTIBLE;
	private BehaviorState  behavior  = BehaviorState.FREE;

	private List<String> behaviorsToday = new ArrayList<>();
//	private int behaviorHoursTotal   = 0;
	private int behaviorHoursRemain  = 0;
	private int infectionHoursTotal  = 0;
	private int infectionHoursRemain = 0;
	
	private Job job;
	private Address home;
	private Address work;
	private Address location;
	
	private static final int WORK_HOUR_START  = 7 ;
	private static final int WORK_HOUR_END    = 15;
	private static final int SLEEP_HOUR_START = 23;
	private static final int SLEEP_HOUR_END   = 6 ;
	
	public Person(Job job)
	{
		this.job  = job ;
	}
	
	/**
	 * Sets this person's home to the given address.
	 * @param home The new home address.
	 */
	public void setHome(Address home)
	{
		this.home = home;
	}

	/**
	 * Sets this person's work to the given address.
	 * @param work The new work address.
	 */
	public void setWork(Address work)
	{
		this.work = work;
	}
	
	/**
	 * See {@link Person#go}.
	 */
	public void goHome()
	{
		go(home);
	}

	
	/**
	 * See {@link Person#go}.
	 */
	public void goWork()
	{
		go(work);
	}
	
	/**
	 * Logs a concurrent request to move between rooms. Must be approved by {@link Country#applyConcurrent}.
	 * @param address The address to move to.
	 */
	public void go(Address address)
	{
		Simulation.INSTANCE.country().movePerson(this, location, address);
		location = address;
	}
	
	public boolean isSusceptible()
	{
		return infection.susceptible();
	}
	
	public boolean isInfectious()
	{
		return infection.infectious();
	}
	
	/**
	 * Enters incubation phase.
	 */
	public void catchDisease()
	{
		infection = InfectionState.INCUBATION;
		infectionHoursTotal = Simulation.INSTANCE.disease().getIncubationTimeCurve()
			.get(Simulation.INSTANCE.random().nextDouble());
		infectionHoursRemain = infectionHoursTotal;
	}
	
	/**
	 * Transmits disease to another person.
	 * @param other The other person.
	 * @return whether successful.
	 */
	public boolean transmit(Person other)
	{
		if(other.isSusceptible())
		{
			other.catchDisease();
			return true;
		}
		return false;
	}

	@Override
	public void nextHour(SimTime time)
	{
		final Simulation sim = Simulation.INSTANCE;
		
		// -- behavior
		if(infection != InfectionState.QUARANTINED)
		{
			tryBehavior(time);
			tryRoutine(time);
			if(infection == InfectionState.INFECTIOUS &&
				sim.random().nextDouble() < sim.disease().getDiagnosisChanceTimetable().get(time))
			{
				goHome();
				infection = InfectionState.QUARANTINED;
			}
		}
		
		// -- countdowns
		infectionHoursRemain = Math.max(infectionHoursRemain - 1, 0);
		behaviorHoursRemain  = Math.max(behaviorHoursRemain  - 1, 0);
		if(infectionHoursRemain == 0 && infectionHoursTotal > 0)
		{
			if(infection == InfectionState.INCUBATION)
			{
				infection = rollSymptoms();
				infectionHoursTotal = sim.disease().getRecoveryTimeCurve()
					.get(sim.random().nextDouble());
				behaviorHoursRemain = infectionHoursTotal;
			}
			else if(infection.infectious())
			{
				infection = InfectionState.RECOVERED;
				infectionHoursTotal = 0;
			}
		}
		if(behaviorHoursRemain == 0 && behavior == BehaviorState.BUSY)
		{
			behavior = BehaviorState.FREE;
		}
	}

	private InfectionState rollSymptoms()
	{
		final Simulation sim = Simulation.INSTANCE;
		boolean symptoms = sim.disease().getSymptomsChance() < sim.random().nextDouble();
		return symptoms ? InfectionState.INFECTIOUS : InfectionState.SYMPTOMLESS;
	}

	public BehaviorState getBehaviorState()
	{
		return behavior;
	}
	
	public InfectionState getInfectionState()
	{
		return infection;
	}
	
	public double getInfectionProgress()
	{
		if(infection == InfectionState.INCUBATION)
			return 0.5 * infectionHoursRemain / infectionHoursTotal;
		if(infection.infectious())
			return 0.5 + 0.5 * infectionHoursRemain / infectionHoursTotal;
		return 0;
	}
	
	public boolean hasToday(String behaviorId)
	{
		return behaviorsToday.contains(behaviorId);
	}

	private void tryBehavior(SimTime time)
	{
		final Simulation sim = Simulation.INSTANCE;
		if(behavior != BehaviorState.BUSY)
		{
			for(Behavior b : sim.registry().behaviors().values())
			{
				if(b.getChance(this, time) > sim.random().nextDouble())
				{
					behavior = BehaviorState.BUSY;
					b.begin(this);
				}
			}
		}
	}
	
	private void tryRoutine(SimTime time)
	{
		if(time.getHour() == WORK_HOUR_START && time.getDay() < 5 && behavior == BehaviorState.FREE)
		{ // TODO magic
			goWork();
			behavior = BehaviorState.AT_WORK;
		}
		if(time.getHour() == WORK_HOUR_END && behavior == BehaviorState.AT_WORK)
		{
			goHome();
			behavior = BehaviorState.FREE;
		}
		if(time.getHour() == SLEEP_HOUR_START && behavior == BehaviorState.FREE)
		{
			behavior = BehaviorState.ASLEEP;
		}
		if(time.getHour() == SLEEP_HOUR_END && behavior == BehaviorState.ASLEEP)
		{
			goHome();
			behavior = BehaviorState.FREE;
		}
		if(time.getHour() == 23) // TODO magic
		{
			behaviorsToday.clear();
		}
	}

	public Address getLocation()
	{
		return location;
	}

	public Job getJob()
	{
		return job;
	}

	public void busyFor(int hours)
	{
		behavior = BehaviorState.BUSY;
		behaviorHoursRemain = hours;
	}
}
