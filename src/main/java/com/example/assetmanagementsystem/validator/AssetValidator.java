package com.example.assetmanagementsystem.validator;

import com.example.assetmanagementsystem.constants.AppConstants;
import com.example.assetmanagementsystem.constants.ErrorConstantString;
import com.example.assetmanagementsystem.constants.ErrorConstants;
import com.example.assetmanagementsystem.dtos.AssetPostRequest;
import com.example.assetmanagementsystem.dtos.UserPostRequest;
import com.example.assetmanagementsystem.exception.ErrorDto;
import com.example.assetmanagementsystem.exception.ValidationException;
import com.example.assetmanagementsystem.repositories.AssetRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetValidator {
    private final AssetRepository assetRepository;
    public void validateAssetRequest(AssetPostRequest asset, String action){
        List<ErrorDto> errors = assetBasicValidation(asset, action);
//        System.out.println(errors);
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

    private List<ErrorDto> assetBasicValidation(AssetPostRequest user, String action) {
        List<ErrorDto> errors = new ArrayList<>();
        validateName(user, action, errors);
        validateDescription(user, errors);
        return errors;
    }

    private void validateName(AssetPostRequest asset, String action, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(asset.getName())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.NAME)));
        } else if (AppConstants.CREATE.equalsIgnoreCase(action) && isNameExists(asset.getName())) {
            errors.add(new ErrorDto(ErrorConstants.ALREADY_PRESENT_ERROR_CODE,
                    ErrorConstantString.alreadyPresentErrorMessage(AppConstants.NAME, asset.getName())));
        }
    }

    private boolean isNameExists(String username) {
        return assetRepository.existsByName(username);
    }

    private void validateDescription(AssetPostRequest asset, List<ErrorDto> errors) {
        if (StringUtils.isEmpty(asset.getDescription())) {
            errors.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE,
                    ErrorConstantString.mandatoryErrorMessage(AppConstants.DESCRIPTION)));
        }
    }
}
