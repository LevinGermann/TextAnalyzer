package ch.zhaw.ads.p10.cache.entities;

import java.util.ArrayList;
import java.util.List;

public class CachedReview {
	
	private String filename;
	private String originalText;
	private List<String> cleanedWords;

	public CachedReview(String text, String filename) {
		this.originalText = text;
		String result = text.replaceAll("([^\\w\\s])", "").trim().toLowerCase();
		String[] tempWords = result.split(" ");
		List<String> finalWords = new ArrayList<>();
		for(String word : tempWords) {
			if(!word.equals("")) {
				finalWords.add(word);
			}
		}
		this.cleanedWords = finalWords;
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}

	public String getOriginalText() {
		return originalText;
	}
	
	public List<String> getWords() {
		return cleanedWords;
	}
	
	public int getAmountOfChars() {
		int result = 0;
		for(String word : cleanedWords) {
			result += word.length();
		}
		return result;
	}
}
