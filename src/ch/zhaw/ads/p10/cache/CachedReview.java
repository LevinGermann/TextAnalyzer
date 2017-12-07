package ch.zhaw.ads.p10.cache;

public class CachedReview {
	
	private String filename;
	private String text;

	public CachedReview(String text, String filename) {
		this.text = text;
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}

	public String getText() {
		return text;
	}
	
	public String[] getWords() {
		String  result = text.replaceAll("[^\\w\\s]","");
		return result.split(" ");
	}
	
	public int getAmountOfChars() {
		return text.length();
	}
}
