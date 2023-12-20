package com.soyhenry.ExpensesApp.service;

import com.soyhenry.ExpensesApp.model.Expense;
import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    Expense addExpense(Expense expense);

    Expense updateExpense(Long id, Expense updatedExpense);

    void deleteExpense(Long id);
}