package com.book.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.entity.BookModule;
import com.book.model.BookModuleDto;
import com.book.model.ResponseMessage;
import com.book.service.IBookModuleService;
import com.book.utility.Constraints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "BooksRegisterController", description = "Customer registers books and retrieves them")
public class BookModuleController {

	@Autowired
	private IBookModuleService bookModuleService;

	private static final Logger logger = LoggerFactory.getLogger(BookModuleController.class);

	// ------------------------------------------------------------
	// Create Book
	// ------------------------------------------------------------
	@Operation(summary = "User Creates Books",
			description = "E-commerce online bookstore register users' books")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "User book saved successfully"),
		@ApiResponse(responseCode = "400", description = "User book save failed"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping("/books")
	public ResponseEntity<ResponseMessage> insertBookDetails(@RequestBody BookModuleDto bookModuleDto) {

		logger.info("BookModuleController: insertBookDetails() - execution started");

		try {
			// Validate request data
			if (bookModuleDto.getTitle() == null || bookModuleDto.getTitle().isEmpty()
					|| bookModuleDto.getGenre() == null || bookModuleDto.getGenre().isEmpty()
					|| bookModuleDto.getAuthorName() == null || bookModuleDto.getAuthorName().isEmpty()) {

				logger.debug("Received book data: {}", bookModuleDto);
				logger.warn("Missing Title, Genre, or AuthorName in registration request");
				logger.error("Book registration failed: Required fields are null or empty");

				return ResponseEntity.ok(
						new ResponseMessage(
								HttpURLConnection.HTTP_BAD_REQUEST,
								Constraints.FAILURE,
								"Book registration failed. Title, Genre, and AuthorName cannot be null or empty.",
								bookModuleDto
								)
						);
			}

			// Save book details
			BookModule savedBook = bookModuleService.customerSaveBookDetails(bookModuleDto);

			if (savedBook != null) {
				logger.info("BOOKSTORE_ONLINE_REGISTRATION_CREATION_SUCCESS");
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ResponseMessage(
								HttpURLConnection.HTTP_CREATED,
								Constraints.CREATED,
								"Book registration completed successfully.",
								savedBook
								));
			} else {
				logger.info("BOOKSTORE_ONLINE_REGISTRATION_CREATION_FAILED");
				logger.warn("Book registration service returned null — registration failed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(
								HttpURLConnection.HTTP_BAD_REQUEST,
								Constraints.FAILURE,
								"Book registration failed.",
								bookModuleDto
								));
			}

		} catch (Exception e) {
			logger.error("Error occurred in insertBookDetails(): {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(
							HttpURLConnection.HTTP_INTERNAL_ERROR,
							Constraints.FAILURE,
							"Internal server error"
							));
		}
	}

	// ------------------------------------------------------------
	// Get All Books
	// ------------------------------------------------------------
	@GetMapping("/books")
	public ResponseEntity<ResponseMessage> customerFetchAllBooks() {

		logger.info("BookModuleController: customerFetchAllBooks() - execution started");

		List<BookModuleDto> allBooks = bookModuleService.customerGetAllBooks();

		if (allBooks != null) {
			logger.info("customerFetchAllBooks() - successfully fetched book list");

			List<String> bookTitles = allBooks.stream()
					.map(BookModuleDto::getTitle)
					.toList();

			return ResponseEntity.ok(
					new ResponseMessage(
							HttpURLConnection.HTTP_OK,
							Constraints.SUCCESS,
							"Customer successfully fetched all book records",
							allBooks,
							bookTitles
							)
					);
		} else {
			logger.info("BOOKSTORE_ONLINE_GETTING_FAILED");
			logger.warn("customerGetAllBooks() returned null — fetch failed");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(
							HttpURLConnection.HTTP_BAD_REQUEST,
							Constraints.FAILURE,
							"Customer fetch all books failed",
							allBooks
							));
		}
	}

	// ------------------------------------------------------------
	// Get Book by ID
	// ------------------------------------------------------------
	@GetMapping("/books/{id}")
	public ResponseEntity<ResponseMessage> retrieveCustomerBookDetailsById(@PathVariable Long id) {

		logger.info("BookModuleController: retrieveCustomerBookDetailsById() - execution started for ID: {}", id);

		BookModuleDto bookDetails = bookModuleService.findCustomerBookDetailsById(id);

		logger.info("BookModuleController: retrieveCustomerBookDetailsById() - execution completed");

		return ResponseEntity.ok(
				new ResponseMessage(
						HttpURLConnection.HTTP_OK,
						Constraints.SUCCESS,
						"Customer successfully fetched the book details by ID",
						bookDetails
						)
				);
	}
}
