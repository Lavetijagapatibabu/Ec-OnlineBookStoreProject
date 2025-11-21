package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.Ratings;

public interface RatingsRepository extends JpaRepository<Ratings, Long>{

	
}
