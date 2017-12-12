package ch.zhaw.ads.p10.cache.entities;

import java.util.Set;

public class CachedReviewUser {
	private String name;
	private Set<CachedReview> reviews;

	public CachedReviewUser(String name, Set<CachedReview> reviews) {
		this.name = name;
		this.reviews = reviews;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<CachedReview> getReviews() {
		return reviews;
	}
}
