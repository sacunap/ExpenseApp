package com.soyhenry.ExpensesApp.service;

import com.soyhenry.ExpensesApp.exception.DataValidationException;
import com.soyhenry.ExpensesApp.exception.ErrorCode;
import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import com.soyhenry.ExpensesApp.repository.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ExpenseCategoryServiceImplementation implements ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    public ExpenseCategoryServiceImplementation(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public ExpenseCategory getExpenseCategoryById(Long id) {
        ExpenseCategory existingExpenseCategory = expenseCategoryRepository.findById(id);

        if (existingExpenseCategory == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_NOT_FOUND);
        }

        return existingExpenseCategory;
    }

    @Override
    public ExpenseCategory createExpenseCategory(ExpenseCategory expenseCategory) {

        final List<ExpenseCategory> categoryList = getAllExpenseCategories();

        for (ExpenseCategory category : categoryList) {
            if (Objects.equals(category.getName(), expenseCategory.getName())) {
                throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_ALREADY_EXISTS);
            }
        }

        if (expenseCategory.getName() == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_KEY);
        }

        return expenseCategoryRepository.save(expenseCategory);
    }

    @Override
    public ExpenseCategory updateExpenseCategory(Long id, ExpenseCategory updatedExpenseCategory) {
        ExpenseCategory existingExpenseCategory = expenseCategoryRepository.findById(id);

        final List<ExpenseCategory> categoryList = getAllExpenseCategories();

        for (ExpenseCategory category : categoryList) {
            if (Objects.equals(category.getName(), updatedExpenseCategory.getName())) {
                throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_ALREADY_EXISTS);
            }
        }

        if(existingExpenseCategory == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_NOT_FOUND);
        }

        if (updatedExpenseCategory.getName() == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_KEY);
        }

        existingExpenseCategory.setName(updatedExpenseCategory.getName());
        return expenseCategoryRepository.update(id, existingExpenseCategory);
    }

    @Override
    public void deleteExpenseCategory(Long id) {
        ExpenseCategory existingExpenseCategory = expenseCategoryRepository.findById(id);

        if (existingExpenseCategory == null) {
            throw new DataValidationException(ErrorCode.EXPENSE_CATEGORY_NOT_FOUND);
        }

        expenseCategoryRepository.deleteById(id);
    }
}
