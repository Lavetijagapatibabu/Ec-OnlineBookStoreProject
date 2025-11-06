package com.book.service;

import java.util.List;

import com.book.dto.BookModuleDto;
import com.book.entity.BookModule;

public interface IBookModuleService {

	BookModule customerSaveBookDetails(BookModuleDto bookModuleDto);

	List<BookModuleDto> customerGetAllBooks();

	BookModuleDto findCustomerBookDetailsById(Long id);

}
