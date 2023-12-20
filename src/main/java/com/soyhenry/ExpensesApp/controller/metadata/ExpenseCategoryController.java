package com.soyhenry.ExpensesApp.controller.metadata;

import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import com.soyhenry.ExpensesApp.service.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metadata/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    // Get all expense categories
    @GetMapping
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryService.getAllExpenseCategories();
    }

    // Get a single expense category by ID
    @GetMapping("/{id}")
    public ExpenseCategory getExpenseCategoryById(@PathVariable Long id) {
        return expenseCategoryService.getExpenseCategoryById(id);
    }

    // Create a new expense category
    @PostMapping
    public ExpenseCategory createExpenseCategory(@RequestBody ExpenseCategory expenseCategory) {
        return expenseCategoryService.createExpenseCategory(expenseCategory);
    }

    // Update an existing expense category
    @PutMapping("/{id}")
    public ExpenseCategory updateExpenseCategory(@PathVariable Long id, @RequestBody ExpenseCategory updatedExpenseCategory) {
        return expenseCategoryService.updateExpenseCategory(id, updatedExpenseCategory);
    }

    // Delete an expense category by ID
    @DeleteMapping("/{id}")
    public void deleteExpenseCategory(@PathVariable Long id) {
        expenseCategoryService.deleteExpenseCategory(id);
    }
}

