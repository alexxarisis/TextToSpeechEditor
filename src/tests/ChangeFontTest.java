package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class ChangeFontTest {
	
	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
	}
	
	@Test
	@DisplayName("Font family changed")
	public void testIfFontFamilyChanged() {
		
		view.getViewMenuFontButton("timesNewRoman").doClick();
		System.out.println(view.getFont().getFontName());
		System.out.println(view.getFont().getStyle());
		System.out.println(view.getFont().getSize());
		
		// 
		assertEquals(true, view.getFont().getFontName().
										equals("Times New Roman"));
	}
}
