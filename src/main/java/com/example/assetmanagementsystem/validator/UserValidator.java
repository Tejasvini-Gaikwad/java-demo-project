package com.example.assetmanagementsystem.validator;

import com.example.assetmanagementsystem.config.AssetManagementConfig;
import com.example.assetmanagementsystem.constants.AppConstants;
import com.example.assetmanagementsystem.constants.ErrorConstantString;
import com.example.assetmanagementsystem.constants.ErrorConstants;
import com.example.assetmanagementsystem.dtos.UserPostRequest;
import com.example.assetmanagementsystem.exception.ErrorDto;
import com.example.assetmanagementsystem.exception.ValidationException;
import com.example.assetmanagementsystem.repositories.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final AssetManagementConfig assetManagementConfig;

    public void validateUserRequest(UserPostRequest user, String action){
        List<ErrorDto> errors = userBasicValidation(user, action);
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

    private List<ErrorDto> userBasicValidation(UserPostRequest user, String action) {
        List<ErrorDto> errors = new ArrayList<>();

        validateUserName(user, action, errors);
        validateFirstName(user, errors);
        validateLastName(user, errors);
        if (action.equalsIgnoreCase(AppConstants.CREATE)) {
            validatePassword(user, errors);
        }
        validateEmail(user, errors);
        validatePhone(user, errors);
        return errors;
    }

    private void validateUserName(UserPostRequest user, String action, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getUsername())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.USERNAME)));
        } else if (AppConstants.CREATE.equalsIgnoreCase(action) && isUsernameExists(user.getUsername())) {
            errors.add(new ErrorDto(ErrorConstants.ALREADY_PRESENT_ERROR_CODE,
                    ErrorConstantString.alreadyPresentErrorMessage(AppConstants.USERNAME, user.getUsername())));
        } else if(!Pattern.matches(assetManagementConfig.getRegExUserName(), user.getUsername())){
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE, ErrorConstantString.notValidErrorMessageUsername()));
        }
    }

    private boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private void validateFirstName(UserPostRequest user, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getFname())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.FIRST_NAME)));
        } else if(!Pattern.matches(assetManagementConfig.getRegExFirstName(), user.getFname())){
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE, ErrorConstantString.notValidErrorMessageFirstname()));
        }
    }

    private void validateLastName(UserPostRequest user, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getLname())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.LAST_NAME)));
        } else if(!Pattern.matches(assetManagementConfig.getRegExLastName(), user.getLname())){
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE, ErrorConstantString.notValidErrorMessageLastname()));
        }
    }

    private void validatePassword(UserPostRequest user, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getPassword())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.PASSWORD)));
        } else if (!Pattern.matches(assetManagementConfig.getRegExPassword(), user.getPassword())) {
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE,
                    ErrorConstantString.notValidErrorMessagePassword(AppConstants.PASSWORD)));
        }
    }

    private void validateEmail(UserPostRequest user, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getEmail())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.EMAIL)));
        } else if (!Pattern.matches(assetManagementConfig.getRegExEmail() , user.getEmail())) {
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE,
                    ErrorConstantString.notValidErrorMessage(AppConstants.EMAIL)));
        } else if (isEmailExists(user.getEmail())) {
            errors.add(new ErrorDto(ErrorConstants.ALREADY_PRESENT_ERROR_CODE,
                    ErrorConstantString.alreadyPresentErrorMessage(AppConstants.EMAIL, user.getEmail())));
        }
    }

    private boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validatePhone(UserPostRequest user, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(user.getPhone())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.PHONE)));
        } else if (!Pattern.matches(assetManagementConfig.getRegExPhone() , user.getPhone())) {
            errors.add(new ErrorDto(ErrorConstants.NOT_VALID_ERROR_CODE,
                    ErrorConstantString.notValidErrorMessage(AppConstants.PHONE)));
        }
    }

}
