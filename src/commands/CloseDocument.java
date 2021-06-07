package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.AdvancedTextToSpeechView;

public class CloseDocument implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	
	public CloseDocument(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public CloseDocument(CloseDocument other) {
		this.view = other.view;
	}
	
	@Override
	public void execute() {
		// disable buttons if all files closed
		view.disableButtons();
		view.getTextArea().setText("");
		view.getTextArea().setEditable(false);
		
		view.debug("Closing file: " + view.getDocument().getName());
		view.getDocument().close();
		
		// trigger to updates line numbering on the left - Crude, i know
		view.getLines().setVisible(false);
		view.getLines().setVisible(true);
		
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new CloseDocument(this));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e == null)
			replay();
		else
			execute();
			
	}
	
	private void replay() {
		// disable buttons if all files closed
		view.disableButtons();
		view.getTextArea().setText("");
		view.getTextArea().setEditable(false);
		
		view.debug("Closing file: " + view.getDocument().getName());
		view.getDocument().close();
		
		// trigger to updates line numbering on the left - Crude, i know
		view.getLines().setVisible(false);
		view.getLines().setVisible(true);
	}
}

