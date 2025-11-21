package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.entity.BookModule;
import com.book.entity.CartModule;
import com.book.entity.Customer;

@Repository
public interface CartModuleRepository extends JpaRepository<CartModule, Long>{

	CartModule findByCustomerAndBookModule(Customer customer, BookModule bookModule);

}
