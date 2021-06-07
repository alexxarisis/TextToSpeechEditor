package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.AdvancedTextToSpeechView;

public class DocumentToSpeech implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private boolean useSelection;
	
	public DocumentToSpeech(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public DocumentToSpeech(DocumentToSpeech other) {
		this.view = other.view;
		this.useSelection = other.useSelection;
	}
	
	@Override
	public void execute() {

		if (useSelection)
			view.getDocument().playSelection();
		else
			view.getDocument().playContents();
		
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new DocumentToSpeech(this));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e == null) {
			replay();
		} else
			if (view.getToolbarButton("edit").isSelected())
				JOptionPane.showMessageDialog(null, "Document is still being edited.", 
						"Error while trying to play audio.", JOptionPane.ERROR_MESSAGE);
			else {
				useSelection = view.getToolbarButton("useSelectedText").isSelected();
				execute();
			}
	}
	
	private void replay() {
		if (useSelection)
			view.getDocument().playSelection();
		else
			view.getDocument().playContents();
		
		
	}
	
}
