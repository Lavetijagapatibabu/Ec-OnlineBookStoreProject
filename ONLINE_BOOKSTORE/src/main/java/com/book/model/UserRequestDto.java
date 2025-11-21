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
public class UserRequestDto {

	private Long id;
	private String firstName;
	private String lastName;

	@NotBlank(message = "Email Can not Blank.")
	@Schema(description = "email",example = "Enter the email")
	@Column(name="email")
	private String email;

	@NotBlank(message = "Password can not Blank.")
	@Schema(description = "password",example = "Enter the password")
	@Column(name="password")
	private String password;
	
	private Boolean prime;
	
	private Long contactId;


}
