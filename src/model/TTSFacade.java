package model;

public class TTSFacade {
	
	private FreeTTS voice;
	
	public TTSFacade() {
		voice = new FreeTTS();
	}
	
	public void play(String contents) {
		voice.play(contents);
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
