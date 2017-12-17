package ch.zhaw.ads.p10.cache;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.gui.LogArea;
import ch.zhaw.ads.p10.gui.MainPanel;

@Component
public class CacheThread extends Thread {

	private File chosenFolder;
	private CacheManager cacheManager;
	private MainPanel mainPanel;
	private LogArea logArea;

	@Autowired
	public CacheThread(CacheManager cacheManager, MainPanel mainPanel, LogArea logArea) {
		this.cacheManager = cacheManager;
		this.mainPanel = mainPanel;
		this.logArea = logArea;
	}

	public void run() {
		try {
			String[] directories = chosenFolder.list(new FolderFilenameFilter());
			logArea.addLog("Caching " + directories.length + " folder...");
			int cachedDirs = 0;

			for (String user : directories) {
				Set<CachedReview> reviews = new HashSet<>();
				try (Stream<Path> paths = Files.walk(Paths.get(chosenFolder + "/" + user))) {
					paths.filter(Files::isRegularFile).forEach(filepath -> {
						try {
							final File file;
							final FileChannel channel;
							final MappedByteBuffer buffer;

							file = filepath.toFile();
							FileInputStream fin = new FileInputStream(file);
							channel = fin.getChannel();
							buffer = channel.map(MapMode.READ_ONLY, 0, file.length());

							String content = "";
							while (buffer.hasRemaining()) {
								content += (char) buffer.get();
							}

							fin.close();
							CachedReview review = new CachedReview(content, file.getName());
							reviews.add(review);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					});
				}

				cacheManager.addReviews(user, reviews);
				cachedDirs++;
				int percent = (cachedDirs * 100) / directories.length;
				mainPanel.getPbProgressBar().setValue(percent);
				logArea.addLog("Cached: " + cachedDirs + "/" + directories.length + " (" + percent + "%)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logArea.addLog("Done caching!");
		mainPanel.loadWords();
		mainPanel.enableDisableComponentStates(true);
	}

	public void setChosenFolder(File folder) {
		this.chosenFolder = folder;
	}
}
