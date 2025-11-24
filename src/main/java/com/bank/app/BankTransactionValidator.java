package com.bank.app;

import com.bank.app.validator.*;
import com.bank.app.model.Transaction;
import com.bank.app.service.TransactionValidationService;

public class BankTransactionValidator {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Create validators (Dependency Injection)
        TransactionValidationService validationService = new TransactionValidationService(
                new BankCodeValidator(),
                new BranchCodeValidator(),
                new AccountNumberValidator(),
                new PersonalKeyValidator(),
                new OrderValueValidator()
        );

        System.out.println("BANK TRANSACTION VALIDATION SYSTEM");

        while (true) {
            System.out.println("\n--- Enter transaction data ---");

            System.out.print("Bank Code (3 digits): ");
            String bankCode = scanner.nextLine();

            System.out.print("Branch Code (4 digits): ");
            String branchCode = scanner.nextLine();

            System.out.print("Account Number (10 digits): ");
            String accountNumber = scanner.nextLine();

            System.out.print("Personal Key (6 alphanumeric characters): ");
            String personalKey = scanner.nextLine();

            System.out.print("Order Value (1 or 2): ");
            String orderValue = scanner.nextLine();

            Transaction transaction = new Transaction(
                    bankCode, branchCode, accountNumber, personalKey, orderValue
            );

            validationService.validateTransaction(transaction);

            System.out.print("\nDo you want to validate another transaction? (y/n): ");
            String continue_choice = scanner.nextLine();
            if (!continue_choice.equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println("\nThank you for using the validation system!");
        scanner.close();
    }
}
