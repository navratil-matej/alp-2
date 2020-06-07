package contamulation.test;

import java.nio.file.Paths;

import contamulation.tools.files.BinReadParser;
import contamulation.tools.files.BinWriteParser;
import contamulation.tools.files.FileReadParser;
import contamulation.tools.files.FileWriteParser;
import contamulation.tools.structs.SimTime;

public class Tests
{
	public static void main(String[] args) throws Exception
	{
		SimTime st = SimTime.fromString("Mon@20");
		int i = 27;
		String str = "hi";
		FileWriteParser wp = new BinWriteParser(Paths.get("resources/test/egg.bin"));
		wp.write(SimTime.class, st);
		wp.write(Integer.class, i);
		wp.write(String .class, str);
		wp.writeToFile();
		FileReadParser rp = new BinReadParser(Paths.get("resources/test/egg.bin"));
		System.out.println(rp.read(SimTime.class));
		System.out.println(rp.read(Integer.class));
		System.out.println(rp.read(String .class));
		
		// -- timetable test
//		Timetable<Double> culture_rate = Timetable.fromFile(Double.class,
//			Paths.get("resources/test/curves/behavior/culture-rate.timetable.csv"));
//		Timetable<Double> injury_rate = Timetable.fromFile(Double.class,
//			Paths.get("resources/test/curves/behavior/injury-rate.timetable.csv"));
//		Timetable<Double> shopping_rate = Timetable.fromFile(Double.class,
//			Paths.get("resources/test/curves/behavior/shopping-rate.timetable.csv"));
//		Random rand = new Random();
//		for(int i = 0; i < 24*7*4; i++)
//		{
//			SimTime time = new SimTime((i / 24) % 7, i % 24);
//			if(rand.nextDouble() < culture_rate.get(time))
//				System.out.println(time + ": culture");
//			if(rand.nextDouble() < injury_rate.get(time))
//				System.out.println(time + ": injury");
//			if(rand.nextDouble() < shopping_rate.get(time))
//				System.out.println(time + ": shopping");
//		}
	}
}
