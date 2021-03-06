package ch.zhaw.ads.p10.cache;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

import ch.zhaw.ads.p10.cache.entities.CachedReview;
import ch.zhaw.ads.p10.cache.entities.CachedReviewUser;

@Component
public class CacheManager {
	private Set<CachedReviewUser> cachedUsers;
	private int counter = 0;
	
	public CacheManager() {
		cachedUsers = new HashSet<>();
	}
	
	public void addReviews(String username, Set<CachedReview> reviews) {
		CachedReviewUser cachedUser = new CachedReviewUser(username, reviews);
		cachedUsers.add(cachedUser);
		counter += reviews.size();
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
	
	public int getCounter() {
		return counter;
	}
}
