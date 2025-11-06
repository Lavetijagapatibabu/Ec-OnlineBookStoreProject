package com.book.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.book.dto.CustomerRequestDto;

public interface ICustomerService {

	CustomerRequestDto createCustomer(CustomerRequestDto customerRequestDto);
	CustomerRequestDto updateCustomer(CustomerRequestDto customerRequestDto);
	CustomerRequestDto getCustomerById(Long id);
	List<CustomerRequestDto> getAllCustomers();
	Page<CustomerRequestDto> getAllCustomersWithPagination(int page,int size, String sortField,String pageDirection);


}
