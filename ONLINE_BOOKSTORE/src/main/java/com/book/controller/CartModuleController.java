package com.book.controller;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.entity.CartModule;
import com.book.model.ResponseMessage;
import com.book.service.ICartModuleService;
import com.book.utility.Constraints;

@RestController
public class CartModuleController {

	@Autowired private ICartModuleService cartModuleService;

	@PostMapping("/addcart")
	public ResponseEntity<ResponseMessage> addToCart(@RequestParam Long custId,
			@RequestParam Long bookId,@RequestParam Long qty){

		CartModule cartModule = cartModuleService.addToCartBooks(custId,bookId,qty);
		try {

			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constraints.CREATED,"Customer added cart Successfully",cartModule));

		}catch(Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST,Constraints.FAILURE,"Customer added cart Failed"));

		}

	}



}
