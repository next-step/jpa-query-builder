package persistence.sql.dml.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.jetbrains.annotations.Nullable;
import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class SimpleMetadataLoader<T> implements MetadataLoader<T> {
    private static final Logger logger = Logger.getLogger(SimpleMetadataLoader.class.getName());

    private final Class<T> clazz;

    public SimpleMetadataLoader(Class<T> entityType) {
        this.clazz = entityType;
    }

    @Override
    public Field getField(int index) {
        return clazz.getDeclaredFields()[index];
    }

    @Override
    public String getTableName() {
        String tableName = clazz.getSimpleName();

        if (clazz.isAnnotationPresent(Table.class)) {
            tableName = clazz.getAnnotation(Table.class).name();
        }
        return tableName;
    }

    @Override
    public String getEntityName() {
        return clazz.getSimpleName();
    }

    @Override
    public String getColumnName(int index) {
        Field declaredField = getField(index);

        return getValueByField(declaredField);
    }

    @Override
    public String getColumnName(Field field) {
        Field foundField = Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransient)
                .filter(field::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        return getValueByField(foundField);
    }

    @Nullable
    private static String getValueByField(Field declaredField) {
        if (declaredField.isAnnotationPresent(Transient.class)) {
            return null;
        }

        Column anno = declaredField.getAnnotation(Column.class);
        if (anno != null && !anno.name().isBlank()) {
            return anno.name();
        }

        return declaredField.getName();
    }

    @Override
    public String getFieldName(int index) {
        Field declaredField = getField(index);

        if (declaredField.isAnnotationPresent(Transient.class)) {
            return null;
        }

        return declaredField.getName();
    }

    @Override
    public List<String> getFieldNameAll() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransient)
                .map(Field::getName).toList();
    }

    @Override
    public List<String> getColumnNameAll(NameConverter nameConverter) {
        int columnCount = getColumnCount();

        return IntStream.range(0, columnCount)
                .mapToObj(this::getColumnName)
                .toList();
    }

    @Override
    public Field getPrimaryKeyField() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransient)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Primary key not found"));
    }

    @Override
    public int getColumnCount() {
        return (int) Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransient).count();
    }

    @Override
    public List<Field> getFieldAllByPredicate(Predicate<Field> fieldPredicate) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransient)
                .filter(fieldPredicate)
                .toList();
    }

    @Override
    public Constructor<T> getNoArgConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            logger.severe("No-arg constructor not found");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<T> getEntityType() {
        return clazz;
    }

    private boolean isNotTransient(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}