package com.book.controller;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.OrderModuleDto;
import com.book.model.ResponseMessage;
import com.book.service.IOrderService;
import com.book.utility.Constraints;

@RestController
public class OrderController {
	
	@Autowired IOrderService orderService;
	
	@PostMapping("/orderplaced")
	public ResponseEntity<ResponseMessage> orderCreate(@RequestBody OrderModuleDto orderModuleDto) {

	    try {
	        //  1. Call the service layer to handle order creation logic
	        // The service method (saveOrders) will validate, check prime status, and save the order
	        String saveOrders = orderService.saveOrders(orderModuleDto);

	        //  2. Check if the returned message from service indicates success
	        // Using contains("success") to detect a successful message from the service layer
	        if (saveOrders.toLowerCase().contains("success")) {

	            //  3. Return a success response to the client
	            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constraints.SUCCESS,"Order placed successfully",
	                    saveOrders));

	        } else {
	            //  4. If service returned a failure message, send a bad request response
	            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST,Constraints.FAILURE,"Order placement failed",
	                  saveOrders));
	        }

	    } catch (Exception e) {
	        //  5. In case of any unexpected exceptions, log and return an internal error response
	        e.printStackTrace();
	        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR,Constraints.FAILURE,
	                "Internal server error"));
	    }
	}

}

