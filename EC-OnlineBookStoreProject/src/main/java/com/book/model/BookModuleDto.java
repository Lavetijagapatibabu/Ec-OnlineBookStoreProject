package com.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModuleDto {

	private Long id;

	@NotBlank(message = "title Can not Blank.")
	@Schema(description = "title",example = "Enter the title")
	@Column(name="title")
	private String title;

	@NotBlank(message = "Genre is required")
	private String genre;

	@NotBlank(message = "authorName Can not Blank.")
	@Schema(description = "authorName",example = "Enter the authorName")
	@Column(name="authorName")
	private String authorName;
	
	private Double price;
	
}
