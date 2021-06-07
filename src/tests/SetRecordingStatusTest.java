package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import client.AdvancedTextToSpeechView;

public class SetRecordingStatusTest {

	private AdvancedTextToSpeechView view;
	
	@BeforeEach
	public void setUp() throws Exception {
		view = new AdvancedTextToSpeechView();// to start it
		view.getToolbarButton("record").doClick();
	}
	
	@Test
	@DisplayName("Recording: start")
	public void testRecordingStart() {
		// already started
		assertEquals(true, view.getReplayManager().isActiveRecording());
	}
	
	@Test
	@DisplayName("Recording: stop")
	public void testRecordingEnd() {
		// to end it
		view.getToolbarButton("record").doClick();
		assertEquals(false, view.getReplayManager().isActiveRecording());
		
	}
}
