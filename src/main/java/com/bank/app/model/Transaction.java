package com.bank.app.model;

public class Transaction {
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String personalKey;
    private String orderValue;

    public Transaction(String bankCode, String branchCode, String accountNumber,
                       String personalKey, String orderValue) {
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
        this.personalKey = personalKey;
        this.orderValue = orderValue;
    }

    public String getBankCode() { return bankCode; }
    public String getBranchCode() { return branchCode; }
    public String getAccountNumber() { return accountNumber; }
    public String getPersonalKey() { return personalKey; }
    public String getOrderValue() { return orderValue; }
}
