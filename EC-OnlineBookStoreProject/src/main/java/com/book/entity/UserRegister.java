package com.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_tab")
public class UserRegister {

	@Id
	@Column(name="user_id_col")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
	@SequenceGenerator(initialValue = 20250001,allocationSize = 1, name = "user_seq")
	private Long id;

	@Column(name="user_fname_col",nullable = false,length = 100)
	private String firstName;

	@Column(name="user_lname_col",nullable = false,length = 100)
	private String lastName;

	@Column(name="user_email_col",nullable = false,length = 150)
	private String email;

	@Column(name="user_password_col",nullable = false,length = 150)
	private String password;

	@Column(name="user_contact_col",nullable=false)
	private Long contactId;

	@CreationTimestamp
	@Column(name="user_creDate_col",nullable = false,updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name="user_upDate_col",nullable = false)
	private LocalDateTime updatedDate;
}
