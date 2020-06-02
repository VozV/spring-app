package ru.edu.exсeptions.entity;

import org.springframework.util.Assert;
import ru.edu.exсeptions.BaseExceptions;

public class EntityNotFoundException extends BaseExceptions {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Ключ не может быть null");
        Assert.hasText(id.toString(), "Ключ не может быть пустым");
        return String.format("%s с ключем %s не найден", type, id);
    }
}
