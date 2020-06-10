package ru.edu.service;

import java.util.List;

public interface CrudService <T> {
    List<T> findAll();

    T findById(Object id);

    T create(T object);

    T update(T object);

    void delete(Object id);
}
