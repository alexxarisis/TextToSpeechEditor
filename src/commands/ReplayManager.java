package commands;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReplayManager {

	private boolean recordingStatus;
	private List<ActionListener> commands;
	
	public ReplayManager() {
		commands = new ArrayList<>();
		recordingStatus = false;
	}
	
	public void replay() {
		for (ActionListener command : commands) {
			command.actionPerformed(null);
		}
	}
	
	public void addCommand(ActionListener command) {
		commands.add(command);
	}
	
	public void startRecording() {
		recordingStatus = true;
		commands.clear();
	}
	
	public void endRecording() {
		recordingStatus = false;
	}
	
	public boolean isActiveRecording() {
		return recordingStatus;
	}
}
