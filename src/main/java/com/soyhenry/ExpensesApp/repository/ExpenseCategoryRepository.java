package com.soyhenry.ExpensesApp.repository;

import com.soyhenry.ExpensesApp.model.ExpenseCategory;

import java.util.List;
import java.util.Optional;

public interface ExpenseCategoryRepository {

    List<ExpenseCategory> findAll();

    ExpenseCategory findById(Long id);

    Long findByName(String name);

    ExpenseCategory save(ExpenseCategory expenseCategory);

    ExpenseCategory update(Long id, ExpenseCategory expenseCategory);

    void deleteById(Long id);
}
