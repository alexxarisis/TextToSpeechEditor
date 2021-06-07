package output;

public class DocumentWriterFactory {
	
	public DocumentWriterFactory() {
		
	}
	
	public IDocumentWriter createWriter(String extension, String encryption) {
		
		IDocumentWriter writer;
		
		// determine reader based on type of the file
		switch (extension) {
		case ".txt":
			writer = new TxtWriter();
			break;
		case ".docx":
			writer = new WordWriter();
			break;
		case ".xlsx":
			writer = new ExcelWriter();
			break;
		default:
			writer = new TxtWriter();
		}
		
		switch (encryption) {
		case "None":
			return writer;
		case "Rot-13":
			return new WriterRot13Decorator(writer);
		case "AtBash":
			return new WriterAtbashDecorator(writer);
		default:
			return writer;
		}
	}
}
