package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public class PersonalKeyValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String personalKey) {
        if (personalKey == null || personalKey.length() != 6) {
            return ValidationResult.error("Personal key error: must be exactly 6 characters");
        }
        if (!personalKey.matches("[A-Za-z0-9]+")) {
            return ValidationResult.error("Personal key error: must contain only letters and numbers");
        }
        return ValidationResult.success();
    }
}