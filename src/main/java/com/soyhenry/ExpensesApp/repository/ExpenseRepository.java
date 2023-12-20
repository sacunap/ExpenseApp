package com.soyhenry.ExpensesApp.repository;

import com.soyhenry.ExpensesApp.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    List<Expense> findAll();
    Expense findById(Long id);
    Expense save(Expense expense, Long categoryID);

    Expense update(Long id, Expense expense, Long categoryID);
    void deleteById(Long id);
}