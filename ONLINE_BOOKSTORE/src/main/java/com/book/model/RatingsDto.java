package com.book.model;

import lombok.Data;

@Data
public class RatingsDto {

	private Long cusmerId;
	private Long bookId;
	private int rate;
	private String reviewText;

}
