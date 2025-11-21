package com.book.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IFilesService {

	String saveFile(MultipartFile fille) throws IOException;
	List<Object> saveAllFiles(MultipartFile[] files);

}
