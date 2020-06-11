package competition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import competition.app.Competitor;
import competition.app.CompetitorBuilder;

public class BinFinishFile implements FinishFile
{
	private Path path;
	
	public BinFinishFile(Path path)
	{
		this.path = path;
	}

//	@Override
//	public List<CompetitorBuilder> readAll() throws IOException
//	{
//		List<CompetitorBuilder> list = new ArrayList<>();
//		DataInputStream din = new DataInputStream(Files.newInputStream(path));
//		while(din.available() > 0)
//			list.add(CompetitorBuilder.read(din));
//		return list;
//	}
//
//	@Override
//	public void writeAll(List<Competitor> competitor) throws IOException
//	{
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public List<Competitor> readAll(List<CompetitorBuilder> starts) throws IOException
	{
		List<Competitor> list = new ArrayList<>();
		DataInputStream din = new DataInputStream(Files.newInputStream(path));
		while(din.available() > 0)
		{
			final int id = din.readInt();
			Optional<CompetitorBuilder> builder = starts.stream()
				.filter(cb -> cb.is(id)).findFirst();
			if(builder.isPresent())
				list.add(Competitor.read(builder.get(), din));
			
		}
		return list;
	}

	@Override
	public void writeAll(List<Competitor> competitors) throws IOException
	{
		try (DataOutputStream dout = new DataOutputStream(Files.newOutputStream(path)))
		{
			for(Competitor c : competitors)
			{
				LocalTime t = c.getEnd();
				dout.writeInt(c.getId());
				dout.writeInt(t.getHour());
				dout.writeInt(t.getMinute());
				dout.writeInt(t.getSecond());
				dout.writeInt(t.getNano() / 1000);
			}
//			dout.close();
		}
	}
}
