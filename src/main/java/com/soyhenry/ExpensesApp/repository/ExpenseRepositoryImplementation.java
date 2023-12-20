package com.soyhenry.ExpensesApp.repository;

import com.soyhenry.ExpensesApp.model.Expense;
import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ExpenseRepositoryImplementation implements ExpenseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ExpenseRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> findAll() {
        String sql = "SELECT * FROM Expense";
        return jdbcTemplate.query(sql, this::mapExpense);
    }

    @Override
    public Expense findById(Long id) {
        String sql = "SELECT * FROM Expense WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapExpense, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Expense save(Expense expense, Long categoryID) {
        String sql = "INSERT INTO Expense (amount, description, date, category_id, category_name) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, expense.getAmount(), expense.getDescription(), expense.getDate(), categoryID, expense.getExpenseCategory().getName());
        return expense;
    }

    @Override
    public Expense update(Long id, Expense updatedExpense, Long categoryID) {
        String sql = "UPDATE Expense SET amount = ?, description = ?, date = ?, category_id = ?, category_name = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, updatedExpense.getAmount(), updatedExpense.getDescription(), updatedExpense.getDate(), categoryID, updatedExpense.getExpenseCategory().getName(), id);

        if (rowsAffected > 0) {
            return findById(id);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Expense WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Expense mapExpense(ResultSet rs, int rowNum) throws SQLException {
        Expense expense = new Expense();
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expense.setId(rs.getLong("id"));
        expense.setAmount(rs.getDouble("amount"));
        expense.setDescription(rs.getString("description"));
        expense.setDate(LocalDate.parse(rs.getString("date")));
        expense.setExpenseCategory(expenseCategory);
        expenseCategory.setId(rs.getLong("category_id"));
        expenseCategory.setName(rs.getString("category_name"));
        return expense;
    }
}
