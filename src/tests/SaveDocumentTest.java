package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;
import commands.SaveDocument;

public class SaveDocumentTest {
	
	private AdvancedTextToSpeechView view;
	private SaveDocument saveCommand;
	private String directory;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		saveCommand = new SaveDocument(view);
		
		directory = System.getProperty("user.dir") + "\\Resources\\Samples\\tests\\";
		String text = "Hi i'm alex.\nNice to meet you!";
		// Like open command
		view.getDocument().setContents(text);
		view.setTextAndLineWrap(text, true);
		view.enableButtons();
	}
	
	// CHECK DIFFERENT EXTENSIONS
	
	@Test
	@DisplayName("Save .txt | No encryption.")
	public void testSaveTxtFile() {
		// set values
		saveCommand.setName("savedPlainTxt");
		saveCommand.setPath(directory);
		saveCommand.setFileType(".txt");
		saveCommand.setEncryption("None");
		
		// load the file through the command (see replay() javadoc)
		saveCommand.replay();

		String output = getTextFromTxtFile("savedPlainTxt", ".txt");
		assertEquals(true, view.getDocument().getContents().equals(output));
	}
	
	@Test
	@DisplayName("Save .docx | No encryption.")
	public void testSaveWordFile() {
		// set values
		saveCommand.setName("savedDocx");
		saveCommand.setPath(directory);
		saveCommand.setFileType(".docx");
		saveCommand.setEncryption("None");
		
		// load the file through the command (see replay() javadoc)
		saveCommand.replay();

		String output = getTextFromDocxFile(directory + "savedDocx.docx");
		assertEquals(true, view.getDocument().getContents().equals(output));
	}
	
	@Test
	@DisplayName("Save .xlsx | No encryption.")
	public void testSaveXlsxFile() {
		// set values
		saveCommand.setName("savedXlsxTxt");
		saveCommand.setPath(directory);
		saveCommand.setFileType(".xlsx");
		saveCommand.setEncryption("None");
		
		// load the file through the command (see replay() javadoc)
		saveCommand.replay();
		
		// only works with strip. I don't know what white chars it uses otherwise.
		String output = getTextFromXlsxFile(directory + "savedXlsxTxt.xlsx").strip();
		String contents = view.getDocument().getContents().strip();
		assertEquals(true, contents.equals(output));
		
	}
	// CHECK ENCRYPTIONS
	
	@Test
	@DisplayName("Encryption: Atbash")
	public void testSaveTxtFileAtbash() {
		// set values
		saveCommand.setName("savedAtbashTxt");
		saveCommand.setPath(directory);
		saveCommand.setFileType(".txt");
		saveCommand.setEncryption("AtBash");
		
		// load the file through the command (see replay() javadoc)
		saveCommand.replay();

		String docEncoded = encodeAtbash(view.getDocument().getContents());
		String output = getTextFromTxtFile("savedAtbashTxt", ".txt");
		assertEquals(true, docEncoded.equals(output));
	}
	
	@Test
	@DisplayName("Encryption: Rot-13")
	public void testSaveTxtFileRot13() {
		// set values
		saveCommand.setName("savedRot-13Txt");
		saveCommand.setPath(directory);
		saveCommand.setFileType(".txt");
		saveCommand.setEncryption("Rot-13");
		
		// load the file through the command (see replay() javadoc)
		saveCommand.replay();
		
		String docEncoded = encodeRot13(view.getDocument().getContents());
		String output = getTextFromTxtFile("savedRot-13Txt", ".txt");
		assertEquals(true, docEncoded.equals(output));
	}
	
	
	// Below are methods to encrypt strings
	private String encodeAtbash(String contents) {
		
		return contents.chars()
				.map(cp -> Character.isUpperCase(cp) ? 'Z' - (cp - 'A')
						: Character.isLowerCase(cp) ? 'z' - (cp - 'a') : cp)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
	
	private String encodeRot13(String contents) {
		StringBuilder encoded = new StringBuilder();
		
		int len = contents.length();
		for (int i = 0; i < len; i++) {
            char c = contents.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            encoded.append(c);
        }
		return encoded.toString();
	}
	
	// Below are methods to open the saved files
	
	/**
	 * Returns the contents of the specified file
	 * 
	 * @param name Name of the file
	 * @param extension Extension of the file like '.txt' or '.docx'
	 * @return String its contents
	 */
	private String getTextFromTxtFile(String name, String extension) {
		String contents = "";
		String path = directory + name + extension;
		
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(path));
			contents = reader.lines().collect(Collectors.joining("\n"));
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contents;
	}
	
	/**
	 * Returns the contents as is, of the specified file
	 * 
	 * @param path Absolute path, including filename and extension
	 * @return Its contents
	 */
	private String getTextFromDocxFile(String path) {
		String contents = "";
		
		
		try {
			FileInputStream inputStream = new FileInputStream(path);
			BufferedInputStream buffer = new BufferedInputStream(inputStream);
			XWPFDocument  document = new XWPFDocument(buffer);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			
			StringBuilder bobOMastoras = new StringBuilder();
			
			for (XWPFParagraph paragraph : paragraphs)
				bobOMastoras.append(paragraph.getText());
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
	
	/**
	 * Returns the contents as is, of the specified file
	 * 
	 * @param path Absolute path, including filename and extension
	 * @return Its contents
	 */
	private String getTextFromXlsxFile(String path) {
		String contents = "";
		
		try {
			FileInputStream inputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			StringBuilder builder = new StringBuilder();
			
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
				builder.append(row.getCell(0).toString());
				builder.append("\n");
			}
			workbook.close();
			
			contents = builder.toString();
			return contents;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return contents;
	}
}
