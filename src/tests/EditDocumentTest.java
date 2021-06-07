package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class EditDocumentTest {
	
	private AdvancedTextToSpeechView view;
	private String text;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		
		text = "Hi i'm alex.\nNice to meet you!";
		// Like open command
		view.getDocument().setContents(text);
		view.setTextAndLineWrap(text, true);
		view.enableButtons();
	}
	
	// Check if JTextArea is editable or not
	
	@Test
	@DisplayName("JTextArea becomes editable on 1st click.")
	public void testEditButtonToggled() {
		view.getToolbarButton("edit").doClick();	// enter edit mode
		assertEquals(true, view.getTextArea().isEditable());
	}
	
	@Test
	@DisplayName("JTextArea becomes uneditable on 2nd click.")
	public void testEditButtonUntoggle() {
		view.getToolbarButton("edit").doClick();	// enter edit mode
		view.getToolbarButton("edit").doClick();	// exit edit mode
		assertEquals(false, view.getTextArea().isEditable());
	}
	
	// Check if when we finish editing the file
	// the contents are changed
	@Test
	@DisplayName("JTextArea becomes editable on 1st click.")
	public void testDocumentContentsAfterEdit() {
		view.getToolbarButton("edit").doClick();	// enter edit mode
		
		text = "Hi i'm alex.";
		view.setTextAndLineWrap(text, false);		// set new text on JTextArea
		view.getToolbarButton("edit").doClick();	// exit edit mode
		
		assertEquals(true, view.getDocument().getContents().equals(text));
	}
	
}
