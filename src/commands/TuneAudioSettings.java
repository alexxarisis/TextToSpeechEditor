package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.AdvancedTextToSpeechView;

public class TuneAudioSettings implements ICommand, ActionListener, ChangeListener {
	
	private AdvancedTextToSpeechView view;
	private float volume;
	private float pitch;
	private float rate;
	
	public TuneAudioSettings(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public TuneAudioSettings(TuneAudioSettings other) {
		this.view = other.view;
		this.volume = other.volume;
		this.pitch = other.pitch;
		this.rate = other.rate;
	}
	
	@Override
	public void execute() {

		volume = (float) view.getAudioSettings("volume").getValue();
		pitch = (float) view.getAudioSettings("pitch").getValue();
		rate = (float) view.getAudioSettings("rate").getValue(); 
		
		view.getDocument().getAudioManager().setVolume(volume);
		view.getDocument().getAudioManager().setPitch(pitch);
		view.getDocument().getAudioManager().setRate(rate);
		
		view.debug("Volume: " + volume + "\tPitch: " + pitch + "\tRate: " + rate);
		
		// if we are recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new TuneAudioSettings(this));
	}
	

	@Override
	public void stateChanged(ChangeEvent e) {
		execute();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			replay();
	}
	
	private void replay() {
		view.getDocument().getAudioManager().setVolume(volume);
		view.getDocument().getAudioManager().setPitch(pitch);
		view.getDocument().getAudioManager().setRate(rate);
	}
}