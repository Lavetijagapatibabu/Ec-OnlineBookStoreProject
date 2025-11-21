package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.BooksExcelFile;

public interface BooksExcelFileRepository extends JpaRepository<BooksExcelFile,Long> {

}
