package commands;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import client.AdvancedTextToSpeechView;

public class SaveDocument implements ICommand, ActionListener{

	private AdvancedTextToSpeechView view;
	private String path;
	private String name;
	private String extension;
	private String encryption;
	
	public SaveDocument(AdvancedTextToSpeechView view) {
		this.view = view;
	}
	
	public SaveDocument(SaveDocument other) {
		this.view = other.view;
		this.path = other.path;
		this.name = other.name;
		this.extension = other.extension;
		this.encryption = other.encryption;
	}

	@Override
	public void execute() {
		
		// Choose file
		JFileChooser fileChooser = new JFileChooser("Resources\\Samples");
		fileChooser.setPreferredSize(new Dimension(800, 500));
		fileChooser.setDialogTitle("Save as...");
		fileChooser.setSelectedFile(new File(view.getDocument().getName()));
		
		// check if file chooser didnt close
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			view.debug("No directory selected.");
			return;
		}
		
		// Choose encryption of the file
		String[] encryptions = {"None", "Rot-13", "AtBash"};
					
		encryption = (String) JOptionPane.showInputDialog(
				null,
				"Choose the encryption type of the file: ",
				"Encryption",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				encryptions,
				encryptions[0]);
					
		// check if encryption chooser window, didnt just close
		if (encryption == null) {
			view.debug("No file encryption was selected. Not even None.");
			return;
		}
		
		// Choose extension of the file
		String[] extensions = {".txt", ".docx", ".xlsx"};
							
		extension = (String) JOptionPane.showInputDialog(
				null,
				"Choose the format to save the file as: ",
				"Extension",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				extensions,
				extensions[0]);
					
		// check if extension chooser window, didnt just close
		if (extension == null) {
			view.debug("No file extension was selected.");
			return;
		}
		
		name = fileChooser.getSelectedFile().getName();
		path = fileChooser.getSelectedFile().getAbsolutePath();
		// to get rid of extensions if user wrote them in file chooser
		int dotOffset = name.lastIndexOf(".");
		if (dotOffset != -1)
			name = name.substring(0, dotOffset);
		// to only get the folder path
		path = path.substring(0, path.lastIndexOf("\\") + 1);
		
		// save the file according to the fields' values.
		String outputPath = view.getDocument().save(name, path, extension, encryption);
		view.debug(outputPath);
		
		// if file saved successfully, we reached here.
		// if we are recording, add it to the list
		if (view.getReplayManager().isActiveRecording())
			view.getReplayManager().addCommand(new SaveDocument(this));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null)
			replay();
		else {
			execute();
		}
	}
	
	/**
	 * Works the same as execute(), but without the need to
	 * set the fields.
	 */
	public void replay() {
		// save the file according to the fields' values.
		String outputPath = view.getDocument().save(name, path, extension, encryption);
		view.debug(outputPath);
	}
	
	// setters exclusively for testing
		public void setName(String name) {
			this.name = name;
		}
		
		public void setPath(String path) {
			this.path = path;
		}
		
		public void setFileType(String extension) {
			this.extension = extension;
		}
		
		public void setEncryption(String encryption) {
			this.encryption = encryption;
		}
		
}
