package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.BookModule;

public interface BookModuleRepository extends JpaRepository<BookModule, Long>{

}
