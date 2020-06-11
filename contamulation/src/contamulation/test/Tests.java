package contamulation.test;

import java.util.ArrayList;
import java.util.List;

import contamulation.tools.ArrayTools;

public class Tests
{
	public static void main(String[] args) throws Exception
	{
		List<Double> doubles = new ArrayList<>();
		doubles.add(0.1);
		doubles.add(0.3);
		doubles.add(0.7);
		doubles.add(0.8);
		doubles.add(1.0);
		System.out.println(ArrayTools.binsearchLessEqual(doubles, 0.6));
		
//		City[] cities = new City[3];
//		cities[0] = new City(15000);
//		cities[1] = new City(12000);
//		cities[2] = new City( 5000);
//		
//		HourlyLog[] hourly = new HourlyLog[24];
//		for(int i = 0; i < 24; i++)
//		{
//			final int total = 100000;
//			final int rec = 4*i*i*i;
//			final int inf = 4*23*i*i - rec;
//			hourly[i] = new HourlyLog(
//				i == 0 ? Optional.empty() : Optional.of(hourly[i-1]),
//					total - inf - rec, inf, rec);
//			UiSim.printHour(hourly[i]);
//		}
//		UiSim.printDay(new DailyLog(Optional.empty(), hourly));
//		SimTime st = SimTime.fromString("Mon@20");
//		int i = 27;
//		String str = "hi";
//		FileWriteParser wp = new BinWriteParser(Paths.get("resources/test/egg.bin"));
//		wp.write(SimTime.class, st);
//		wp.write(Integer.class, i);
//		wp.write(String .class, str);
//		wp.writeToFile();
//		FileReadParser rp = new BinReadParser(Paths.get("resources/test/egg.bin"));
//		System.out.println(rp.read(SimTime.class));
//		System.out.println(rp.read(Integer.class));
//		System.out.println(rp.read(String .class));
		
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
