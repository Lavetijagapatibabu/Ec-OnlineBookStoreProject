package com.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="File_tab")
public class FilesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="file_name_col",nullable = false,length = 150)
	private String fileName;

	@Column(name="file_type_col",nullable=false,length=150)
	private String fileType;


	@Column(columnDefinition = "longblob",name="files_data_col")
	@Lob
	private byte[] data;

	@CreationTimestamp
	@Column(name="created_date_col",updatable = false,nullable=false)
	private LocalDateTime createDate;

	@UpdateTimestamp
	@Column(name="updated_date_col",nullable=false)
	private LocalDateTime updateDate;


}
