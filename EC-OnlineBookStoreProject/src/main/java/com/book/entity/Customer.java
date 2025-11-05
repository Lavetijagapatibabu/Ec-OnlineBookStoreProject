package com.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Customers_tab")
public class Customer {

	@Id
	@Column(name="cust_id_col")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="cust_name_col",nullable=false,length=100)
	private String name;

	@Column(name="cust_email_col",nullable = false,unique = true,length=150)
	private  String email;

	@CreationTimestamp
	@Column(name="cust_createdDate_col",updatable=false,nullable=false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name="cust_updatedDate_col",nullable=false)
	private LocalDateTime updatedDate;

}
