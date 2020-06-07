package eleven.common;

public interface Deck<V extends Value, S extends Symbol, C extends Card<V, S>>
{
	public void shuffle(Shuffler<C> shuffler);
	
	public int remaining();
	
	public C take();
}
