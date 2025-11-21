package com.book.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

	private Integer statusCode;

	@NonNull
	private String status;

	@NonNull
	private String message;

	@NonNull
	private Object data;

	private List<?> list;

	public ResponseMessage(Integer statusCode, String status, String message) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.data = null; 
	}
    
	public ResponseMessage(Integer statusCode, String status, String message, Object data) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.data = data;
	}

}