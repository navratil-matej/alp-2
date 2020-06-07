package eleven.common;

public interface Game<V extends Value, S extends Symbol, C extends Card<V, S>>
{
	public Deck<V, S, C> deck();
	
	public Ruleset<V, S, C> ruleset();
	
	public boolean canPlay();
	
	public C[] board();
}
