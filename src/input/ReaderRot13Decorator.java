package input;

public class ReaderRot13Decorator extends ReaderDecorator{
	
	public ReaderRot13Decorator(IDocumentReader reader) {
		super(reader);
	}
	
	@Override
	public String read(String path) {
		
		return decodeRot13(super.read(path));
	}
	
	private String decodeRot13(String contents) {
		StringBuilder decoded = new StringBuilder();
		
		int len = contents.length();
		for (int i = 0; i < len; i++) {
            char c = contents.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            decoded.append(c);
        }
		return decoded.toString();
	}
}
