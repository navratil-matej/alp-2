package contamulation.app.behavior;

import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;

public class Architecture
{
	private String id;
	private int roomCount;
	private int jobCap;
	private int outerCap;
	private double contact;
	
	public Architecture(String id, int roomCount, int jobCap, int outerCap, double contact)
	{
		super();
		this.id = id;
		this.roomCount = roomCount;
		this.jobCap = jobCap;
		this.outerCap = outerCap;
		this.contact = contact;
	}
	
	public static Architecture fromParser(FileReadParser parser)
	{
		final String id = parser.read(String.class);
		final int capJob = parser.read(Integer.class);
		final int capOut = parser.read(Integer.class);
		final int roomCo = (capJob + capOut) / parser.read(Integer.class);
		final double contact = parser.read(Double.class);
		return new Architecture(id, roomCo, capJob, capOut, contact);
	}
	
	public void toParser(FileWriteParser parser)
	{
		parser.write(String .class, id);
		parser.write(Integer.class, jobCap);
		parser.write(Integer.class, outerCap);
		parser.write(Integer.class, (jobCap + outerCap) / roomCount);
		parser.write(Double .class, contact);
	}

	public String getId()
	{
		return id;
	}

	public double contact()
	{
		return contact;
	}

	/**
	 * The total number of workers who can enter the building for work related purposes.
	 * @return The capacity for workers
	 */
	public int capacityOuter()
	{
		return outerCap;
	}

	/**
	 * The total number of visitors who can enter the building for work related purposes.
	 * @return The capacity for visitors
	 */
	public int capacityJob()
	{
		return jobCap;
	}

	public int roomCount()
	{
		return roomCount;
	}
}
