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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="cartModule_table")
public class CartModule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id_col")
	private Long id;

	@Column(name="cart_qty_col")
	@NonNull
	private Long quantity;

	@Column(name="cart_totPrice_col")
	private Double totalPrice;

	@ManyToOne
	@JoinColumn(name="cust_id_col")
	@NonNull
	private Customer customer;

	@ManyToOne
	@JoinColumn(name="book_id_col")
	@NonNull
	private BookModule bookModule;

	@CreationTimestamp
	@Column(name="book_creationDate_col" ,updatable = false)
	private LocalDateTime creationDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;


}
