package com.soyhenry.ExpensesApp.repository;

import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExpenseCategoryRepositoryImplementation implements ExpenseCategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ExpenseCategoryRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ExpenseCategory> findAll() {
        String sql = "SELECT * FROM ExpenseCategory";
        return jdbcTemplate.query(sql, this::mapExpenseCategory);
    }

    @Override
    public ExpenseCategory findById(Long id) {
        String sql = "SELECT * FROM ExpenseCategory WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapExpenseCategory, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Long findByName(String name) {
        String sql = "SELECT id FROM ExpenseCategory WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, Long.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        String sql = "INSERT INTO ExpenseCategory (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, expenseCategory.getName());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
        expenseCategory.setId(generatedId);

        return expenseCategory;
    }

    @Override
    public ExpenseCategory update(Long id, ExpenseCategory updatedExpenseCategory) {
        String sql = "UPDATE ExpenseCategory SET name = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, updatedExpenseCategory.getName(), id);

        if (rowsAffected > 0) {
            return findById(id);
        } else {
            return null;
        }
    }


    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM ExpenseCategory WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ExpenseCategory mapExpenseCategory(ResultSet rs, int rowNum) throws SQLException {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setId(rs.getLong("id"));
        expenseCategory.setName(rs.getString("name"));
        return expenseCategory;
    }
}
