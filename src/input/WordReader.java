package input;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class WordReader implements IDocumentReader{

	@Override
	public String read(String path) {
		
		String contents = "";
		
		
		try {
			FileInputStream inputStream = new FileInputStream(path);
			BufferedInputStream buffer = new BufferedInputStream(inputStream);
			XWPFDocument  document = new XWPFDocument(buffer);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			
			StringBuilder bobOMastoras = new StringBuilder();
			
			for (XWPFParagraph paragraph : paragraphs) {
				bobOMastoras.append(paragraph.getText());
				bobOMastoras.append("\n");
			}
			buffer.close();
			inputStream.close();
			document.close();
			
			contents = bobOMastoras.toString();
			return contents;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contents;
	}
}
