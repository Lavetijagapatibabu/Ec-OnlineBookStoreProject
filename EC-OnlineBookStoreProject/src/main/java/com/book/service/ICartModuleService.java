package com.book.service;

import com.book.entity.CartModule;

public interface ICartModuleService {

	CartModule addToCartBooks(Long custId, Long bookId, Long qty);
	
}
