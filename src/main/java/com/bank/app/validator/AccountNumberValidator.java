package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public class AccountNumberValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String accountNumber) {
        if (accountNumber == null || accountNumber.length() != 10) {
            return ValidationResult.error("Account number error: must be exactly 10 digits");
        }
        if (!accountNumber.matches("\\d+")) {
            return ValidationResult.error("Account number error: must contain only numbers");
        }
        return ValidationResult.success();
    }
}