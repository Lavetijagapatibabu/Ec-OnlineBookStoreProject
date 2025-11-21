package com.book.mongoEntity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collation = "ecombook")
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterMongo {
	
	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private Long contactId;

}
