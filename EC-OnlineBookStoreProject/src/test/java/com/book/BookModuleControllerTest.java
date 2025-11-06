package com.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.book.controller.BookModuleController;
import com.book.dto.BookModuleDto;
import com.book.entity.BookModule;
import com.book.service.IBookModuleService;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Unit test for BookModuleController Using MockMvc
 * This test focuses only one the Controller layer
 */
@WebMvcTest(BookModuleController.class)	//limit test scope to this controller
public class BookModuleControllerTest {

	@Autowired 
	private MockMvc mockMvc; //simulates HTTP requests without starting a real server

	@MockitoBean	//it is avoiding hitting database or business logics
	private IBookModuleService bookModuleService;

	private static final ObjectMapper mapper = new ObjectMapper(); //JSON <==> Object

	@Test
	public void createBookModuleTest() throws Exception {

		//Prepare input Dto(Client Request)
		BookModuleDto bookModuleDto = new BookModuleDto();
		bookModuleDto.setTitle("Na Chavu Nenu Chasta Nekenduku");
		bookModuleDto.setGenre("For Nothi Doola Galiki");
		bookModuleDto.setAuthorName("Jagapatibabu");

		//Prepare mock service response (what controller returns)
		BookModule bookModule = new BookModule();
		bookModuleDto.setTitle("Na Chavu Nenu Chasta Nekenduku");
		bookModuleDto.setGenre("For Nothi Doola Galiki");
		bookModuleDto.setAuthorName("Jagapatibabu");
		//When controller service call method return entity/dto object
		when(bookModuleService.customerSaveBookDetails(bookModuleDto)).thenReturn(bookModule);
		//perform POST request and verify response

		mockMvc.perform(

				post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(bookModuleDto))
				)
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	
	
	

}
