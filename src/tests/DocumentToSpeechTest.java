package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;
import model.FakeTTSFacade;

public class DocumentToSpeechTest {

	private AdvancedTextToSpeechView view;
	private FakeTTSFacade audioManager;
	private String text;
	private String selectedText;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		audioManager = new FakeTTSFacade();
		view.getDocument().setAudioManager(audioManager);

		// Like open command
		text = "Hi i'm alex.\nNice to meet you!";
		selectedText = "Hi i'm alex.";
		view.getDocument().setContents(text);
		view.setTextAndLineWrap(text, true);
		view.enableButtons();
	}
	
	@Test
	@DisplayName("9 letters word, playtime < 2 sec")
	public void test9LetterWordPlaysLessThan2sec() {
		// Change the word because we need a < 10 chars one
		// to test if it runs in less than 2 sec
		view.getDocument().setContents("Beautiful");
		
		assertTimeout(ofSeconds(2), () -> {
			view.getToolbarButton("play").doClick();
	    });
	}
	
	@Test
	@DisplayName("Play contents")
	public void testDocToSpeechAll() {
		// trigger the DocumentToSpeech command
		view.getToolbarButton("play").doClick();
		assertEquals(true, audioManager.getPlayedContents().equals(text));		
	}
	
	@Test
	@DisplayName("Play selected contents")
	public void testDocToSpeechSelection() {
		// Selection
		// set caret to start
		view.getTextArea().setCaretPosition(0);
		view.getTextArea().moveCaretPosition(12);
		// choose to use the selected text
		view.getToolbarButton("useSelectedText").doClick();
		
		// trigger the DocumentToSpeech command
		view.getToolbarButton("play").doClick();
		assertEquals(true, audioManager.getPlayedContents().equals(selectedText));
	}
	
}
