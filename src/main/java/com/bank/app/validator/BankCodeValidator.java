package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public class BankCodeValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String bankCode) {
        if (bankCode == null || bankCode.length() != 3) {
            return ValidationResult.error("Bank code error: must be exactly 3 digits");
        }
        if (!bankCode.matches("\\d+")) {
            return ValidationResult.error("Bank code error: must contain only numbers");
        }
        return ValidationResult.success();
    }
}
