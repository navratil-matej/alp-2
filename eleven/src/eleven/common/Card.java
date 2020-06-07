package eleven.common;

public interface Card<V extends Value, S extends Symbol>
{
	public V value();
	
	public S getSymbol();
}
