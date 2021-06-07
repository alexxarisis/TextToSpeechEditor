package model;

import input.DocumentReaderFactory;
import input.IDocumentReader;
import output.DocumentWriterFactory;
import output.IDocumentWriter;

public class Document {

	private TTSFacade audioManager;
	private String contents;
	private String selection;
	private String name;
	
	public Document() {
		this.audioManager = new TTSFacade();
		this.contents = "";
		this.selection = null;
		this.name = "";
	}
	
	public TTSFacade getAudioManager() {
		return audioManager;
	}
	public String getName() {
		return this.name;
	}
	
	public String getContents() {
		return this.contents; 
	}
	
	public String getSelection() {
		return this.selection;
	}
	
	public void setAudioManager(TTSFacade audioManager) {
		this.audioManager = audioManager;
	}
	
	public void setContents(String contents) { 
		this.contents = contents; 
	}
	
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	public String open(String name, String path, String fileType, String encryption) {
		String contents = "";
		DocumentReaderFactory factory = new DocumentReaderFactory();
		IDocumentReader reader = factory.createReader(fileType, encryption);
		
		
		if (reader != null)
			contents = reader.read(path);
		
		this.contents = contents;
		this.name = name;
		return this.contents;
	}
	
	public String save(String name, String path, String extension, String encryption)
	{
		path = path + name + extension;
		DocumentWriterFactory factory = new DocumentWriterFactory();
		IDocumentWriter writer = factory.createWriter(extension, encryption);
		
		if (writer != null)
			writer.write(path, contents);
		
		//System.out.println(path);
		return path;
	}
	
	public void playContents() {
		audioManager.play(contents);
	}
	
	public void playSelection() {
		audioManager.play(selection);
	}
	
	public void close() {
		this.contents = "";
		this.selection = null;
		this.name = "";
	}
}
