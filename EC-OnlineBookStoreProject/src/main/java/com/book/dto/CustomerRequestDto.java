package com.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequestDto {
	private Long id;
	private String name;
	@NotBlank(message = "Email Can not Blank.")
	@Schema(description = "email",example = "Enter the email")
	private  String email;
}
