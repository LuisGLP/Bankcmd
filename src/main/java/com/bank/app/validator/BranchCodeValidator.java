package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public class BranchCodeValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String branchCode) {
        if (branchCode == null || branchCode.length() != 4) {
            return ValidationResult.error("Branch code error: must be exactly 4 digits");
        }
        if (!branchCode.matches("\\d+")) {
            return ValidationResult.error("Branch code error: must contain only numbers");
        }
        return ValidationResult.success();
    }
}