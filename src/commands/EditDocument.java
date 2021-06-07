package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JTextArea;

import client.AdvancedTextToSpeechView;

public class EditDocument implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private JTextArea textArea;
	private String text;
	private boolean isEditable;
	private boolean linewrap;
	
	public EditDocument(AdvancedTextToSpeechView view) {
		this.view = view;
		this.textArea = view.getTextArea();
	}
	
	public EditDocument(EditDocument other) {
		this.view = other.view;
		this.textArea = other.view.getTextArea();
		this.text = other.text;
		this.isEditable = other.isEditable;
		this.linewrap = other.linewrap;
	}
	
	@Override
	public void execute() {
		text = textArea.getText();
		
		if (isEditable) {
			view.enableEditButtons(true);
			textArea.setEditable(true);
		} else {
			view.enableEditButtons(false);
			textArea.setEditable(false);
			view.getDocument().setContents(text);
		}
		
		// for the replay manager
		linewrap = textArea.getLineWrap();
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new EditDocument(this));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			replay();
		else {
			String str = e.getActionCommand();
			Object source = e.getSource();
			AbstractButton editButtonOnToolbar = view.getToolbarButton("edit");
			
			if (str.equals("Edit Text") || (source == editButtonOnToolbar && editButtonOnToolbar.isSelected()))
				isEditable = true;
			else if (str.equals("Stop Editing") || source == editButtonOnToolbar)
				isEditable = false;
			
			execute();
		}	
	}
	
	private void replay() {
		if (isEditable) {
			view.enableEditButtons(true);
			textArea.setEditable(true);
		} else {
			view.enableEditButtons(false);
			textArea.setEditable(false);
			view.getDocument().setContents(text);
			// only for the replay manager,
			// to set the text on the gui as well 
			view.setTextAndLineWrap(text, linewrap);;
		}
	}
}
