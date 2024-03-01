package domain.mapper;

import domain.vo.ColumnName;
import jakarta.persistence.Transient;
import jdbc.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RowMapperImpl<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public RowMapperImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        T object;
        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException("클래스 인스턴스 생성 실패했습니다.", e);
        }

        LinkedList<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toCollection(LinkedList::new));

        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                ColumnName columnName = new ColumnName(fields, field);
                field.set(object, resultSet.getObject(columnName.getName()));
            } catch (IllegalAccessException | SQLException e) {
                throw new IllegalStateException("지원하는 타입이 아닙니다.");
            }
        });
        return object;
    }
}
