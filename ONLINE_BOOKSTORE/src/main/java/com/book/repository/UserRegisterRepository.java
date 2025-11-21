package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.UserRegister;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> {

	UserRegister findByEmail(String email);

}
