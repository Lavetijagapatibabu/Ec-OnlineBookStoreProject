package com.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.book.dto.UserRequestDto;
import com.book.entity.UserRegister;

public interface IUserRegisterService {

	UserRegister saveUserRegisterDetails(UserRequestDto userRequestDto);

	 List<UserRegister> saveAllUsersRegisterDetails(List<UserRequestDto> registerAllUserDetails);

	UserRegister findByUserId(Long id);

	UserRegister checkUserLogInDetails(UserRequestDto userRequestDto);

	UserRegister uploadMultipleUserRegistersDetails(UserRequestDto userRequestDto, MultipartFile[] multipleFiles);

	List<UserRegister> getAllUserRegistersDetails();
}