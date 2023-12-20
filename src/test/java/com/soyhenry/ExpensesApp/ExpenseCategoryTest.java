package com.soyhenry.ExpensesApp;

import com.soyhenry.ExpensesApp.controller.metadata.ExpenseCategoryController;
import com.soyhenry.ExpensesApp.model.ExpenseCategory;
import com.soyhenry.ExpensesApp.service.ExpenseCategoryService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryTest {

    @InjectMocks
    private ExpenseCategoryController expenseCategoryController;

    @Mock
    private ExpenseCategoryService expenseCategoryService;

    private MockMvc mockMvc;

    @Test
    void testGetAllExpenseCategories() throws Exception {

        ExpenseCategory category1 = new ExpenseCategory();
        category1.setId(1L);
        category1.setName("Food");

        ExpenseCategory category2 = new ExpenseCategory();
        category2.setId(2L);
        category2.setName("Travel");

        List<ExpenseCategory> mockCategories = Arrays.asList(category1, category2);

        when(expenseCategoryService.getAllExpenseCategories()).thenReturn(mockCategories);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryController).build();

        mockMvc.perform(get("/api/metadata/expense-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(mockCategories.size())));
    }

    @Test
    void testGetExpenseCategoryById() throws Exception {

        ExpenseCategory newCategory = new ExpenseCategory();
        newCategory.setId(1L);
        newCategory.setName("Food");

        when(expenseCategoryService.getExpenseCategoryById(anyLong())).thenReturn(newCategory);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryController).build();

        mockMvc.perform(get("/api/metadata/expense-categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newCategory.getId().intValue()));
    }

    @Test
    void testCreateExpenseCategory() throws Exception {

        ExpenseCategory newCategory = new ExpenseCategory();

        when(expenseCategoryService.createExpenseCategory(any(ExpenseCategory.class))).thenReturn(newCategory);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryController).build();

        mockMvc.perform(post("/api/metadata/expense-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Other\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newCategory.getName()));
    }

    @Test
    void testUpdateExpenseCategory() throws Exception {

        ExpenseCategory updatedCategory = new ExpenseCategory();
        updatedCategory.setId(1L);
        updatedCategory.setName("Rent");

        when(expenseCategoryService.updateExpenseCategory(anyLong(), any(ExpenseCategory.class))).thenReturn(updatedCategory);

        mockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryController).build();

        mockMvc.perform(put("/api/metadata/expense-categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Subscriptions\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCategory.getId().intValue()));
    }

    @Test
    void testDeleteExpenseCategory() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryController).build();

        mockMvc.perform(delete("/api/metadata/expense-categories/{id}", 1L))
                .andExpect(status().isOk());

        verify(expenseCategoryService, times(1)).deleteExpenseCategory(anyLong());
    }
}
