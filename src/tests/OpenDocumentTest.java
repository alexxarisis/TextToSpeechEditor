package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;
import commands.OpenDocument;

public class OpenDocumentTest {

	private AdvancedTextToSpeechView view;
	private OpenDocument openCommand;
	private String directory;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		openCommand = new OpenDocument(view);
		
		directory = System.getProperty("user.dir") + "\\Resources\\Samples\\tests\\";
	}
	
	// CHECK DIFFERENT EXTENSIONS
	
	@Test
	@DisplayName("Open .txt | No encryption.")
	public void testOpenTxtFile() {
		// set values
		openCommand.setName("plainTxt");
		openCommand.setPath(directory + "plainTxt.txt");
		openCommand.setFileType("txt");
		openCommand.setLinewrap(true);
		openCommand.setEncryption("None");
		
		// load the file through the command (see replay() javadoc)
		openCommand.replay();

		assertEquals(true, view.getDocument().getContents().equals(getPlainTextForTxt()));
	}
	
	@Test
	@DisplayName("Open .docx | No encryption.")
	public void testOpenDocxFile() {
		// set values
		openCommand.setName("plainDocx");
		openCommand.setPath(directory + "plainDocx.docx");
		openCommand.setFileType("docx");
		openCommand.setLinewrap(true);
		openCommand.setEncryption("None");
				
		// load the file through the command (see replay() javadoc)
		openCommand.replay();
				
		assertEquals(true, view.getDocument().getContents().equals(getPlainTextForDocx()));
	}
	
	@Test
	@DisplayName("Open .xlsx | No encryption.")
	public void testOpenXlxsFile() {
		// set values
		openCommand.setName("plainXlsx");
		openCommand.setPath(directory + "plainXlsx.xlsx");
		openCommand.setFileType("xlsx");
		openCommand.setLinewrap(false);
		openCommand.setEncryption("None");
				
		// load the file through the command (see replay() javadoc)
		openCommand.replay();

		assertEquals(true, view.getDocument().getContents().equals(getPlainTextForXlsx()));
	}
	
	// CHECK DECRYPTIONS
	
	@Test
	@DisplayName("Decryption: Atbash.")
	public void testOpenTxtFileAtbash() {
		// set values
		openCommand.setName("atbashTxt");
		openCommand.setPath(directory + "atbashTxt.txt");
		openCommand.setFileType("txt");
		openCommand.setLinewrap(true);
		openCommand.setEncryption("AtBash");
		
		// load the file through the command (see replay() javadoc)
		openCommand.replay();
		
		// check to see if we decrypted the contents correctly.
		assertEquals(true, view.getDocument().getContents().equals(getPlainTextForTxt()));
	}
	
	@Test
	@DisplayName("Decryption: Rot13.")
	public void testOpenTxtFileRot13() {
		// set values
		openCommand.setName("rot13Txt");
		openCommand.setPath(directory + "rot13Txt.txt");
		openCommand.setFileType("txt");
		openCommand.setLinewrap(true);
		openCommand.setEncryption("Rot-13");
		
		// load the file through the command (see replay() javadoc)
		openCommand.replay();
		
		// check to see if we decrypted the contents correctly.
		assertEquals(true, view.getDocument().getContents().equals(getPlainTextForTxt()));
	}
	
	
	// Below are the expected outcomes, depending on the reader.
	
	private String getPlainTextForTxt() {
		return "Hi i'm alex 3361.\n"
				+ "\n"
				+ "This is a test.\n"
				+ "\n"
				+ "##############";
	}
	
	private String getPlainTextForDocx() {
		return "Hi I’m alex 3361.\n"
				+ "This is a test.\n"
				+ "##############\n";
	}

	private String getPlainTextForXlsx() {
		return "Hi	I'm	alex	3361.0	\n"
				+ "This	is	a	test.	\n"
				+ "##############	\n";
	}
	
}