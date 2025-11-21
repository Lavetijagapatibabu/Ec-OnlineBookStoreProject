package com.book.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.book.entity.BookModule;
import com.book.exception.BookIdNotFoundException;
import com.book.model.BookModuleDto;
import com.book.repository.BookModuleRepository;
import com.book.service.IBookModuleService;

@Service
public class BookServiceImpl implements IBookModuleService {

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired ModelMapper modelMapper;

	@Autowired BookModuleRepository bookModuleRepository;

	@Override
	public BookModule customerSaveBookDetails(BookModuleDto bookModuleDto) {
		logger.info("customerSaveBookDetails service layer is calling or Started");
		BookModule bookModule = modelMapper.map(bookModuleDto, BookModule.class);
		BookModule bookmodule = bookModuleRepository.save(bookModule);
		logger.info("customerSaveBookDetails  service layer is calling or Ended");
		return bookmodule;
	}

	@Override
	@Cacheable(value="getallcustomers")
	public List<BookModuleDto> customerGetAllBooks() {
		logger.info("customerGetAllBooks service layer is Calling or Started");
		List<BookModule> llistBooksModule = bookModuleRepository.findAll();
		List<BookModuleDto>  llistBooksModuleDto= llistBooksModule.stream().map(book->modelMapper.map(book,BookModuleDto.class)).collect(Collectors.toList()); //mutable
//		List<BookModuleDto>  llistBooksModuleDto= llistBooksModule.stream().map(book->modelMapper.map(book,BookModuleDto.class)).toList();  immutable
		System.out.println("check the database how many Times get the Records");
		logger.info("customerGetAllBooks service layer is Ending or Stoped ");
		return llistBooksModuleDto;
	}

	@Override
	@Cacheable(value="getcustbooksbyid")
	public BookModuleDto findCustomerBookDetailsById(Long id) {
		logger.info("getCustomerByBookId service layer is Calling or Started");
		BookModule bookModule = bookModuleRepository.findById(id).orElseThrow(()->new BookIdNotFoundException("Book Id Not Found "+id));
		logger.info("getCustomerByBookId service layer is Ending or ended");
		return modelMapper.map(bookModule, BookModuleDto.class);
	}

}
