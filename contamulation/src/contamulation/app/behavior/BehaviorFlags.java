package contamulation.app.behavior;

import java.util.function.BiPredicate;

import contamulation.app.world.Person;

public enum BehaviorFlags implements BehaviorFlag
{
	AT_WORK  ((b, p) -> p.getBehaviorState() == BehaviorState.AT_WORK),
	ASLEEP   ((b, p) -> p.getBehaviorState() == BehaviorState.ASLEEP ),
	REPEATED ((b, p) -> p.hasToday(b.id())),
	;
	
	private final BiPredicate<Behavior, Person> predicate;
//	private static Map<String, BehaviorFlag> flags;
	
	private BehaviorFlags(BiPredicate<Behavior, Person> predicate)
	{
		this.predicate = predicate;
	}

	@Override
	public boolean matches(Behavior behavior, Person person)
	{
		return predicate.test(behavior, person);
	}

	@Override
	public String id()
	{
		return name().toLowerCase();
	}
}
