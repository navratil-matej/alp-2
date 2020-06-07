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
import java.util.Scanner;

import competition.app.Competitor;
import competition.app.CompetitorBuilder;

public class BinStartFile implements StartFile
{
	private Path path;
	
	public BinStartFile(Path path)
	{
		this.path = path;
	}

	@Override
	public List<CompetitorBuilder> readAll() throws IOException
	{
		List<CompetitorBuilder> list = new ArrayList<>();
		DataInputStream din = new DataInputStream(Files.newInputStream(path));
		while(din.available() > 0)
			list.add(CompetitorBuilder.read(din));
		return list;
	}

	@Override
	public void writeAll(List<Competitor> competitor) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

}
