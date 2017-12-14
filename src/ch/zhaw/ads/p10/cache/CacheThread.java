package ch.zhaw.ads.p10.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.swing.JProgressBar;

import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.filters.FolderFilenameFilter;
import ch.zhaw.ads.p10.gui.MainPanel;

public class CacheThread extends Thread {

	private final File chosenFolder;
	private final CacheManager cacheManager;
	private final MainPanel mainPanel;

	public CacheThread(File chosenFolder, MainPanel mainPanel, CacheManager cacheManager) {
		this.chosenFolder = chosenFolder;
		this.cacheManager = cacheManager;
		this.mainPanel = mainPanel;
	}

	public void run() {
		try {
			String[] directories = chosenFolder.list(new FolderFilenameFilter());
			mainPanel.addLog("Caching " + directories.length + " files...");
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
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					});
				}
				cachedDirs++;
                int percent = (cachedDirs * 100) / directories.length;
                mainPanel.getPbProgressBar().setValue(percent);
                mainPanel.addLog("Cached: " + cachedDirs + "/" + directories.length + " (" + percent + "%)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		mainPanel.addLog("Done caching!");
		mainPanel.loadWords();
	}

//	private static int getFilesCount(File file) {
//		File[] files = file.listFiles();
//		int count = 0;
//		for (File f : files)
//			if (f.isDirectory())
//				count += getFilesCount(f);
//			else
//				count++;
//
//		return count;
//	}
}
