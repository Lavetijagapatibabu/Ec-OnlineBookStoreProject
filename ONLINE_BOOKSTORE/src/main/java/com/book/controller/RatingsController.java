package com.book.controller;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.RatingsDto;
import com.book.model.ResponseMessage;
import com.book.service.IRatingService;
import com.book.utility.Constraints;

@RestController
public class RatingsController {

	@Autowired private IRatingService ratingService;

	@PostMapping("/setratings")
	public ResponseEntity<ResponseMessage> giveRatings(@RequestBody RatingsDto ratingsDto){
		try {
			RatingsDto ratingRivews = ratingService.giveRatingReviews(ratingsDto);

			if(ratingRivews!=null) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constraints.SUCCESS, "rating save successfully", ratingRivews));
			} else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "rating  save Failed", ratingRivews));

			}

		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "Internal server error"));
		}
	}

	@GetMapping("/getratings")
	public ResponseEntity<ResponseMessage> getAllRatings(){

		List<RatingsDto> allRatings = ratingService.getAllRatings();

		List<Integer> ratingsList = allRatings.stream().map(list->list.getRate()).collect(Collectors.toList());

		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constraints.SUCCESS,"Successfully get the all Ratings ",allRatings,ratingsList));
	}
}
