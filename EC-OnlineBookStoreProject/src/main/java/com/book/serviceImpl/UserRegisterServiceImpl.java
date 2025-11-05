package com.book.serviceImpl;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.FilesEntity;
import com.book.entity.UserRegister;
import com.book.exception.UserIdNotFoundException;
import com.book.model.UserRequestDto;
import com.book.mongoEntity.UserRegisterMongo;
import com.book.mongoRepository.UserRegisterMongoRepo;
import com.book.repository.FilesEntityRepository;
import com.book.repository.UserRegisterRepository;
import com.book.service.IUserRegisterService;

@Service
public class UserRegisterServiceImpl implements IUserRegisterService {

	private final static Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

	@Autowired	private UserRegisterRepository userRegisterRepository;

	@Autowired private ModelMapper modelMapper;

	@Autowired private FilesEntityRepository filesEntityRepository;
	
	@Autowired private UserRegisterMongoRepo userMongoRepo ;

	//	1st method
	@Override
	public UserRegister saveUserRegisterDetails(UserRequestDto userRequestDto) {
		logger.info("UserRegister service layer calling or started");//1st
		UserRegister user = new UserRegister();
		try {
			user.setFirstName(userRequestDto.getFirstName());
			user.setLastName(userRequestDto.getLastName());
			user.setEmail(userRequestDto.getEmail());
			user.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
			user.setContactId(userRequestDto.getContactId());
			logger.info("saveUserRegisterDetails serive layer calling or ended");//2nd
			user=userRegisterRepository.save(user);
			logger.info("User registration completed successfully for user: {}", userRequestDto.getEmail());
			
			//Mongo configurations
			UserRegisterMongo userMongo = new UserRegisterMongo();
			modelMapper.map(userRequestDto, userMongo);
			userMongoRepo.save(userMongo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create new user in Bookstore-DB. Cause: {}",e.getMessage(), e);
			
		}
		return user;
	}


	//	2nd method
	@Override
	public List<UserRegister> saveAllUsersRegisterDetails(List<UserRequestDto> registerAllUserDetails) {
		logger.info("AllUsersRegister servcie layer calling or started");
		List<UserRegister> saveAllUserRegister = registerAllUserDetails.stream().map(dto -> {

			logger.info("registerAllUserDetails serive layer calling or ended");

			UserRegister user = modelMapper.map(dto, UserRegister.class);

			if (dto.getEmail() != null && ! dto.getEmail().isBlank()) {
				user.setEmail(dto.getEmail());
				user.setPassword(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));
				logger.info("Users registration's completed successfully for user: {}",user.getEmail());
			} else {
				throw new UserIdNotFoundException("Gmail Should Not Be Null");
			}
			return user;
		}).collect(Collectors.toList());

		return userRegisterRepository.saveAll(saveAllUserRegister);
	}


	//3rd method
	@Override
	public UserRegister findByUserId(Long id) {
		logger.info("findByUserId UserRegister  service layer calling or started");
		UserRegister userRegister = userRegisterRepository.findById(id).orElseThrow(
				()->new UserIdNotFoundException("User ID Not Found  "+id)
				);
		System.err.println(userRegister.getPassword());
		String pwdDecode = new String(Base64.getDecoder().decode(userRegister.getPassword()));
		userRegister.setPassword(pwdDecode);
		
		logger.info("User found with ID {}: ", id, userRegister.getEmail());
		return userRegister;
	}

	//4th method

	@Override
	public UserRegister checkUserLogInDetails(UserRequestDto userRequestDto) {
		logger.info("checkUserDetails  service layer calling or started");
		UserRegister userRegisterFindEmail =userRegisterRepository.findByEmail(userRequestDto.getEmail());
		if(userRegisterFindEmail!=null) {
			String pwdDecode = new String(Base64.getDecoder().decode(userRegisterFindEmail.getPassword()));
			
			if(pwdDecode.equals(userRequestDto.getPassword())){
				logger.info("checkUserDetails checked successfully for this user:{}",userRequestDto);
				return userRegisterFindEmail;
			}
		}
		return userRegisterFindEmail;
	}


	//5th method

	@Override
	public UserRegister uploadMultipleUserRegistersDetails(UserRequestDto userRequestDto,MultipartFile[] multiFiles) {
		logger.info("uploadMultipleUserRegistersDetails  service layer calling or started");
		UserRegister userRegister = new UserRegister();
		try {
			userRegister = modelMapper.map(userRequestDto, UserRegister.class);
			userRegister.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
			logger.info("userRegisterRepository  save method calling or Ended");
			userRegister = userRegisterRepository.save(userRegister);
			if(multiFiles!=null && multiFiles.length > 0) {
				logger.info("multipartfiles service layer calling or started");
				for(MultipartFile multipartFile : multiFiles) {
					FilesEntity fss= new FilesEntity();
					fss.setFileName(multipartFile.getOriginalFilename());
					fss.setFileType(multipartFile.getContentType());
					fss.setData(multipartFile.getBytes());
					logger.info("multipartfiles filesEntityRepository calling or Ended ");
					filesEntityRepository.save(fss);
				}
			}
		}catch (Exception e) {
			logger.error("Failed to create new user...", e);
		}
		return userRegister;
	}

	//6th method
	@Override
	@Cacheable(value = "getAllUsers")
	public List<UserRegister> getAllUserRegistersDetails() {
		logger.info("getAllUserRegistersDetails service layer calling or started");
		List<UserRegister> allUsersList = userRegisterRepository.findAll();
		allUsersList.stream().map(user->{
				String decPwd = new String(Base64.getDecoder().decode(user.getPassword()));
				user.setPassword(decPwd);
				return user; 
				}
				).collect(Collectors.toList());
		logger.info("getAllUserRegistersDetails service layer calling or Ended");
		return allUsersList;
	}


}
