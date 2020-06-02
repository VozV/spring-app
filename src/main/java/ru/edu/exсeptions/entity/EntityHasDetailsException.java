package ru.edu.exсeptions.entity;

import org.springframework.util.Assert;
import ru.edu.exсeptions.BaseExceptions;

/**
 * Исключение при удалении сущности с потомками
 */
public class EntityHasDetailsException extends BaseExceptions {

    public EntityHasDetailsException(String message) {
        super(message);
    }

    public EntityHasDetailsException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Ключ не может быть null");
        Assert.hasText(id.toString(), "Ключ не может быть пустым");
        return String.format("%s ссылается на удаляемый объект c ключем $s", type, id);
    }
}
