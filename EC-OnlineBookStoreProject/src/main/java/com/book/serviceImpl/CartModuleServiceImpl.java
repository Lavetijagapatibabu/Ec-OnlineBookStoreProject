package com.book.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.entity.BookModule;
import com.book.entity.CartModule;
import com.book.entity.Customer;
import com.book.exception.BookIdNotFoundException;
import com.book.exception.CustomerIdNotFoundException;
import com.book.repository.BookModuleRepository;
import com.book.repository.CartModuleRepository;
import com.book.repository.CustomerRepository;
import com.book.service.ICartModuleService;


@Service
public class CartModuleServiceImpl implements ICartModuleService {

	@Autowired private CartModuleRepository cartModuleRepository; 
	
	@Autowired private CustomerRepository customerRepository; 
	
	@Autowired private BookModuleRepository bookModuleRepository;
	
	
	@Override
	public CartModule addToCartBooks(Long custId, Long bookId, Long qty) {
		
		Customer customer = customerRepository.findById(custId).orElseThrow(()-> new CustomerIdNotFoundException("Customer Id Not Found"));
		
		BookModule  bookModule = bookModuleRepository.findById(bookId).orElseThrow(()->new BookIdNotFoundException("Book Id Not Found"));
		
		CartModule cartItem = cartModuleRepository.findByCustomerAndBookModule(customer,bookModule);
		
		if(cartItem != null) {
			
			cartItem.setQuantity(cartItem.getQuantity()+qty);
			
		}else {
			
			 cartItem = new CartModule(qty, customer, bookModule);
		}
		
		//calculating total price 
		
		cartItem.setTotalPrice((cartItem.getQuantity()*bookModule.getPrice()));
		
		cartModuleRepository.save(cartItem);

		return cartItem;
	}

}
