package com.book.exception;

import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.book.utility.Constraints;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserIdNotFoundException.class)
	public ResponseEntity<ErrorMessage> getUserNotFoundException(UserIdNotFoundException userException){
		List<String> list = new ArrayList<>();
		list.add("Error Type : UserIdNotFoundException");
		list.add("Error Message : "+userException.getLocalizedMessage());
		list.add("Timestamp : "+Instant.now().toString());
		ErrorMessage errorMessage = new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.ERROR,"The requested user ID does not exist in the database.", list);
		return ResponseEntity.ok(errorMessage);
	}

	@ExceptionHandler(BookIdNotFoundException.class)
	public ResponseEntity<ErrorMessage> getBookIdNotFoundException(BookIdNotFoundException bookIdException){
		List<String> list = new ArrayList<>();
		list.add("Error Type : BookIdNotFoundException");
		list.add("Error Message : "+bookIdException.getLocalizedMessage());
		list.add("Timestamp : "+Instant.now().toString());
		ErrorMessage errorMessage = new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.ERROR,"The requested user ID does not exist in the database.", list);
		return ResponseEntity.ok(errorMessage);
	}

	@ExceptionHandler(CustomerIdNotFoundException.class)
	public ResponseEntity<ErrorMessage> getCustomerIdNotFoundException(CustomerIdNotFoundException customerIdNotFoundException){
		List<String> list = new ArrayList<>();
		list.add("Error Type : customerIdNotFoundException");
		list.add("Error Message : "+customerIdNotFoundException.getLocalizedMessage());
		list.add("Timestamp : "+Instant.now().toString());
		ErrorMessage errorMessage = new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.ERROR,"The requested user ID does not exist in the database.", list);
		return ResponseEntity.ok(errorMessage);
	}


}
