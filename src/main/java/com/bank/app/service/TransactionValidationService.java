package com.bank.app.service;

import com.bank.app.model.Transaction;
import com.bank.app.model.ValidationResult;
import com.bank.app.validator.Validator;

public class TransactionValidationService {
    private final Validator<String> bankCodeValidator;
    private final Validator<String> branchCodeValidator;
    private final Validator<String> accountNumberValidator;
    private final Validator<String> personalKeyValidator;
    private final Validator<String> orderValueValidator;

    public TransactionValidationService(
            Validator<String> bankCodeValidator,
            Validator<String> branchCodeValidator,
            Validator<String> accountNumberValidator,
            Validator<String> personalKeyValidator,
            Validator<String> orderValueValidator) {
        this.bankCodeValidator = bankCodeValidator;
        this.branchCodeValidator = branchCodeValidator;
        this.accountNumberValidator = accountNumberValidator;
        this.personalKeyValidator = personalKeyValidator;
        this.orderValueValidator = orderValueValidator;
    }

    public void validateTransaction(Transaction transaction) {
        boolean hasErrors = false;
        StringBuilder errors = new StringBuilder();

        ValidationResult bankResult = bankCodeValidator.validate(transaction.getBankCode());
        if (!bankResult.isValid()) {
            errors.append("- ").append(bankResult.getErrorMessage()).append("\n");
            hasErrors = true;
        }

        ValidationResult branchResult = branchCodeValidator.validate(transaction.getBranchCode());
        if (!branchResult.isValid()) {
            errors.append("- ").append(branchResult.getErrorMessage()).append("\n");
            hasErrors = true;
        }

        ValidationResult accountResult = accountNumberValidator.validate(transaction.getAccountNumber());
        if (!accountResult.isValid()) {
            errors.append("- ").append(accountResult.getErrorMessage()).append("\n");
            hasErrors = true;
        }

        ValidationResult keyResult = personalKeyValidator.validate(transaction.getPersonalKey());
        if (!keyResult.isValid()) {
            errors.append("- ").append(keyResult.getErrorMessage()).append("\n");
            hasErrors = true;
        }

        ValidationResult orderResult = orderValueValidator.validate(transaction.getOrderValue());
        if (!orderResult.isValid()) {
            errors.append("- ").append(orderResult.getErrorMessage()).append("\n");
            hasErrors = true;
        }

        System.out.println("\n" + "=".repeat(60));
        if (!hasErrors) {
            System.out.println("✓ VALIDATION SUCCESSFUL");
            System.out.println("State: Ready -> Confirmation");
            System.out.println("Result: Request accepted");
        } else {
            System.out.println("✗ VALIDATION ERRORS");
            System.out.println("State: Ready -> Error");
            System.out.println("\nErrors found:");
            System.out.println(errors.toString());
        }
        System.out.println("=".repeat(60));
    }
}
