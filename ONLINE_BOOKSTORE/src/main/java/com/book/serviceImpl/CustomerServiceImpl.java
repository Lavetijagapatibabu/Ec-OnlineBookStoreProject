package com.book.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.book.entity.Customer;
import com.book.exception.CustomerIdNotFoundException;
import com.book.model.CustomerRequestDto;
import com.book.repository.CustomerRepository;
import com.book.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired private CustomerRepository customerRepository;

	@Autowired private  ModelMapper modelMapper;


	@Override
	public CustomerRequestDto createCustomer(CustomerRequestDto customerRequestDto) {
		logger.info("CustomerServiceImpl: createCustomer() - execution started");

		logger.debug("Mapping CustomerRequestDto to Customer entity");
		Customer customer = modelMapper.map(customerRequestDto, Customer.class);

		logger.info("CustomerServiceImpl: createCustomer() - saving customer to database");
		Customer savedCustomer = customerRepository.save(customer);
		logger.info("CustomerServiceImpl: createCustomer() - customer saved successfully with ID: {}", savedCustomer.getId());

		logger.debug("Mapping saved Customer entity back to CustomerRequestDto");
		CustomerRequestDto response = modelMapper.map(savedCustomer, CustomerRequestDto.class);

		logger.info("CustomerServiceImpl: createCustomer() - execution completed");
		return response;
	}

	@Override
	public CustomerRequestDto updateCustomer(CustomerRequestDto customerRequestDto) {
		logger.info("CustomerServiceImpl: updateCustomer() - execution started");

		logger.debug("Mapping CustomerRequestDto to Customer entity");
		Customer customer = modelMapper.map(customerRequestDto, Customer.class);

		if(customer.getId()==customerRequestDto.getId()) {

			logger.info("CustomerServiceImpl:updateCustomer() - Updating customer from database");
			customer = customerRepository.save(customer);
		}
		logger.info("CustomerServiceImpl: udateCustomer() - customer updated successfully with ID: {}",customer.getId());

		logger.debug("Mapping saved Customer entity back to CustomerRequestDto");
		CustomerRequestDto response = modelMapper.map(customer, CustomerRequestDto.class);

		logger.info("CustomerServiceImpl: updateCustomer() - execution completed");
		return response;
	}

	@Override
	public CustomerRequestDto getCustomerById(Long id) {
		logger.info("CustomerServiceImpl: getCustomerById() - execution started");

		//		 customerRepository.findById(id).orElseThrow(()->new CustomerIdNotFoundException("Customer Not Found..! "));

		logger.info("CustomerServiceImpl: getCustomerById() - finding customer from database");
		Optional<Customer> byId = customerRepository.findById(id);

		if(!byId.isPresent()) {

			logger.warn("CustomerServiceImpl: Customer ID {} not found", id);
			throw new CustomerIdNotFoundException("Customer ID Not Found");
		}
		logger.debug("Mapping saved Customer entity back to CustomerRequestDto");
		CustomerRequestDto  CustomerRequestDtoResponse= modelMapper.map(byId, CustomerRequestDto.class);

		logger.info("CustomerServiceImpl: getCustomerById() - execution Completed");
		return CustomerRequestDtoResponse;
	}

	@Override
	public List<CustomerRequestDto> getAllCustomers() {
		logger.info("CustomerServiceImpl: getAllCustomers() - execution started");
		logger.info("CustomerServiceImpl: getAllCustomers() - getting all customers from database");
		List<Customer> allCustomers = customerRepository.findAll();
		logger.info("Mapping findAll customers back to CustomerRequestDto as list ");
		List<CustomerRequestDto> listCustomerRequestDto = allCustomers.stream()
				.map(customer->modelMapper.map(customer, CustomerRequestDto.class)).collect(Collectors.toList());
		logger.info("CustomerServiceImpl: getAllCustomers() - execution Completed");
		return listCustomerRequestDto;
	}

	@Override
	public Page<CustomerRequestDto> getAllCustomersWithPagination(int page, int size, String sortField,
			String pageDirection) {

		logger.info("CustomerServiceImpl: getAllCustomersWithPagination() - execution started");
		logger.debug("CustomerServiceImpl: Creating Srot Object by using pageDirection and sortFiled");
		Sort sort = pageDirection.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

		logger.debug("CustomerServiceImpl: PageRequest.of() method passing values as page size sort as get pageRequest obj");
		PageRequest pageRequest = PageRequest.of(page, size, sort);

		logger.info("CustomerServiceImpl: getAllCustomersWithPagination() - getting  All Customers as customersPageable");
		Page<Customer> customersPageable = customerRepository.findAll(pageRequest);

		logger.info("CustomerServiceImpl: getAllCustomersWithPagination() - execution Completed");
		
		return customersPageable.map(customer->modelMapper.map(customer, CustomerRequestDto.class));
	}

}