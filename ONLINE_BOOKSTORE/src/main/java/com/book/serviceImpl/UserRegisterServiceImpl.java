package com.book.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserRegisterServiceImpl implements IUserRegisterService, org.springframework.security.core.userdetails.UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FilesEntityRepository filesEntityRepository;

    @Autowired
    private UserRegisterMongoRepo userMongoRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Use BCrypt for secure passwords

    // -------------------------
    // 1st method: Save single user
    // -------------------------
    @Override
    public UserRegister saveUserRegisterDetails(UserRequestDto userRequestDto) {
        logger.info("UserRegister service layer started");
        UserRegister user = new UserRegister();
        try {
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setEmail(userRequestDto.getEmail());

            // -----------------------------
            // Base64 (old, insecure) - commented
            // user.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
            // -----------------------------

            // BCrypt encoding (recommended)
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

            user.setContactId(userRequestDto.getContactId());
            user.setPrime(userRequestDto.getPrime());
            user = userRegisterRepository.save(user);

            // Save in MongoDB
            UserRegisterMongo userMongo = new UserRegisterMongo();
            modelMapper.map(userRequestDto, userMongo);
            userMongoRepo.save(userMongo);

            logger.info("User registration completed successfully for user: {}", userRequestDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to create new user in Bookstore-DB. Cause: {}", e.getMessage(), e);
        }
        return user;
    }

    // -------------------------
    // 2nd method: Save multiple users
    // -------------------------
    @Override
    public List<UserRegister> saveAllUsersRegisterDetails(List<UserRequestDto> registerAllUserDetails) {
        logger.info("AllUsersRegister service layer started");

        List<UserRegister> saveAllUserRegister = registerAllUserDetails.stream().map(dto -> {
            UserRegister user = modelMapper.map(dto, UserRegister.class);

            if (dto.getEmail() != null && !dto.getEmail().isBlank()) {

                user.setEmail(dto.getEmail());

                // -----------------------------
                // Base64 encoding (old) - commented
                // user.setPassword(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));
                // -----------------------------

                // BCrypt encoding (recommended)
                user.setPassword(passwordEncoder.encode(dto.getPassword()));

            } else {
                throw new UserIdNotFoundException("Email should not be null");
            }
            return user;
        }).collect(Collectors.toList());

        return userRegisterRepository.saveAll(saveAllUserRegister);
    }

    // -------------------------
    // 3rd method: Find by ID
    // -------------------------
    @Override
    public UserRegister findByUserId(Long id) {
        UserRegister userRegister = userRegisterRepository.findById(id)
                .orElseThrow(() -> new UserIdNotFoundException("User ID Not Found " + id));
        return userRegister;
    }

    // -------------------------
    // 4th method: Check login credentials
    // -------------------------
    @Override
    public UserRegister checkUserLogInDetails(UserRequestDto userRequestDto) {
        UserRegister user = userRegisterRepository.findByEmail(userRequestDto.getEmail());
        if (user != null) {

            // -----------------------------
            // Base64 check (old) - commented
            // String pwdDecode = new String(Base64.getDecoder().decode(user.getPassword()));
            // if (pwdDecode.equals(userRequestDto.getPassword())) { ... }
            // -----------------------------

            // BCrypt check (recommended)
            if (passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    // -------------------------
    // 5th method: Upload multiple files
    // -------------------------
    @Override
    public UserRegister uploadMultipleUserRegistersDetails(UserRequestDto userRequestDto, MultipartFile[] multiFiles) {
        UserRegister userRegister = new UserRegister();
        try {
            userRegister = modelMapper.map(userRequestDto, UserRegister.class);

            // -----------------------------
            // Base64 (old) - commented
            // userRegister.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
            // -----------------------------

            // BCrypt encoding
            userRegister.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

            userRegister = userRegisterRepository.save(userRegister);

            if (multiFiles != null && multiFiles.length > 0) {
                for (MultipartFile multipartFile : multiFiles) {
                    FilesEntity fss = new FilesEntity();
                    fss.setFileName(multipartFile.getOriginalFilename());
                    fss.setFileType(multipartFile.getContentType());
                    fss.setData(multipartFile.getBytes());
                    filesEntityRepository.save(fss);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRegister;
    }

    // -------------------------
    // 6th method: Get all users
    // -------------------------
    @Override
    @Cacheable(value = "getAllUsers")
    public List<UserRegister> getAllUserRegistersDetails() {
        List<UserRegister> allUsersList = userRegisterRepository.findAll();

        // Optional: Remove Base64 decode, passwords are already BCrypt
        return allUsersList;
    }

    // -------------------------
    // UserDetailsService implementation
    // -------------------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserRegister user = userRegisterRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

}
