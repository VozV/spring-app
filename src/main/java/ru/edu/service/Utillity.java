package ru.edu.service;

import org.dom4j.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;

public class Utillity {

    public static Integer parseId (Object id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Ключ объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf(String.valueOf(id));
        } catch (NumberFormatException e) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к " +
                    "нужному типу: %s", e));
        }
        return parsedId;
    }
}
