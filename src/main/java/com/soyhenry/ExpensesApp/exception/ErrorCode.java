package com.soyhenry.ExpensesApp.exception;


public enum ErrorCode {
    EXPENSE_NOT_FOUND("expense-001","Expense not found"),
    EXPENSE_KEY("expense-002","Please provide the correct properties for the expense: amount, description, date and expenseCategory"),

    EXPENSE_CATEGORY_NOT_FOUND("expense-category-001","Expense category not found"),
    EXPENSE_CATEGORY_KEY("expense-category-002","Please provide the name property"),
    EXPENSE_CATEGORY_ALREADY_EXISTS("expense-category-003","Expense category already exists");


    private final String code;

    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
