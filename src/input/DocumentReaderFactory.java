package input;

public class DocumentReaderFactory {
	
	public DocumentReaderFactory() {
		
	}
	
	public IDocumentReader createReader(String fileType, String encryptionType) {
		
		IDocumentReader reader;
		
		// determine reader based on type of the file
		switch (fileType) {
		case "txt":
			reader = new TxtReader();
			break;
		case "docx":
			reader = new WordReader();
			break;
		case "xlsx":
			reader = new ExcelReader();
			break;
		default:
			reader = new TxtReader();
			break;
		}
		
		// determine reader based on encryption of the file
		switch (encryptionType) {
		case "None":
			return reader;
		case "Rot-13":
			return new ReaderRot13Decorator(reader);
		case "AtBash":
			return new ReaderAtbashDecorator(reader);
		default:
			return reader;
		}
	}
}
