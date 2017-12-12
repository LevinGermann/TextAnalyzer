package ch.zhaw.ads.p10.cache;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.cache.entities.CachedReviewUser;

public class CacheManager {
	private Set<CachedReviewUser> cachedUsers;
	
	public CacheManager() {
		cachedUsers = new HashSet<>();
	}
	
	public void addReviews(String username, Set<CachedReview> reviews) {
		CachedReviewUser cachedUser = new CachedReviewUser(username, reviews);
		cachedUsers.add(cachedUser);
	}
	
	public Set<CachedReviewUser> getAllCachedUsers() {
		return cachedUsers;
	}
	
	public Set<CachedReview> getAllCachedDocuments() {
		Set<CachedReview> result = new HashSet<>();
		Iterator<CachedReviewUser> iterator = cachedUsers.iterator();
		while(iterator.hasNext()) {
			CachedReviewUser cachedUser = iterator.next();
			result.addAll(cachedUser.getReviews());
		}
		return result;
	}
}
