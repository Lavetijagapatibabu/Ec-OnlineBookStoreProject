package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
