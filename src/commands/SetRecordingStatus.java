package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.AdvancedTextToSpeechView;

public class SetRecordingStatus implements ICommand, ActionListener {

		private AdvancedTextToSpeechView view;
		
		public SetRecordingStatus(AdvancedTextToSpeechView view) {
			this.view = view;
		}
		
		@Override
		public void execute() {
			
			if (view.getReplayManager().isActiveRecording())
				view.getReplayManager().endRecording();
			else
				view.getReplayManager().startRecording();
			
			view.debug("Recording: " + view.getReplayManager().isActiveRecording());
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// not going to store it to replayManager list, so just execute
			execute();
			
		}

		
		
}
