package ru.edu.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductJdbc {
    private long id;
    private long category;
    private String name;

    public ProductJdbc(long id, long category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }
}
