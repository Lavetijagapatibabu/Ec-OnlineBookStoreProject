package com.book.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderModuleDto {

	private Long customerId;
	
	private List<String> title;

	
}
