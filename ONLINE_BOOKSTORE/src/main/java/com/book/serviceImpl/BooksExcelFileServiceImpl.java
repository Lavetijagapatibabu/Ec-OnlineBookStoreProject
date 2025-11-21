package com.book.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.BooksExcelFile;
import com.book.repository.BooksExcelFileRepository;
import com.book.service.IBooksExcelFileService;
import com.book.utility.Helper;

@Service
public class BooksExcelFileServiceImpl implements IBooksExcelFileService {

	@Autowired BooksExcelFileRepository booksExcelFileRepository ;


	@Override
	public void uploadExcelFileIntoDatabase(MultipartFile files) throws IOException {

		List<BooksExcelFile> excelFileStoreIntoDatabase  =  Helper.excelDataStoreIntoDataBase(files.getInputStream());

		booksExcelFileRepository.saveAll(excelFileStoreIntoDatabase);

	}

}
