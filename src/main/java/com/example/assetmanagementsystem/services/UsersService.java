package com.example.assetmanagementsystem.services;

import com.example.assetmanagementsystem.dtos.UserAssetsDTO;
import com.example.assetmanagementsystem.dtos.UserPostRequest;
import com.example.assetmanagementsystem.dtos.UserWithAssetsResponse;
import com.example.assetmanagementsystem.entities.UserAssets;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.repositories.UserRepository;
import com.example.assetmanagementsystem.response.RequestResponse;
import com.example.assetmanagementsystem.util.EmailTemplate;
import com.example.assetmanagementsystem.validator.UserValidator;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final UserValidator userValidator;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Value("${from.email.address}")
    private String applicationEmailAddress;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

//    public ResponseEntity<List<UserWithAssetsResponse>> getAllUsersResponse(String keyword, int page, int size) {
//        try {
//            List<Users> users = getAllUsers(keyword, page, size);
//            List<UserWithAssetsResponse> userDTOs = users.stream().map(this::convertToUserWithAssetsResponse).collect(Collectors.toList());
//            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<List<UserWithAssetsResponse>> getAllUsersResponse(String keyword, int page, int size) {
        try {
            List<Users> users = getAllUsers(keyword, page, size);
            List<UserWithAssetsResponse> userDTOs = users.stream()
                    .map(this::convertToUserWithAssetsResponse)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private UserWithAssetsResponse convertToUserWithAssetsResponse(Users user) {
        List<UserAssetsDTO> userAssetsDTOs = user.getUserAssets().stream()
                .map(userAsset -> new UserAssetsDTO(
                        userAsset.getId(),
                        userAsset.getStatus(),
                        userAsset.getAsset().getName(),
                        userAsset.getAsset().getDescription()
                ))
                .collect(Collectors.toList());

        return new UserWithAssetsResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getUserType(),
                user.getEmail(),
                user.getPhone(),
                user.getFname(),
                user.getLname(),
                Math.toIntExact(user.getCreated_by()),
                user.getCreated_at(),
                Math.toIntExact(user.getUpdated_by()),
                user.getUpdated_at(),
                userAssetsDTOs
        );
    }

//    public List<Users> getAllUsers(String keyword, int page, int size) {
//        List<Users> users = userRepository.findAllWithUserAssets();
//        for (Users user : users) {
//            Hibernate.initialize(user.getUserAssets()); // Ensure the collection is loaded
//        }
//        return users;
//    }

    public List<Users> getAllUsers(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id")); // Adjust sorting as needed
        Page<Users> userPage = userRepository.findAllWithUserAssets(pageable, keyword);
        List<Users> users = userPage.getContent();

        for (Users user : users) {
            Hibernate.initialize(user.getUserAssets()); // Ensure the collection is loaded
        }
        return users;
    }


    public ResponseEntity<RequestResponse> processAndSaveUser(UserPostRequest user, String token) {
        token = token.substring(7);
        UserDetails userDetails = jwtService.getUserDetailsFromToken(token, userDetailsService);
        Long userId = null;
        if (userDetails instanceof Users) {
            userId = ((Users) userDetails).getId();
        } else {
            System.out.println("The userDetails object is not an instance of Users.");
        }
        userValidator.validateUserRequest(user, "CREATE");
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setUserType(user.getUser_type());
        newUser.setCreated_by(userId);
        newUser.setUpdated_by(userId);
        newUser.setCreated_at(new Date());
        newUser.setUpdated_at(new Date());
        newUser.setPhone(user.getPhone());
        Users savedUser = saveUser(newUser, "CREATE", user.getPassword());
        RequestResponse response = new RequestResponse("User saved successfully.", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public Users saveUser(Users user, String method, String password) {
        if(method.equals("CREATE")){
            sendUserOnboardingEmail(user, password);
        }

        return userRepository.save(user);
    }

    public ResponseEntity<RequestResponse> deleteUserResponse(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            RequestResponse response = new RequestResponse("User deleted successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            RequestResponse response = new RequestResponse("User not found or deletion failed", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean doesUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public void sendUserOnboardingEmail(Users user, String password) {
        try {
             sendEmail(user.getEmail(),
                    EmailTemplate.USER_ONBOARDING_SUBJECT,
                    MessageFormat.format(EmailTemplate.USER_ONBOARDING_SUCCESS, user.getFname(),
                            user.getLname(),
                            user.getUsername(), password));
        } catch (Exception emailException) {
            //throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error..");
        }


    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress(applicationEmailAddress));
            message.setRecipients(Message.RecipientType.TO, to);
            String encodedSubject = MimeUtility.encodeText(subject, "UTF-8", "B");
            message.setSubject(encodedSubject);
            message.setContent(text, "text/html; charset=utf-8");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send the mail ", e);
        }
    }

    public ResponseEntity<RequestResponse> processUpdateProfile(Long id, UserPostRequest userPostRequest) {
        boolean isUpdated = updateProfile(id, userPostRequest);

        if (isUpdated) {
            return new ResponseEntity<>(
                    new RequestResponse("Profile updated successfully", HttpStatus.OK.value()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new RequestResponse("Request not found or update failed", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public boolean updateProfile(Long id, UserPostRequest userPostRequest) {
        Users user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFname(userPostRequest.getFname());
            user.setLname(userPostRequest.getLname());
            user.setPhone(userPostRequest.getPhone());
            saveUser(user, "UPDATE", "");
            return true;
        }else{
            return false;
        }
    }
}
