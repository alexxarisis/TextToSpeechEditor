package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.AdvancedTextToSpeechView;

public class ReplayCommands implements ICommand, ActionListener{

	private AdvancedTextToSpeechView view;
	
	public ReplayCommands(AdvancedTextToSpeechView view) {
		this.view = view;
	}

	@Override
	public void execute() {
		
		view.getReplayManager().replay();
		view.debug("Replaying");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// not going to store it to replayManager list, so just execute
		execute();
		
	}
}
