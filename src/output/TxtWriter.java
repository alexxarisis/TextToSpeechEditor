package output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtWriter implements IDocumentWriter{

	@Override
	public void write(String path, String contents) {
		
		try {
			File file = new File(path);
			file.createNewFile();
			
			FileWriter writer = new FileWriter(path);
			writer.write(contents);
			writer.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
