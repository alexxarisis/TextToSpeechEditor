package output;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordWriter implements IDocumentWriter{

	@Override
	public void write(String path, String contents) {
		
		// blank document
		XWPFDocument document = new XWPFDocument();
		
		// create paragraph
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(contents);
		
		try {
			document.write(new FileOutputStream(new File(path)));;
			document.createParagraph();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
