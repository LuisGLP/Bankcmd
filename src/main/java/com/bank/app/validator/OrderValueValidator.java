package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public class OrderValueValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String orderValue) {
        if (orderValue == null || orderValue.isEmpty()) {
            return ValidationResult.error("Order error: value cannot be empty");
        }

        int value;
        try {
            value = Integer.parseInt(orderValue);
        } catch (NumberFormatException e) {
            return ValidationResult.error("Order error: must be a valid number");
        }

        if (value != 1 && value != 2) {
            return ValidationResult.error("Order error: value must be 1 or 2");
        }

        return ValidationResult.success();
    }
}