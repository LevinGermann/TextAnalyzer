package ch.zhaw.ads.p10.gui.comparators;

import java.util.Comparator;

public class WordComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if(o1 != null && o2 != null){
            return o1.compareTo(o2);
        }
		return 0;
	}

}
