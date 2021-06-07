package commands;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.compress.utils.FileNameUtils;

import client.AdvancedTextToSpeechView;

public class OpenDocument implements ICommand, ActionListener {

	private AdvancedTextToSpeechView view;
	private String name;
	private String fileType;
	private String path;
	private String encryption;
	private String contents;
	private boolean linewrap;
	
	public OpenDocument(AdvancedTextToSpeechView view) {
		this.view = view;
		this.linewrap = true;
	}
	
	// to create a clone & pass it to the replay manager
	public OpenDocument(OpenDocument other) {
		this.view = other.view;
		this.name = other.name;
		this.fileType = other.fileType;
		this.path = other.path;
		this.encryption = other.encryption;
		this.contents = other.contents;
		this.linewrap = other.linewrap;
	}
	
	@Override
	public void execute() {
		
		try
		{
			// Choose file
			JFileChooser fileChooser = new JFileChooser("Resources\\Samples");
			fileChooser.setPreferredSize(new Dimension(800, 500));
			fileChooser.setDialogTitle("Open file:");
			
			// check if file chooser didnt close
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				view.debug("No file selected.");
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
				view.debug("No file encryption type selected.");
				return;
			}
			
			// Get info of file
			File file = fileChooser.getSelectedFile();
			name = file.getName().split("\\.")[0];
			path = file.getAbsolutePath();	
			fileType = FileNameUtils.getExtension(path);
			
			if (fileType.equals("xlsx"))
				linewrap = false;
	
			view.getDocument().open(name, path, fileType, encryption);
			view.setTextAndLineWrap(view.getDocument().getContents(), linewrap);
			view.enableButtons();
			// trigger to updates line numbering on the left - Crude, i know
			view.getLines().setVisible(false);
			view.getLines().setVisible(true);

			view.debug("Opening file: " + name + "." + fileType);
			
			// if file opened successfully we have reached here
			// if we are recording, add it to the list
			if (view.getReplayManager().isActiveRecording())
				view.getReplayManager().addCommand(new OpenDocument(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e == null)
			replay();
		else
			execute();
	}
	
	
	/**
	 * Works the same as execute(), but without the need to
	 * set the fields.
	 */
	public void replay() {
		view.getDocument().open(name, path, fileType, encryption);
		view.setTextAndLineWrap(view.getDocument().getContents(), linewrap);
		view.enableButtons();

		// trigger to updates line numbering on the left - Crude, i know
		view.getLines().setVisible(false);
		view.getLines().setVisible(true);

		view.debug("Opening file: " + name);
	}
	
	
	// setters exclusively for testing
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setLinewrap(boolean linewrap) {
		this.linewrap = linewrap;
	}
	
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
