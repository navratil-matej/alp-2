package contamulation.tools.structs;

import java.util.Map;

public class Kvp<K, V> implements Map.Entry<K, V>
{
	protected K key;
	protected V val;

	public Kvp(K key, V val)
	{
		this.key = key;
		this.val = val;
	}

	@Override
	public K getKey()
	{
		return key;
	}

	@Override
	public V getValue()
	{
		return val;
	}

	@Override
	public V setValue(V value)
	{
		V prev = val;
		val = value;
		return prev;
	}

}
