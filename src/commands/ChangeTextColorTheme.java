package commands;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.AdvancedTextToSpeechView;

public class ChangeTextColorTheme implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private boolean isBlackTheme;
	
	public ChangeTextColorTheme(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public ChangeTextColorTheme(ChangeTextColorTheme other) {
		this.view = other.view;
		this.isBlackTheme = other.isBlackTheme;
	}
	
	@Override
	public void execute() {
		isBlackTheme = view.getBlackThemeButton().getState();
		
		if (isBlackTheme)
			view.setColors(new Color(245, 245, 250), new Color(39, 39, 39), Color.DARK_GRAY);
		else 
			view.setColors(Color.BLACK, Color.WHITE, Color.WHITE);

		view.changeTextColorTheme();
		
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new ChangeTextColorTheme(this));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			replay();
		else 
			execute();
		
	}
	
	private void replay() {
		if (isBlackTheme)
			view.setColors(new Color(245, 245, 250), new Color(39, 39, 39), Color.DARK_GRAY);
		else 
			view.setColors(Color.BLACK, Color.WHITE, Color.WHITE);

		view.changeTextColorTheme();
	}

	
}