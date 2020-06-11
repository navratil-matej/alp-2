package competition.io;

import java.io.IOException;
import java.util.List;

import competition.app.Competitor;
import competition.app.CompetitorBuilder;

public interface StartFile
{
	public List<CompetitorBuilder> readAll() throws IOException;

	void writeAll(List<CompetitorBuilder> builders) throws IOException;
}
