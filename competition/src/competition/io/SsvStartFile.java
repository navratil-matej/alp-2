package competition.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import competition.app.Competitor;
import competition.app.CompetitorBuilder;

public class SsvStartFile implements StartFile
{
	private Path path;
	
	public SsvStartFile(Path path)
	{
		this.path = path;
	}

	@Override
	public List<CompetitorBuilder> readAll() throws IOException
	{
		List<CompetitorBuilder> list = new ArrayList<>();
		for(String line : Files.readAllLines(path))
			list.add(CompetitorBuilder.parse(new Scanner(line)));
		return list;
	}

	@Override
	public void writeAll(List<Competitor> competitors) throws IOException
	{
		List<String> lines = new ArrayList<>();
		for(Competitor c : competitors)
		{
			lines.add(c.toString());
		}
		Files.write(path, lines);
	}
}
