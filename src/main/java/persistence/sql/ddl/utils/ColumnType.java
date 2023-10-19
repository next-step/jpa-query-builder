package persistence.sql.ddl.utils;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.type.DataType;
import persistence.sql.ddl.type.DataTypeMapper;
import persistence.sql.ddl.type.H2DataTypeMapper;

import java.lang.reflect.Field;
import java.util.Optional;

public class ColumnType {
    private String name;
    private boolean primaryKey;
    private DataType dataType;
    private Integer length;
    private Class<?> type;
    private boolean isNullable;
    private GenerationType generationType;

    private final DataTypeMapper dataTypeMapper;

    public ColumnType(final Field field) {
        this.dataTypeMapper = H2DataTypeMapper.getInstance();
        setName(field);
        setPrimaryKey(field);
        setDataType(field);
        setLength();
        setIsNullable(field);
        setGenerationType(field);
    }

    private Column getColumnAnnotation(final Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    private void setName(final Field field) {
        this.name = Optional.ofNullable(getColumnAnnotation(field))
                .filter(column -> !column.name().isEmpty())
                .map(Column::name)
                .orElse(field.getName());
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    private void setPrimaryKey(final Field field) {
        this.primaryKey = field.isAnnotationPresent(Id.class);
    }

    public DataType getDataType() {
        return dataType;
    }

    private void setDataType(final Field field) {
        this.dataType = this.dataTypeMapper.getDataType(field.getType());
    }

    public Integer getLength() {
        return length;
    }

    private void setLength() {
        this.length = this.dataType.getDefaultLength();
    }

    public String getTypeName() {
        return this.dataType.getName();
    }

    private void setType(final Field field) {
        this.type = field.getType();
    }

    private void setIsNullable(final Field field) {
        this.isNullable = Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::nullable)
                .orElse(true);
    }

    public boolean isNullable() {
        return isNullable;
    }

    private void setGenerationType(final Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            this.generationType = field.getAnnotation(GeneratedValue.class).strategy();
            return;
        }
        this.generationType = null;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }
}