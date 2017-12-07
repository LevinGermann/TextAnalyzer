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
import java.util.Set;
import java.util.stream.Stream;

import ch.zhaw.ads.p10.cache.CacheManager;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.tools.ProgressBar;

public class TextAnalyzer {
	private static final String ROOT_PATH = "resources/reviewsbymembers50000";
	private static final int TOTAL_USERS = 1208;
	private CacheManager cacheManager;

	public static void main(String[] args){
		TextAnalyzer textAnalyzer = new TextAnalyzer();
		try {
			textAnalyzer.start();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	private void start() throws IOException {
		cacheManager = new CacheManager();
		
		File file = new File(ROOT_PATH);
		String[] directories = file.list(new FolderFilenameFilter());
		int reviewCounter = 0;
		ProgressBar bar = new ProgressBar();
		bar.update(0, TOTAL_USERS);
		System.out.println("Caching files...");
		
		for(String user : directories){
			Set<String> reviews = new HashSet<>();
			try (Stream<Path> paths = Files.walk(Paths.get(ROOT_PATH + "/" + user))) {
			    paths
			        .filter(Files::isRegularFile)
			        .forEach(filepath -> {
			        	 try {
							String content = new String(Files.readAllBytes(filepath));
							reviews.add(content);
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
