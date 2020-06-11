package contamulation.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import contamulation.app.behavior.BehaviorFlag;
import contamulation.app.behavior.BehaviorFlags;
import contamulation.app.behavior.BehaviorState;
import contamulation.app.flags.BuildingFlags;
import contamulation.tools.structs.Kvp;

/**
 * Utility class for parsing flags, like "--asleep=0.0" or "--livable".
 * @author des
 *
 */
public final class FlagTools
{
	protected static Map<Class<?>, Function<String, ?>> parsers = new HashMap<>();
	
	static
	{
		parsers.put(BehaviorFlag.class, (str) -> BehaviorFlags.valueOf(str.toUpperCase())); // TODO
//		parsers.put(BuildingFlags.class, (str) -> BuildingFlags.valueOf(str.toUpperCase()));
		parsers.put(Double.class, (str) -> Double.parseDouble(str));
		parsers.put(Integer.class, (str) -> Integer.parseInt(str));
		parsers.put(String.class, (str) -> str);
	}
	
	public static <K, V> Map.Entry<K, V> parse(Class<K> keyType, Class<V> valType, String str)
	{
		if(!str.startsWith("--"))
			throw new IllegalArgumentException("Not a flag; should start with --: " + str);
		if(!parsers.containsKey(keyType) || !parsers.containsKey(valType))
			throw new IllegalArgumentException("Cannot parse type: " + keyType + ", or: " + valType);
		str = str.substring(2);
		String[] split = str.split("=");
		K key = keyType.cast(parsers.get(keyType).apply(split[0]));
		V val = valType.cast(parsers.get(valType).apply(split[1]));
		return new Kvp<>(key, val);
	}
	
	public static <K, V> Map<K, V> parseMany(Class<K> keyType, Class<V> valType, String str)
	{
		Map<K, V> map = new HashMap<>();
		String[] split = str.split(" +");
		for(String arg : split)
		{
			Map.Entry<K, V> flag = parse(keyType, valType, arg);
			map.put(flag.getKey(), flag.getValue());
		}
		return map;
	}
	
	public static <K, V> String serializeMany(Map<K, V> flags)
	{
		StringBuilder sb = new StringBuilder();
		for(Entry<K, V> kvp : flags.entrySet())
			sb.append(" --").append(kvp.getKey  ().toString().toLowerCase())
				.append("=").append(kvp.getValue().toString().toLowerCase());
		return sb.substring(1);
	}
}
