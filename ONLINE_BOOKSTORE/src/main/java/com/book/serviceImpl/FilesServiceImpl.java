package com.book.serviceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.FilesEntity;
import com.book.repository.FilesEntityRepository;
import com.book.service.IFilesService;

@Service
public class FilesServiceImpl implements IFilesService {

	private final static Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

	@Autowired FilesEntityRepository filesEntityRepository;

	@Override
	public String saveFile(MultipartFile file) throws IOException {
		logger.info("saveFile serviceImpl layer started or Calling");
		FilesEntity fes=new FilesEntity();
		fes.setFileName(file.getOriginalFilename());
		fes.setFileType(file.getContentType());
		fes.setData(file.getBytes());
		logger.info("saveFile serviceImpl layer Calling save method");
		FilesEntity saveFileEntity = filesEntityRepository.save(fes);
		logger.info("saveFile serviceImpl layer Completed or Ending");
		return saveFileEntity.getFileName();
	}

	@Override
	public List<Object> saveAllFiles(MultipartFile[] files) {
		logger.info("saveAllFiles serviceImpl layer started or Calling");
		List<Object> responseSaveAllFiles = Arrays.stream(files)
					.map(file->{
						try {
							logger.info("saveAllFiles serviceImpl layer Calling saveFile");
							return saveFile(file);
						}catch (Exception e) {
							logger.error("saveAllFiles serviceImpl layer Returned Error FILES UPLOAD FAILED ",e);
							return "files upload failed" +e.getLocalizedMessage();
						}
					}).collect(Collectors.toList());
		logger.info("saveAllFiles serviceImpl layer Completed or Ended");
		return responseSaveAllFiles;
	}
}
