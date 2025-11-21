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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.book.controller.UserRegisterController;
import com.book.entity.UserRegister;
import com.book.model.ResponseMessage;
import com.book.model.UserRequestDto;
import com.book.service.IUserRegisterService;
import com.book.utility.Constraints;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserRegisterController.class)
public class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRegisterService userRegisterService;

    // Mock JwtUtilService and AppFilter if your controller indirectly depends on them
    @MockBean
    private com.book.utility.JwtUtilService jwtUtilService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void userRegisterTest() throws Exception {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setFirstName("Laveti");
        userDto.setLastName("Jagadeesh");
        userDto.setEmail("LavetiJagapatibabu143@gmail.com");
        userDto.setPassword("password");
        userDto.setContactId(9100127255L);

        UserRegister savedUser = new UserRegister();
        savedUser.setFirstName("Laveti");
        savedUser.setLastName("Genesh");
        savedUser.setEmail("LavetiJGenesh143@gmail.com");
        savedUser.setPassword("password");
        savedUser.setContactId(8374102537L);

        when(userRegisterService.saveUserRegisterDetails(userDto)).thenReturn(savedUser);

        mockMvc.perform(post("/userregister")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(savedUser)));
    }

    @Test
    public void getAllUsersDetailsTest() throws Exception {
        UserRegister user1 = new UserRegister();
        user1.setFirstName("Laveti");
        user1.setLastName("Jagapatibabu");
        user1.setEmail("LavetiJagapatibabu@Gmail.com");
        user1.setPassword("12345");
        user1.setContactId(9100127255L);

        UserRegister user2 = new UserRegister();
        user2.setFirstName("Laveti");
        user2.setLastName("Ganesh");
        user2.setEmail("LavetiGanesh@Gmail.com");
        user2.setPassword("567890");
        user2.setContactId(8374102537L);

        List<UserRegister> userList = Arrays.asList(user1, user2);

        when(userRegisterService.getAllUserRegistersDetails()).thenReturn(userList);

        mockMvc.perform(get("/getAllUsers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(userList)));
    }

    @Test
    public void checkLoginTest() throws Exception {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setEmail("LavetiJagapatibabu143@gmail.com");
        userDto.setPassword("1234");

        UserRegister mockUser = new UserRegister();
        mockUser.setFirstName("Laveti");
        mockUser.setLastName("Jagapatibabu");
        mockUser.setEmail("LavetiJagapatibabu143@gmail.com");

        when(userRegisterService.checkUserLogInDetails(userDto)).thenReturn(mockUser);

        ResponseMessage expectedResponse = new ResponseMessage(
                HttpURLConnection.HTTP_OK,
                Constraints.SUCCESS,
                "Login successful.",
                mockUser
        );

        mockMvc.perform(post("/userLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse), false));
    }
}
