package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class ChangeFontSizeTest {
	
	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
	}
	
	@Test
	@DisplayName("Font size changed")
	public void testIfFontSizeChanged() {
		view.getIncreaseFontSizeButton().doClick();
		// was 12
		assertEquals(13, view.getFont().getSize());
	}
}
