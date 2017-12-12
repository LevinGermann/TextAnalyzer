package ch.zhaw.ads.p10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JProgressBar;

import ch.zhaw.ads.p10.cache.CacheManager;
import ch.zhaw.ads.p10.cache.CacheThread;
import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.cache.entities.CachedReviewUser;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.gui.MainPanel;

public class TextAnalyzer {
	private static final int TOTAL_USERS = 1208;
	private CacheManager cacheManager = new CacheManager();
	private Map<String, Integer> wordFrequency = new HashMap<>();

	public static void main(String[] args){
		TextAnalyzer textAnalyzer = new TextAnalyzer();
			//textAnalyzer.startAnalysis();
	}

	private void startAnalysis() {
		System.out.println();
		Set<CachedReviewUser> cachedUsers = cacheManager.getAllCachedUsers();
		Set<CachedReview> cachedReviews = new HashSet<>();
		int countOfAllWords = 0;
		int countOfAllChars = 0;
		Set<String> uniqueWords = new HashSet<>();
		
		for(CachedReviewUser user : cachedUsers) {
			Set<CachedReview> reviews = user.getReviews();
			for(CachedReview review : reviews) {
				List<String> words = review.getWords();
				appendInWordFrequency(words);
				countOfAllWords += words.size();
				uniqueWords.addAll(words);
				cachedReviews.add(review);
				
				countOfAllChars += review.getAmountOfChars();
			}
		}
		
		System.out.println("Reviews: " + cachedReviews.size());
		System.out.println("Unique words: " + uniqueWords.size());
		System.out.println("Words: " + countOfAllWords);
		System.out.println("Avg Review Length : " + countOfAllChars / cachedReviews.size());
		System.out.println("=== Word List ===");
		
		LinkedHashMap<String, Integer> sorted = wordFrequency.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
        .collect(Collectors.toMap(
          Map.Entry::getKey, 
          Map.Entry::getValue, 
          (e1, e2) -> e1, 
          LinkedHashMap::new
        ));
		
		sorted.forEach((key, value) -> {
			System.out.println(key + ": " + value);
		});
	}

	private void appendInWordFrequency(List<String> words) {
		for(String word : words) {
			Integer count = wordFrequency.get(word);
			if(count == null) {
				count = 0;
			}
			count++;
			wordFrequency.put(word, count);
		}
	}

	public void beginCache(File folder, MainPanel mainPanel) throws IOException {
		CacheThread cacheThread = new CacheThread(folder, mainPanel, cacheManager);
		cacheThread.start();
	}
}
