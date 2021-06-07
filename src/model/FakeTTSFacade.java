package model;

public class FakeTTSFacade extends TTSFacade{
	
	private StringBuilder playedContents;
	private float volume;
	private float pitch;
	private float rate;
	
	public FakeTTSFacade() {
		playedContents = new StringBuilder();
		volume = 1.0f;
		pitch = 100.0f;
		rate = 150.0f;
	}
	
	@Override
	public void play(String contents) {
		playedContents.append(contents);
		super.play(contents);
	}
	
	@Override
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	@Override
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	@Override
	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public String getPlayedContents() {
		return playedContents.toString();
	}
	
	@Override
	public float getVolume() {
		return volume;
	}
	
	@Override
	public float getPitch() {
		return pitch;
	}
	
	@Override
	public float getRate() {
		return rate;
	}

}
