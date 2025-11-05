package com.book.controller;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.CustomerRequestDto;
import com.book.model.ResponseMessage;
import com.book.service.ICustomerService;
import com.book.utility.Constraints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "CustomerController", description = "Customer registers Into ECommerece BookStore")
public class CustomerController {
	
		@Autowired ICustomerService customerService ;
	
		public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
		@Operation(summary = "Create User Custmers",description = "e commerece online books store  register the users")
		@ApiResponses({
			@ApiResponse(responseCode = "201",description = "user register successfully"),
			@ApiResponse(responseCode = "400",description = "user register failure"),
			@ApiResponse(responseCode = "500",description = "Internal server error")
		})
	
		@PostMapping("/customersave")
		public ResponseEntity<ResponseMessage> registerCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
	
			logger.info("CustomerController : registerCustomer() - execution started");
	
			try {
				if(customerRequestDto.getEmail()==null || customerRequestDto.getEmail().isEmpty() || customerRequestDto.getName() ==null || customerRequestDto.getName().isEmpty()) {
	
					logger.debug("Received customer registration data: {}", customerRequestDto);
					logger.warn("Missing email or password in registration request");
					logger.error("Customer registration failed: email or password missing");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "Customer registration failed. Email and password cannot be null or empty."));
				}
	
				logger.info("CustomerController layer : customerService: createCustomer() - Calling the service Methode");
				customerRequestDto = customerService.createCustomer(customerRequestDto);
	
				if(customerRequestDto != null) {
	
					logger.info("BOOKSTORE_CUSTOMER_REGISTRATION_CREATION_SUCCESS");
					logger.info("CustomerController : registerCustomer() - execution Completed");
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constraints.SUCCESS, "Customer registered successfully. With this ID ::"+customerRequestDto.getId(),customerRequestDto));
	
				}else {
	
					logger.info("BOOKSTORE_CUSTOMER_REGISTRATION_CREATION_FAILED");
					logger.warn("Customer Registration Service Returned Null");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage( HttpURLConnection.HTTP_BAD_REQUEST,Constraints.FAILURE,"Customer Registration Failed.",customerRequestDto));
	
				}
			}catch (Exception e) {
				logger.error("User Registration Failed Due to Exception: {}", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "INTERNAL SERVER ERROR"));
			}
	
		}
	
	
		//	--------------------------------------
	
		@PutMapping("/updatecustomer")
		public ResponseEntity<ResponseMessage> modifyCustomerDetails(@RequestBody CustomerRequestDto customerRequestDto ){
	
			logger.info("CustomerController : modifyCustomerDetails() - execution started ");
	
			try {
				if( customerRequestDto.getEmail()==null || customerRequestDto.getEmail().isEmpty() || customerRequestDto.getName() ==null || customerRequestDto.getName().isEmpty()) {
				
					logger.debug("Received customer registration data: {}", customerRequestDto);
					logger.warn("Missing email or password in registration request");
					logger.error("Customer registration failed: email or password missing");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "Customer registration failed. Email and password cannot be null or empty."));
				}
	
				if(customerRequestDto.getId()==null) {
	
					logger.info("CustomerController layer : customerService: updateCustomer() - Calling the service Methode");
					customerRequestDto = customerService.updateCustomer(customerRequestDto);
	
					logger.info("CustomerController : modifyCustomerDetails() - execution Completed ");
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constraints.CREATED, "Customer Created Successfully",customerRequestDto));
				} else {
	
					customerRequestDto = customerService.updateCustomer(customerRequestDto);
	
					logger.info("CustomerController : modifyCustomerDetails() - execution Completed ");
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS, "Customer Updated Successfully",customerRequestDto));
				}
			}catch (Exception e) {
				logger.error("Customer Update failed due to exception: {}", e.getMessage());
				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "Internal server error"));
	
			}
	
		}
	
		//	---------------------------------------------------
	
	
		@GetMapping("/reterivecusomer/{id}")
		public ResponseEntity<ResponseMessage>  retrieveCustomerById(@PathVariable Long id ){
			logger.info("CustomerController : retrieveCustomerById() - execution started ");
	
			CustomerRequestDto returnCustomerRequestDto = customerService.getCustomerById(id);
	
			if(returnCustomerRequestDto != null) {
	
				logger.info("CustomerController : retrieveCustomerById() - execution Completed ");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constraints.SUCCESS," Customer Id Getting Successfully",returnCustomerRequestDto));
			}else {
				logger.info("BOOKSTORE_CUSTOMER ID NOT FOUND");
				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST,Constraints.ERROR,"Customer Id  Failed to Success ::"+id));
			}
	
		}
	
		@GetMapping("/getallrecords")
		public ResponseEntity<ResponseMessage> listAllCustomers() {
			
			logger.info("CustomerController : listAllCustomers() - execution started ");
			List<CustomerRequestDto> allCustomersOfList = customerService.getAllCustomers();
	
			if(allCustomersOfList!=null) {
				logger.info("CustomerController : listAllCustomers() - execution Completed ");
				List<String> allCustomerMails = allCustomersOfList.stream().map(name->name.getEmail()).collect(Collectors.toList());
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS, "Customer Getting All Records Successfully", allCustomersOfList,allCustomerMails));
	
			}else {
				logger.error("BOOK-ECOMMERECE FETCHING CUSTOMER ALL RECORDS GETTING FAILED");
				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "Customer Records Failed to Getting ", allCustomersOfList));
	
			}
		}  
	
		//-----------------------------------------------
	
		@GetMapping("/getAllCustmerswithpagination")
		public ResponseEntity<ResponseMessage> getByAllCustmerpagination(@RequestParam int page , 
				@RequestParam int size,
				@RequestParam String sortField,
				@RequestParam String pageDir) {
	
			logger.info("CustomerController : getByAllCustmerpagination() - execution started ");
			Page<CustomerRequestDto> byAllCustmersWithPaginations = customerService.getAllCustomersWithPagination(page,size,sortField,pageDir);
	
			if(byAllCustmersWithPaginations!=null) {
	
				logger.info("CustomerController : getByAllCustmerpagination() - execution Completed ");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS, "All Custemers getting with pagination successfully", byAllCustmersWithPaginations));
			}else { 
	
				logger.info("CustomerController : getByAllCustmerpagination() - execution FAILED ");
				return ResponseEntity.status(HttpURLConnection.HTTP_BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "All Custemers getting  pagination Failure", byAllCustmersWithPaginations));
	
			}
	
		}
}  

