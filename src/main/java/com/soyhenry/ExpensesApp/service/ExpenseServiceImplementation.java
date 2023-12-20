package com.soyhenry.ExpensesApp.service;

import com.soyhenry.ExpensesApp.exception.DataValidationException;
import com.soyhenry.ExpensesApp.exception.ErrorCode;
import com.soyhenry.ExpensesApp.model.Expense;
import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import com.soyhenry.ExpensesApp.repository.ExpenseCategoryRepository;
import com.soyhenry.ExpensesApp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImplementation implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    public ExpenseServiceImplementation(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense existingExpense = expenseRepository.findById(id);

        if (existingExpense == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_NOT_FOUND);
        }

        return existingExpense;
    }

    @Override
    public Expense addExpense(Expense expense) {
        Long expenseCategoryID = expenseCategoryRepository.findByName(expense.getExpenseCategory().getName());

        this.validateFields(expense);

        if (expenseCategoryID == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_NOT_FOUND);
        }

        return expenseRepository.save(expense, expenseCategoryID);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(id);
        Long expenseCategoryID = expenseCategoryRepository.findByName(updatedExpense.getExpenseCategory().getName());

        if(existingExpense == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_NOT_FOUND);
        }

        this.validateFields(updatedExpense);

        if (expenseCategoryID == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_NOT_FOUND);
        }

        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDescription(updatedExpense.getDescription());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setExpenseCategory(updatedExpense.getExpenseCategory());
        return expenseRepository.update(id, existingExpense, expenseCategoryID);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense existingExpense = expenseRepository.findById(id);

        if (existingExpense == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_NOT_FOUND);
        }

        expenseRepository.deleteById(id);
    }

    private void validateFields(Expense expense) {

        if (expense.getAmount() == 0.0 || expense.getDescription() == null || expense.getDate() == null || expense.getExpenseCategory() == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_KEY);
        }

        if (expense.getExpenseCategory().getName() == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_KEY);
        }
    }
}
