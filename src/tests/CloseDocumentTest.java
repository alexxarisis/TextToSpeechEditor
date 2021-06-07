package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class CloseDocumentTest {
	
	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		
		// Like open command
		view.getDocument().setContents("Hi I'm alex!");
		view.getDocument().setSelection("alex!");
		view.setTextAndLineWrap("Hi I'm alex!", true);
		view.enableButtons();
	}
	
	@Test
	@DisplayName("Buttons: disabled.")
	public void testButtonsDisable() {
		view.getToolbarButton("close").doClick();
		assertEquals(false, view.getToolbarButton("close").isEnabled());
	}
	
	@Test
	@DisplayName("Document info reseted/deleted.")
	public void testDocumentReset() {
		view.getToolbarButton("close").doClick();
		assertEquals(true, view.getDocument().getName().equals(""));
		assertEquals(true, view.getDocument().getContents().equals(""));
		assertEquals(null, view.getDocument().getSelection());
	}
	
	@Test
	@DisplayName("Text area empty and not editable.")
	public void testTextAreaReset() {
		view.getToolbarButton("close").doClick();
		assertEquals(true, view.getTextArea().getText().equals(""));
		assertEquals(false, view.getTextArea().isEditable());
	}
}
