package com.book.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.dto.ResponseMessage;
import com.book.service.IFilesService;
import com.book.utility.Constraints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "FilesUploadController ",description = "Files Upload into DataBase")
public class FilesController {

	@Autowired private IFilesService filesService;

	private static final Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Operation(summary = "Files Uploads Into DB",description = "ec commerece online books store  Storing the Files")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "files saved successfully"),
		@ApiResponse(responseCode = "400",description = "files stored failure"),
		@ApiResponse(responseCode = "500",description = "Internal server error")
	})

	@PostMapping("/uploadfile")
	public ResponseEntity<ResponseMessage> insertFileIntoDB(@RequestParam MultipartFile file){
		logger.info("insertFileIntoDB FileController Calling or started");
		//validations
		if(file.isEmpty()||file.getName()==null||file.getContentType()==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constraints.FAILURE, "File should not contain null name,contentType and blank "));
		}
		try {
			String saveFile = filesService.saveFile(file);
			logger.info("insertFileIntoDB FileController Calling save method with filesService or Ended");
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constraints.SUCCESS, saveFile,"Sucdesfully File Stored Into DB"));
		}catch (Exception e) {
			logger.error("FilesController layer Ecommerece ERROR {}",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constraints.FAILURE, "Internal server error"));
		}
	}

	@PostMapping("/uploadmultifiles")
	public ResponseEntity<ResponseMessage> storeMultipleFilesintoDB(@RequestParam MultipartFile[] files){
		logger.info("FilesController layer storeMultipleFilesintoDB method started or Calling");
		List<Object> saveAllFiles = filesService.saveAllFiles(files);
		logger.info("FilesController layer storeMultipleFilesintoDB method completed or Ending");
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constraints.SUCCESS, "Successfully passed Multiple Files Into DB", saveAllFiles));
	}

}
