package ch.zhaw.ads.p10;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.zhaw.ads.p10.cache.CacheManager;
import ch.zhaw.ads.p10.cache.CachedReview;
import ch.zhaw.ads.p10.cache.CachedReviewUser;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.tools.ProgressBar;

public class TextAnalyzer {
	private static final String ROOT_PATH = "resources/reviewsbymembers50000";
	private static final int TOTAL_USERS = 1208;
	private CacheManager cacheManager;

	public static void main(String[] args){
		TextAnalyzer textAnalyzer = new TextAnalyzer();
		try {
			textAnalyzer.beginCache();
			textAnalyzer.startAnalysis();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
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
				String[] words = review.getWords();
				countOfAllWords += words.length;
				uniqueWords.addAll(Arrays.asList(words));
				cachedReviews.add(review);
				
				countOfAllChars += review.getAmountOfChars();
			}
		}
		
		System.out.println("Reviews: " + cachedReviews.size());
		System.out.println("Unique words: " + uniqueWords.size());
		System.out.println("Words: " + countOfAllWords);
		System.out.println("Avg Review Length : " + countOfAllChars / cachedReviews.size());
	}

	private void beginCache() throws IOException {
		cacheManager = new CacheManager();
		
		File file = new File(ROOT_PATH);
		String[] directories = file.list(new FolderFilenameFilter());
		int reviewCounter = 0;
		ProgressBar bar = new ProgressBar();
		bar.update(0, TOTAL_USERS);
		System.out.println("Caching files...");
		
		for(String user : directories){
			Set<CachedReview> reviews = new HashSet<>();
			try (Stream<Path> paths = Files.walk(Paths.get(ROOT_PATH + "/" + user))) {
			    paths
			        .filter(Files::isRegularFile)
			        .forEach(filepath -> {
			        	 try {
							String content = new String(Files.readAllBytes(filepath));
							CachedReview review = new CachedReview(content, filepath.getFileName().toString());
							reviews.add(review);
			        	 } catch (IOException e) {
							e.printStackTrace();
						}
			        });
			}
			reviewCounter++;
			bar.update(reviewCounter, TOTAL_USERS);
			cacheManager.addReviews(user, reviews);
		}
	}
}
