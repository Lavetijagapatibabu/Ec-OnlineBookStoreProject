package com.book.controller;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.model.ResponseMessage;
import com.book.service.IBooksExcelFileService;
import com.book.utility.Constraints;
import com.book.utility.Helper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "BooksExcelFileUploadController", description = "Customer BooksExcelFileUpload and retrieves them")
public class BooksExcelFileController {

	@Autowired IBooksExcelFileService booksExcelFileService;

	@PostMapping("/excelfileupload")
	public ResponseEntity<ResponseMessage> uploadExcelFileData(@RequestParam MultipartFile file) throws IOException {
		try {
			if(Helper.checkExcelFile(file)) {

				booksExcelFileService.uploadExcelFileIntoDatabase(file);

				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS, "uploadExcelFileIntoDatabase Data Uploaded Successfully "));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "uploadExcelFileIntoDatabase Data Uploaded FAILED!!!"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(
					HttpURLConnection.HTTP_INTERNAL_ERROR,Constraints.FAILURE, "Internal server error"));
		}
	}
}
