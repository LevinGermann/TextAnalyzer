package ch.zhaw.ads.p10;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.ads.p10.cache.CacheManager;
import ch.zhaw.ads.p10.cache.CacheThread;
import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.cache.entities.CachedReviewUser;
import ch.zhaw.ads.p10.gui.LogArea;
import ch.zhaw.ads.p10.gui.WordListTable;

@Component
public class TextAnalyzer {
	private CacheThread cacheThread;
	private CacheManager cacheManager;
	private LogArea logArea;
	private WordListTable wordListTable;
	private Map<String, Integer> wordFrequency = new HashMap<>();

	public void startAnalysis() {
		Set<CachedReviewUser> cachedUsers = cacheManager.getAllCachedUsers();
		Set<CachedReview> cachedReviews = new HashSet<>();
		int countOfAllWords = 0;
		int countOfAllChars = 0;
		Set<String> uniqueWords = new HashSet<>();

		for (CachedReviewUser user : cachedUsers) {
			Set<CachedReview> reviews = user.getReviews();
			for (CachedReview review : reviews) {
				List<String> words = review.getWords();
				appendInWordFrequency(words);
				countOfAllWords += words.size();
				uniqueWords.addAll(words);
				cachedReviews.add(review);

				countOfAllChars += review.getAmountOfChars();
			}
		}

		logArea.addLog("Reviews: " + cachedReviews.size());
		logArea.addLog("Unique words: " + uniqueWords.size());
		logArea.addLog("Words: " + countOfAllWords);
		logArea.addLog("Avg Review Length : " + countOfAllChars / cachedReviews.size());

		LinkedHashMap<String, Integer> sorted = wordFrequency.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		wordFrequency = sorted;
		
		sorted.forEach((key, value) -> {
			wordListTable.addWord(key, value);
		});
	}

	private void appendInWordFrequency(List<String> words) {
		for (String word : words) {
			Integer count = wordFrequency.get(word);
			if (count == null) {
				count = 0;
			}
			count++;
			wordFrequency.put(word, count);
		}
	}

	public Map<String, Integer> filterWords(String searchExpression){
		Map<String, Integer> filtered =
				wordFrequency.entrySet()
	            .stream()
	            .filter(p -> p.getKey().contains(searchExpression))
	            .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		return filtered;
	}
	
	public Map<String, Integer> getWordFrequency() {
		return wordFrequency;
	}
	
	public void beginCache(File folder) {
		cacheThread.setChosenFolder(folder);
		cacheThread.start();
	}

	@Autowired
	public void setCacheThread(CacheThread cacheThread) {
		this.cacheThread = cacheThread;
	}

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Autowired
	public void setLogArea(LogArea logArea) {
		this.logArea = logArea;
	}

	@Autowired
	public void setWordListTable(WordListTable wordListTable) {
		this.wordListTable = wordListTable;
	}
	
	
}
