package competition.app;

import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class CompetitorBuilder
{
	private String name;
	private String surname;
	private int id;
	private LocalTime time;
	
	public CompetitorBuilder(String name, String surname, int id, LocalTime time)
	{
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.time = time;
	}

	public static CompetitorBuilder parse(Scanner sc)
	{
		final String name = sc.next();
		final String surname = sc.next();
		final int id = sc.nextInt();
		final int h  = sc.nextInt();
		final int m  = sc.nextInt();
		final int s  = sc.nextInt();
		return new CompetitorBuilder(name, surname, id, LocalTime.of(h, m, s));
	}

	public static CompetitorBuilder read(DataInputStream din) throws IOException
	{
		final String name = din.readUTF();
		final String surname = din.readUTF();
		final int id = din.readInt();
		final int h  = din.readInt();
		final int m  = din.readInt();
		final int s  = din.readInt();
		return new CompetitorBuilder(name, surname, id, LocalTime.of(h, m, s));
	}
	
	public Competitor build(LocalTime end)
	{
		return new Competitor(name, surname, id, time, end);
	}

	public boolean is(int id)
	{
		return this.id == id;
	}
}
