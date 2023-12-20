package com.soyhenry.ExpensesApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

public class ExpenseCategory {

    private Long id;

    @JsonProperty("name")
    private String name;

    public ExpenseCategory() {
    }

    public ExpenseCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
