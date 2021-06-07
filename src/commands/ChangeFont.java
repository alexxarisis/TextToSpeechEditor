package commands;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.AdvancedTextToSpeechView;

public class ChangeFont implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private Font font;
	
	public ChangeFont(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public ChangeFont(ChangeFont other) {
		this.view = other.view;
		this.font = other.font;
	}
	
	@Override
	public void execute() {
		
		view.setFont(font);
		view.applyFont();
		
		view.debug("Changed font to: " + font.getFontName());
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new ChangeFont(this));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e == null)
			replay();
		else {
			String str = e.getActionCommand();
			if(str.equals("Helvetica"))
				font = new Font(str, Font.PLAIN, view.getFont().getSize());
			else if(str.equals("Times New Roman"))
				font = new Font(str, Font.PLAIN, view.getFont().getSize());
			else if(str.equals("Arial"))
				font = new Font(str, Font.PLAIN, view.getFont().getSize());
			else if(str.equals("Courier New"))
				font = new Font(str, Font.PLAIN, view.getFont().getSize());
			else if(str.equals("Bookman Old Style"))
				font = new Font(str, Font.PLAIN, view.getFont().getSize());
			
			execute();
		}
	}

	private void replay() {
		view.setFont(font);
		view.applyFont();
		
		view.debug("Changed font to: " + font.getFontName());
	}
}
