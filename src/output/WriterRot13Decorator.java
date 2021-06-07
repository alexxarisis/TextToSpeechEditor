package output;

public class WriterRot13Decorator extends WriterDecorator{

	public WriterRot13Decorator(IDocumentWriter writer) {
		super(writer);
	}

	@Override
	public void write(String path, String contents) {
		super.write(path, encodeRot13(contents));
	}
	
	private String encodeRot13(String contents) {
		StringBuilder encoded = new StringBuilder();
		
		int len = contents.length();
		for (int i = 0; i < len; i++) {
            char c = contents.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            encoded.append(c);
        }
		return encoded.toString();
	}
	
}
