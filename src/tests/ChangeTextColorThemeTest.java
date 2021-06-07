package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class ChangeTextColorThemeTest {
	
	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
	}
	
	@Test
	@DisplayName("Color theme: white.")
	public void testColorThemeWhite() {
		view.getBlackThemeButton().doClick();
		
		// we start at black background and white text
		// so when we changed it, text color should be absolute black
		Color textColor = view.getTextArea().getDisabledTextColor();
		assertEquals(0, textColor.getRed());
		assertEquals(0, textColor.getGreen());
		assertEquals(0, textColor.getBlue());
	}
	
	@Test
	@DisplayName("Color theme: black again.")
	public void testColorThemeBackToBlack() {
		// we enter white theme mode
		view.getBlackThemeButton().doClick();
		// and change back to black
		view.getBlackThemeButton().doClick();
		
		// text color should be: Color(245, 245, 250)
		Color textColor = view.getTextArea().getDisabledTextColor();
		assertEquals(245, textColor.getRed());
		assertEquals(245, textColor.getGreen());
		assertEquals(250, textColor.getBlue());
	}
}
