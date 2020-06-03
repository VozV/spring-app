package ru.edu.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    public static String TYPE_NAME = "Товар";

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    private Category category;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
