package com.book.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IBooksExcelFileService {

	public void uploadExcelFileIntoDatabase(MultipartFile files) throws IOException ;
}
