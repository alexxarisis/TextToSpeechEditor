package commands;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.AdvancedTextToSpeechView;

public class ChangeFontSize implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private Font font;
	
	public ChangeFontSize(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public ChangeFontSize(ChangeFontSize other) {
		this.view = other.view;
		this.font = other.font;
	}
	
	@Override
	public void execute() {
		view.setFont(font);
		view.applyFont();
		// crude but working as intended
		view.setFontSizeLabel("            " + Integer.toString(font.getSize()) + " ");
		
		view.debug("Font size changed to: " + font.getSize());
		
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new ChangeFontSize(this));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e == null)
			replay();
		else {
			if (e.getSource() == view.getIncreaseFontSizeButton())
				font = new Font(view.getFont().getFamily(), Font.PLAIN, view.getFont().getSize() + 1);
			else
				font = new Font(view.getFont().getFamily(), Font.PLAIN, view.getFont().getSize() - 1);

			execute();
		}
	}
	
	private void replay() {
		view.setFont(font);
		view.applyFont();
		// crude but working as intended
		view.setFontSizeLabel("            " + Integer.toString(font.getSize()) + " ");
		
		view.debug("Font size changed to: " + font.getSize());
		
	}
}
