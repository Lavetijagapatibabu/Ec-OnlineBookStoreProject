package com.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ratings") // it is optional
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "rate")
	private int rate;

	@Column(name = "reviewText")
	private String reviewText;


	@ManyToOne
	@JoinColumn(name = "book_id" ,updatable = false)
	private BookModule bookModule;

	@ManyToOne
	@JoinColumn(name = "custmer_id" ,updatable = false)
	private Customer customer;


	@CreationTimestamp
	@Column(name = "createdDate" ,updatable = false)
	public LocalDateTime createdDate;


	@UpdateTimestamp
	@Column(name = "updatedDate")
	public LocalDateTime updatedDate;


}
