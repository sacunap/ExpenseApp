package com.soyhenry.ExpensesApp.service;

import com.soyhenry.ExpensesApp.model.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategory> getAllExpenseCategories();

    ExpenseCategory getExpenseCategoryById(Long id);

    ExpenseCategory createExpenseCategory(ExpenseCategory expenseCategory);

    ExpenseCategory updateExpenseCategory(Long id, ExpenseCategory updatedExpenseCategory);

    void deleteExpenseCategory(Long id);
}
