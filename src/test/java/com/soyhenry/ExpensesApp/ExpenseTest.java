package com.soyhenry.ExpensesApp;

import com.soyhenry.ExpensesApp.controller.ExpenseController;
import com.soyhenry.ExpensesApp.model.Expense;
import com.soyhenry.ExpensesApp.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @InjectMocks
    private ExpenseController expenseController;

    @Mock
    private ExpenseService expenseService;

    private MockMvc mockMvc;

    @Test
    void testGetAllExpenses() throws Exception {

        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setDescription("Lunch expense");
        expense1.setAmount(100.0);

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setDescription("Gift expense");
        expense2.setAmount(150.0);

        List<Expense> mockExpenses = Arrays.asList(expense1, expense2);
        when(expenseService.getAllExpenses()).thenReturn(mockExpenses);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(get("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(mockExpenses.size())));
    }

    @Test
    void testGetExpenseById() throws Exception {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setDescription("HBO Subscription");
        expense.setAmount(100.0);

        when(expenseService.getExpenseById(anyLong())).thenReturn(expense);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(get("/api/expenses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expense.getId().intValue()));
    }

    @Test
    void testCreateExpense() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Lunch expense\",\"amount\":100.0,\"date\":\"2023-12-16\"," +
                                "\"expenseCategory\":{\"name\":\"Food\"}}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateExpense() throws Exception {

        Expense existingExpense = new Expense();
        existingExpense.setId(1L);
        existingExpense.setDescription("Existing Expense");
        existingExpense.setAmount(75.0);

        Expense updatedExpense = new Expense();
        updatedExpense.setId(1L);
        updatedExpense.setDescription("Updated Expense");
        updatedExpense.setAmount(100.0);

        when(expenseService.updateExpense(eq(1L), any(Expense.class))).thenReturn(updatedExpense);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(put("/api/expenses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated Expense\",\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(updatedExpense.getAmount()))
                .andExpect(jsonPath("$.description").value(updatedExpense.getDescription()))
                .andExpect(jsonPath("$.id").value(updatedExpense.getId().intValue()));
    }

    @Test
    void testDeleteExpense() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(delete("/api/expenses/{id}", 1L))
                .andExpect(status().isOk());
    }
}
