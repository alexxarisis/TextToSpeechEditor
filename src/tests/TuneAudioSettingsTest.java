package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;
import model.FakeTTSFacade;

public class TuneAudioSettingsTest {
	
	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();
		view.getDocument().setAudioManager(new FakeTTSFacade());
	}
	
	@Test
	@DisplayName("Volume: changed")
	public void testVolumeChanged() {
		// Change the slider -> triggers command
		view.getAudioSettings("volume").setValue(10);
		// was 1.0
		assertEquals(10, view.getDocument().getAudioManager().getVolume());
	}
	
	@Test
	@DisplayName("Pitch: changed")
	public void testPitchChange() {
		// Change the slider -> triggers command
		view.getAudioSettings("pitch").setValue(150);
		// was 100.0
		assertEquals(150, view.getDocument().getAudioManager().getPitch());
	}
	
	@Test
	@DisplayName("Rate: changed")
	public void testRateChange() {
		// Change the slider -> triggers command
		view.getAudioSettings("rate").setValue(300);
		System.out.println(view.getDocument().getAudioManager().getRate());
		// was 150.0
		assertEquals(300, view.getDocument().getAudioManager().getRate());
	}
	
}
