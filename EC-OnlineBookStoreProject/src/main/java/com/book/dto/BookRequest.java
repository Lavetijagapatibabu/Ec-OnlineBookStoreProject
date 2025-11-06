package com.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {
	private String title;
	private String genre;
	private String authorName;
}
