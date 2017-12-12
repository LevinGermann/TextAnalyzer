package ch.zhaw.ads.p10.cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.swing.JProgressBar;

import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.gui.MainPanel;

public class CacheThread extends Thread {

	private final File folder;
	private final CacheManager cacheManager;
	private final MainPanel mainPanel;

	public CacheThread(File folder, MainPanel mainPanel, CacheManager cacheManager) {
		this.folder = folder;
		this.cacheManager = cacheManager;
		this.mainPanel = mainPanel;
	}

	public void run() {
		try {
			String[] directories = folder.list(new FolderFilenameFilter());
			int reviewCounter = 0;
			int total = getFilesCount(folder);
			System.out.println("Caching " + total);

			for (String user : directories) {
				Set<CachedReview> reviews = new HashSet<>();
				try (Stream<Path> paths = Files.walk(Paths.get(folder + "/" + user))) {
					paths.filter(Files::isRegularFile).forEach(filepath -> {
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
				cacheManager.addReviews(user, reviews);
				
				int percent = (reviewCounter * 100) / total;
				mainPanel.getPbProgressBar().setValue(percent);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("done");
		mainPanel.enableDisableComponentStates(true);
	}

	public static int getFilesCount(File file) {
		File[] files = file.listFiles();
		int count = 0;
		for (File f : files)
			if (f.isDirectory())
				count += getFilesCount(f);
			else
				count++;

		return count;
	}
}
