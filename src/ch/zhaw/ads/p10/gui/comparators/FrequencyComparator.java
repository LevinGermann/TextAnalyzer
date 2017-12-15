package ch.zhaw.ads.p10.gui.comparators;

import java.util.Comparator;

public class FrequencyComparator implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		if(o1 != null && o2 != null){
            return o1.compareTo(o2);
        }
		return 0;
	}

}
