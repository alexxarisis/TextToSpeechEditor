package input;

public class ReaderAtbashDecorator extends ReaderDecorator{

	public ReaderAtbashDecorator(IDocumentReader reader) {
		super(reader);
	}
	
	@Override
	public String read(String path) {
		
		return decodeAtbash(super.read(path));
	}
	
	private String decodeAtbash(String contents) {
		
		return contents.chars()
				.map(cp -> Character.isUpperCase(cp) ? 'Z' - (cp - 'A')
						: Character.isLowerCase(cp) ? 'z' - (cp - 'a') : cp)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

}
