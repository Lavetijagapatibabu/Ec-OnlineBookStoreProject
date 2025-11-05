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


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books_excel_file_tab")
public class BooksExcelFile {

	@Id
	@Column(name="book_id_col")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "prod_name_col")
	private String productName;

	@Column(name="prod_desc_col")
	private String description;

	@Column(name="prod_price_col")
	private Double price;

	@CreationTimestamp
	@Column(name="prod_cretion_data_col",updatable = false)
	private LocalDateTime creationDate;

	@UpdateTimestamp
	@Column(name="prod_update_date_col",updatable = true)
	private LocalDateTime updateDate;

}
