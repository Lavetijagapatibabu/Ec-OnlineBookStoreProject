package com.book.service;

import java.util.List;

import com.book.entity.BookModule;
import com.book.model.BookModuleDto;

public interface IBookModuleService {

	BookModule customerSaveBookDetails(BookModuleDto bookModuleDto);

	List<BookModuleDto> customerGetAllBooks();

	BookModuleDto findCustomerBookDetailsById(Long id);

}
