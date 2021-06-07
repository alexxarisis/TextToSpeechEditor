package commands;

import java.awt.event.ActionListener;

import javax.swing.event.CaretListener;
import javax.swing.event.ChangeListener;

import client.AdvancedTextToSpeechView;

public class CommandsFactory {

	private AdvancedTextToSpeechView view;
	
	public CommandsFactory (AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public ActionListener createCommand(String str) {
		
		switch (str) {
		case "open":
			return new OpenDocument(view);
		case "save":
			return new SaveDocument(view);
		case "close":
			return new CloseDocument(view);
		case "edit":
			return new EditDocument(view);
		case "play":
			return new DocumentToSpeech(view);
		case "updateSelection":
			return new UpdateSelection(view);
		case "replay":
			return new ReplayCommands(view);
		case "record":
			return new SetRecordingStatus(view);
		case "font":
			return new ChangeFont(view);
		case "fontSize":
			return new ChangeFontSize(view);
		case "colorTheme":
			return new ChangeTextColorTheme(view);
		default:
			return null;
		}
	}
	
	public CaretListener createUpdateSelectionCommand() {
		return new UpdateSelection(view);
	}
	
	public ChangeListener createChangeListenerCommand(String str) {
		switch (str) {
		case "voice":
			return new TuneAudioSettings(view);
		default:
			return null;
		}
	}
}
