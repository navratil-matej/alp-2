package competition.io;

import java.io.IOException;
import java.util.List;

import competition.app.Competitor;
import competition.app.CompetitorBuilder;

public interface FinishFile
{
	public List<Competitor> readAll(List<CompetitorBuilder> starts) throws IOException;

	void writeAll(List<Competitor> competitor) throws IOException;
}
