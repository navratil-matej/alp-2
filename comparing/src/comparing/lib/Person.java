package comparing.lib;

import java.text.Collator;
import java.util.Comparator;

public class Person implements Comparable<Person>
{
	private String name;
	private int age;
	private double height;
	private double weight;

	public static final Comparator<Person> BY_NAME = (a, b) -> a.compareTo(b);
	public static final Comparator<Person> BY_AGE = (a, b) -> a.age() - b.age();
	public static final Comparator<Person> BY_HEIGHT = (a, b) -> Double.compare(a.height(), b.height());
	public static final Comparator<Person> BY_WEIGHT = (a, b) -> Double.compare(a.weight(), b.weight());
	
	public Person(String name, int age, double height, double weight)
	{
		super();
		this.name = name;
		this.age = age;
		this.height = height;
		this.weight = weight;
	}

	public String name()
	{
		return name;
	}

	public int age()
	{
		return age;
	}

	public double height()
	{
		return height;
	}

	public double weight()
	{
		return weight;
	}

	@Override
	public int compareTo(Person other)
	{
		return Collator.getInstance().compare(name(), other.name());
	}
}
