package model;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class FreeTTS{
	
	private VoiceManager vm;
	private Voice voice;
	
	public FreeTTS() {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
		voice.allocate();
	}
	
	public void play(String contents) {
		voice.speak(contents);
	}
	
	public void setVolume(float volume) {
		voice.setVolume(volume);
	}
	
	public void setPitch(float pitch) {
		voice.setPitch(pitch);
	}
	
	public void setRate(float rate) {
		voice.setRate(rate);
	}
	
	// These getters are not yet used,
	// but just in case for the furute
	public float getVolume() {
		return voice.getVolume();
	}
	
	public float getPitch() {
		return voice.getPitch();
	}
	
	public float getRate() {
		return voice.getRate();
	}
}
