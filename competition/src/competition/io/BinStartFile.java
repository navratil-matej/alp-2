package competition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
	public void writeAll(List<CompetitorBuilder> builders) throws IOException
	{
		try (DataOutputStream dout = new DataOutputStream(Files.newOutputStream(path)))
		{
			for(CompetitorBuilder b : builders)
				b.write(dout);
//			dout.close();
		}
	}
}
