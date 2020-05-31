package ru.edu.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductJdbc {
    private long id;
    private long category_id;
    private String name;

    public ProductJdbc(long id, long category_id, String name) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
    }
}
