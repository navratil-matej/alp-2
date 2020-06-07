package eleven.common;

@SuppressWarnings("unchecked")
public interface Ruleset<V extends Value, S extends Symbol, C extends Card<V, S>>
{
	public int cardsOnBoard();
	
	public boolean areMatch(C...cards);
	
	public int pointsFor(C card);
}
