package input;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TxtReader implements IDocumentReader {

	@Override
	public String read(String path) {
		
		String contents = "";
		
		try
		{
			BufferedReader reader = Files.newBufferedReader(Paths.get(path));
			contents = reader.lines().collect(Collectors.joining("\n"));
			reader.close();
			
			return contents;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return contents;
	}
}
