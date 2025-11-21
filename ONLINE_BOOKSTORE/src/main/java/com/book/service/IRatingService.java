package com.book.service;

import java.util.List;

import com.book.model.RatingsDto;

public interface IRatingService {

	RatingsDto giveRatingReviews(RatingsDto ratingsDto);
	
	List<RatingsDto> getAllRatings();
}