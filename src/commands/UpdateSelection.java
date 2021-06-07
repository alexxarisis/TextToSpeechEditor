package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

import client.AdvancedTextToSpeechView;

public class UpdateSelection implements ICommand, ActionListener, CaretListener {

	private AdvancedTextToSpeechView view;
	private JTextArea textArea;
	private AbstractButton useSelectedTextButtonToolbar;
	private String selectedText;
	private boolean useSelectedText;
	
	public UpdateSelection(AdvancedTextToSpeechView view) {
		this.view = view;
		this.textArea = view.getTextArea();
		this.useSelectedTextButtonToolbar = view.getToolbarButton("useSelectedText");
	}
	
	public UpdateSelection(UpdateSelection other) {
		this.view = other.view;
		this.textArea = other.textArea;
		this.useSelectedTextButtonToolbar = other.useSelectedTextButtonToolbar;
		this.selectedText = other.selectedText;
		this.useSelectedText = other.useSelectedText;
	}
	
	@Override
	public void execute() {

		selectedText = textArea.getSelectedText();
		useSelectedText = useSelectedTextButtonToolbar.isSelected();
				
		// IF there is selected text, enable button
		if (selectedText == null || selectedText.isBlank()) {
			useSelectedTextButtonToolbar.setSelected(false);
			useSelectedTextButtonToolbar.setEnabled(false);
		} else
			useSelectedTextButtonToolbar.setEnabled(true);
		
		// set selected text accordingly
		if (useSelectedText)
			view.getDocument().setSelection(selectedText);
		else
			view.getDocument().setSelection(null);
		
		// if recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new UpdateSelection(this));

		// to show up on the screen
		if (selectedText == null || selectedText.isBlank())
			view.debug("Selected text: None" );
		else {
			
			String words[] = selectedText.strip().split(" ");
			// Split by \\t for the excel files
			String firstWord = words[0].split("\\t")[0].strip();
			String lastWord = words[words.length - 1].split("\\t")[0].strip();
			
			view.debug("Selected text: " + firstWord + "...." + lastWord + "  | Lines: " + 
						getLine(view.getTextArea().getSelectionStart()) + "-" + 
						getLine(view.getTextArea().getSelectionEnd()) +
						"\t Using selected text: " + useSelectedText);
		}
	}
	
	/**
	 * Update the selected text each time we click/select 
	 * something on the text area
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == textArea)
				execute();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			replay();
		else if (e.getSource() == useSelectedTextButtonToolbar)
			execute();
	}

	private void replay() {
		// IF there is selected text, enable button
		if (selectedText == null || selectedText.isBlank()) {
			useSelectedTextButtonToolbar.setSelected(false);
			useSelectedTextButtonToolbar.setEnabled(false);
		} else
			useSelectedTextButtonToolbar.setEnabled(true);
		
		// set selected text accordingly
		if (useSelectedText)
			view.getDocument().setSelection(selectedText);
		else
			view.getDocument().setSelection(null);
	}
	
	/** 
	 * Given as a param the caret's potision on the text,
	 * it finds the line its into.
	 * Currently used to get lines of the selected text,
	 * both at the start and the end of the selection.
	 * 
	 * @param caretPosition Caret position 
	 * @return String the line found into
	 */
	private String getLine(int caretPosition) {
		int rowNum = (caretPosition == 0) ? 1 : 0;
		
		for (int offset = caretPosition; offset > 0;) {
			try {
				offset = Utilities.getRowStart(textArea, offset) - 1;
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			rowNum++;
		}
		return String.valueOf(rowNum);
	}
}
