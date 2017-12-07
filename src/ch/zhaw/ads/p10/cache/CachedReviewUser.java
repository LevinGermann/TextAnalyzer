package ch.zhaw.ads.p10.cache;

import java.util.HashSet;
import java.util.Set;

public class CachedReviewUser {
	private String name;
	private Set<CachedReview> reviews;
	
	public CachedReviewUser(String name) {
		this.name = name;
		reviews = new HashSet<>();
	}
	
	public void addReview(CachedReview review) {
		this.reviews.add(review);
	}
	
	public String getName() {
		return name;
	}
	
	public Set<CachedReview> getReviews() {
		return reviews;
	}
}
