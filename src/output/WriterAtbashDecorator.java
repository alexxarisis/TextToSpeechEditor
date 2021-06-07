package output;

public class WriterAtbashDecorator extends WriterDecorator{

	public WriterAtbashDecorator(IDocumentWriter writer) {
		super(writer);
	}

	@Override
	public void write(String path, String contents) {
		super.write(path, encodeAtbash(contents));
	}
	
	private String encodeAtbash(String contents) {
		
		return contents.chars()
				.map(cp -> Character.isUpperCase(cp) ? 'Z' - (cp - 'A')
						: Character.isLowerCase(cp) ? 'z' - (cp - 'a') : cp)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
}
