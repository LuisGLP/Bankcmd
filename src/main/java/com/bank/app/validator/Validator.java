package com.bank.app.validator;

import com.bank.app.model.ValidationResult;

public interface Validator<T> {
    ValidationResult validate(T value);
}