package ch.zhaw.ads.p10.cache;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CacheManager {
	private Set<CachedReviewUser> cachedUsers;
	
	public CacheManager() {
		cachedUsers = new HashSet<>();
	}
	
	public void addReviews(String username, Set<String> reviews) {
		CachedReviewUser cachedUser = new CachedReviewUser(username);
		Iterator<String> iterator = reviews.iterator();
		while(iterator.hasNext()) {
			String review = iterator.next();
			CachedReview cachedReview = new CachedReview(review);
			cachedUser.addReview(cachedReview);
		}
		
		cachedUsers.add(cachedUser);
	}
	
	public int getCachedUsersSize() {
		return cachedUsers.size();
	}
}
