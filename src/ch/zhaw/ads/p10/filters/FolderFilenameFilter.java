package ch.zhaw.ads.p10.filters;

import java.io.File;
import java.io.FilenameFilter;

public class FolderFilenameFilter implements FilenameFilter{

	@Override
	  public boolean accept(File current, String name) {
	    return new File(current, name).isDirectory();
	  }

}
