package com.book.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders_table")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="bookId_col")
	private Long bookId;

	@Column(name="customerId_col")
	private Long customerId;

	@Column(name="order_status_col")
	private Boolean status;

	@Column(name="order_creationDate_col",updatable = false)
	private LocalDateTime creationDate;

	@Column(name="order_updateDate_col")
	private LocalDateTime updateDate;
}
