package competition.app;

import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class Competitor
{
	private String name;
	private String surname;
	private int id;
	private LocalTime start;
	private LocalTime end;
	
	public Competitor(String name, String surname, int id, LocalTime start, LocalTime end)
	{
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public static Competitor parse(CompetitorBuilder builder, Scanner sc)
	{
		final int h  = sc.nextInt();
		final int m  = sc.nextInt();
		final int s  = sc.nextInt();
		final int n  = sc.nextInt() * 1000;
		return builder.build(LocalTime.of(h, m, s, n));
	}

	public static Competitor read(CompetitorBuilder builder, DataInputStream din) throws IOException
	{
		final int h  = din.readInt();
		final int m  = din.readInt();
		final int s  = din.readInt();
		final int n  = din.readInt();
		return builder.build(LocalTime.of(h, m, s, n));
	}

	public String getName()
	{
		return name;
	}

	public String getSurname()
	{
		return surname;
	}

	public int getId()
	{
		return id;
	}

	public LocalTime getStart()
	{
		return start;
	}

	public LocalTime getEnd()
	{
		return end;
	}
}
