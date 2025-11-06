package com.book;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.book.controller.UserRegisterController;   // ✅ Add your controller here
import com.book.dto.ResponseMessage;
import com.book.dto.UserRequestDto;
import com.book.entity.UserRegister;
import com.book.service.IUserRegisterService;
import com.book.utility.Constraints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Unit test for UserRegisterController using MockMvc.
 * This test focuses only on the controller layer.
 */
@WebMvcTest(UserRegisterController.class) // ✅ Limit test scope to this controller

public class UserRegisterControllerTest {

	@Autowired
	private MockMvc mockMvc; // ✅ MockMvc simulates HTTP requests without starting a real server

	@MockitoBean
	private IUserRegisterService userRegisterService; // ✅ Mock service to avoid hitting database or business logic

	private static final ObjectMapper mapper = new ObjectMapper(); // ✅ JSON converter

	@Test
	public void userRegisterTest() throws JsonProcessingException,Exception { // ✅ Should not be private, must be 'void' and accessible

		// ✅ Step 1: Prepare input DTO (client request)
		UserRequestDto userDto = new UserRequestDto();
		userDto.setFirstName("Laveti");
		userDto.setLastName("Jagadeesh");
		userDto.setEmail("LavetiJagapatibabu143@gmail.com");
		userDto.setPassword("password");
		userDto.setContactId(9100127255L);

		// ✅ Step 2: Prepare mock service response (what controller returns)
		UserRegister savedUser = new UserRegister();
		savedUser.setFirstName("Laveti");
		savedUser.setLastName("Genesh");
		savedUser.setEmail("LavetiJGenesh143@gmail.com");
		savedUser.setPassword("password");
		savedUser.setContactId(8374102537l);

		// ✅ Step 3: Mock service call behavior
		// When the controller calls service.saveUserRegisterDetails(...),
		// return the savedUser object.
		when(userRegisterService.saveUserRegisterDetails(userDto)).thenReturn(savedUser);

		// ✅ Step 4: Perform POST request and verify response
		mockMvc.perform(
				post("/userregister") // your endpoint mapping
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userDto)) // send DTO as request body
				)
		.andExpect(status().isCreated()) // expect HTTP 201 Created
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)); // expect JSON response
	}
	
	@Test
	public void getAllUsersDetailsTest() throws JsonProcessingException,Exception {
		
		UserRegister user = new UserRegister();
		user.setFirstName("Laveti");
		user.setLastName("Jagapatibabu");
		user.setEmail("LavetiJagapatibabu@Gmail.com");
		user.setPassword("12345");
		user.setContactId(9100127255l);
		
		UserRegister user1 = new UserRegister();
		user.setFirstName("Laveti");
		user.setLastName("Ganesh");
		user.setEmail("LavetiGanesh@Gmail.com");
		user.setPassword("567890");
		user.setContactId(8374102537l);
		
		List<UserRegister> asList = Arrays.asList(user1,user1);
		
		when(userRegisterService.getAllUserRegistersDetails()).thenReturn(asList);
		
		mockMvc.perform(
				get("/getAllUsers")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(asList)));
	}
	
	
	@Test
	public void checkLoginTest() throws JsonProcessingException,Exception{
		
		UserRequestDto userDto = new UserRequestDto();
		userDto.setFirstName("Laveti");
		userDto.setLastName("Jagapatibabu");
		userDto.setEmail("LavetiJagapatibabu143@gmail.com");
		userDto.setPassword("1234");
		
		
		UserRegister mockUser = new UserRegister();
		mockUser.setFirstName("Laveti");
		mockUser.setLastName("Jagapatibabu");
		mockUser.setEmail("LavetiJagapatibabu143@gmail.com");
		
		when(userRegisterService.checkUserLogInDetails(userDto)).thenReturn(mockUser);
		
		mockMvc.perform(
				post("/userLogin")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(userDto))
				)
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json(
			    new ObjectMapper().writeValueAsString(
			        new ResponseMessage(HttpURLConnection.HTTP_OK,
			                            Constraints.SUCCESS,
			                            "Login successful.",
			                            mockUser)
			    ), false  // false → ignore extra fields
			));
	}
	
	
	
	
	
	
	
	
	

}//close class