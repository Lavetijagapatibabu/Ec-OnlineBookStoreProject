package com.book.controller;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.UserRegister;
import com.book.model.ResponseMessage;
import com.book.model.UserRequestDto;
import com.book.service.IUserRegisterService;
import com.book.utility.Constraints;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "UserRegisterController", description = "Handles User Registration and Login operations")
public class UserRegisterController {

	@Autowired
	private IUserRegisterService userRegisterService;

	private static final Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

	// 1️⃣ Create Single User Registration
	@Operation(summary = "Create User Customers", description = "E-commerce online bookstore user registration")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "User registration saved successfully"),
		@ApiResponse(responseCode = "400", description = "User registration failed"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping("/setUser")
	public ResponseEntity<ResponseMessage> userRegisterDetails(@RequestBody UserRequestDto userRequestDto) {
		logger.info("UserRegisterController: userRegisterDetails() - execution started");
		try {
			if (userRequestDto.getEmail() == null || userRequestDto.getEmail().isEmpty()
					|| userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
				logger.warn("Missing email or password in registration request");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE,
						"User registration failed. Email and password cannot be null or empty.", userRequestDto));
			}

			UserRegister savedUser = userRegisterService.saveUserRegisterDetails(userRequestDto);
			if (savedUser != null) {
				logger.info("BOOKSTORE_USER_REGISTRATION_CREATION_SUCCESS");
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED,
						Constraints.SUCCESS, "User registered successfully.", savedUser));
			} else {
				logger.warn("BOOKSTORE_USER_REGISTRATION_CREATION_FAILED");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(
						HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "User registration failed.", userRequestDto));
			}
		} catch (Exception e) {
			logger.error("User registration failed due to exception: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "Internal server error"));
		}
	}

	// 2️⃣ Register Multiple Users
	@PostMapping("/listUsers")
	public ResponseEntity<ResponseMessage> passListOfUsersRegister(@RequestBody List<UserRequestDto> allUsersList) {
		logger.info("UserRegisterController: passListOfUsersRegister() - execution started");
		if (allUsersList == null || allUsersList.isEmpty()) {
			logger.warn("User registration list is null or empty");
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE,
					"User list cannot be null or empty.", allUsersList));
		}

		List<UserRegister> savedUsers = userRegisterService.saveAllUsersRegisterDetails(allUsersList);
		List<String> emails = StreamSupport.stream(savedUsers.spliterator(), false)
				.map(UserRegister::getEmail).toList();
		logger.info("UserRegisterController: passListOfUsersRegister() - execution completed");
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constraints.SUCCESS,
				"User list registered successfully.", savedUsers, emails));
	}

	// 3️⃣ Fetch User by ID
	@GetMapping("/fetch/userdata/{id}")
	public ResponseEntity<ResponseMessage> getUserFindById(@PathVariable Long id) {
		logger.info("UserRegisterController: getUserFindById() - execution started for ID: {}", id);
		UserRegister userDetails = userRegisterService.findByUserId(id);
		logger.info("UserRegisterController: getUserFindById() - execution completed");
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS,
				"User details fetched successfully.", userDetails));
	}

	// 4️⃣ User Login
	@PostMapping("/userLogin")
	public ResponseEntity<ResponseMessage> checkLogin(@RequestBody UserRequestDto userRequestDto) {
		logger.info("UserRegisterController: checkLogin() - execution started");
		try {
			if (userRequestDto.getEmail() == null || userRequestDto.getEmail().isEmpty()
					|| userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
				logger.warn("Missing email or password in login request");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE,
						"Email and password cannot be empty."));
			}

			UserRegister user = userRegisterService.checkUserLogInDetails(userRequestDto);

			if (user != null) {
				logger.info("Login successful");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS,
						"Loginx` successful.", user));
			} else {
				logger.warn("Invalid credentials");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE,
						"Invalid credentials."));
			}
		} catch (Exception e) {
			logger.error("Login failed due to exception: {}", e.getMessage());
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE,
					"Internal server error"));
		}
	}

	// 5️⃣ Upload Multiple Files with User Data
	@PostMapping("/uploadMultiUserPartFiles")
	public ResponseEntity<ResponseMessage> createUserRegisterUploadFiles(
			@RequestParam String jsonData, @RequestParam MultipartFile[] files) {
		logger.info("UserRegisterController: createUserRegisterUploadFiles() - execution started");
		try {
			UserRequestDto userRequestDto = new ObjectMapper().readValue(jsonData, UserRequestDto.class);
			UserRegister userRegister = userRegisterService.uploadMultipleUserRegistersDetails(userRequestDto, files);

			if (userRegister != null) {
				logger.info("User registration with files completed successfully");
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED,
						Constraints.SUCCESS, "User registration with files completed successfully.", userRegister));
			} else {
				logger.warn("User registration failed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST,
						Constraints.FAILURE, "User registration failed.", userRegister));
			}
		} catch (Exception e) {
			logger.error("createUserRegisterUploadFiles() failed due to exception: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(
					HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "Internal server error"));
		}
	}

	// 6️⃣ Fetch All Users
	@GetMapping("/getAllUsers")
	public List<UserRegister> getAllUserDetails() {
		logger.info("UserRegisterController: getAllUserDetails() - execution started");
		List<UserRegister> allUsers = userRegisterService.getAllUserRegistersDetails();
		logger.info("UserRegisterController: getAllUserDetails() - execution completed");
		return allUsers;
	}
}
