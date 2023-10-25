package persistence.sql.ddl.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import persistence.sql.ddl.type.DataType;
import persistence.sql.ddl.type.H2DataTypeMapper;

import java.lang.reflect.Field;
import java.util.Optional;

public class ColumnField implements ColumnType {

    private final String name;
    private final boolean nullable;

    private final boolean isTransient;
    private final int length;

    private final DataType dataType;

    public ColumnField(final Field field) {
        this.name = parseName(field);
        this.nullable = parsNullable(field);
        this.isTransient = parseTransient(field);
        this.length = parseLength(field);
        this.dataType = parseDataType(field);
    }

    private boolean parsNullable(final Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::nullable)
                .orElse(true);
    }

    private boolean parseTransient(final Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String parseName(Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .filter(column -> !column.name().isEmpty())
                .map(Column::name)
                .orElse(field.getName());
    }

    private int parseLength(final Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::length)
                .orElse(255);
    }

    private DataType parseDataType(final Field field) {
        //H2DataTypeMapper는 누가 가지고 있어야 하는지,,?
        return H2DataTypeMapper.getInstance().getDataType(field.getType());
    }


    private Column getColumnAnnotation(final Field field) {
        return field.getAnnotation(Column.class);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return this.nullable;
    }

    @Override
    public boolean isTransient() {
        return this.isTransient;
    }

    @Override
    public String getLength() {
        if ("VARCHAR".equals(this.dataType.getName())) {
            return "("+this.length+")";
        }
        return "";
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

}