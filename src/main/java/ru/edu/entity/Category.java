package ru.edu.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Category {

    public static String TYPE_NAME = "Категория товара";

    @Id
    @Column
    private Integer id;
    @Column
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
