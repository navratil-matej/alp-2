package competition.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
	public void writeAll(Competitor competitor)
	{
		// TODO Auto-generated method stub
		
	}

}
