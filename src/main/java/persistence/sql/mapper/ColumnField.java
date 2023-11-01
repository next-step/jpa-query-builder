package persistence.sql.mapper;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import persistence.sql.ddl.type.H2DataTypeMapper;
import utils.EntityAnnotationUtils;
import utils.TypeUtils;

import java.lang.reflect.Field;
import java.util.Optional;

import static utils.EntityAnnotationUtils.getColumnAnnotation;

public class ColumnField implements ColumnType {

    private final String name;
    private final boolean nullable;

    private final boolean isTransient;
    private final int length;

    private final String dataType;
    private final Class<?> type;
    private final ColumnValue columnValue;

    public ColumnField(final Object entity, final Field field) {
        this.type = field.getType();
        this.name = this.parseName(field);
        this.nullable = this.parsNullable(field);
        this.isTransient = this.parseTransient(field);
        this.length = this.parseLength(field);
        this.dataType = this.parseDataType(field);
        this.columnValue = this.parseValue(entity, field);
    }

    private ColumnValue parseValue(final Object entity, final Field field) {
        return new ColumnValue(entity, field.getName());
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
        return EntityAnnotationUtils.parseColumnName(field);
    }

    private int parseLength(final Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::length)
                .orElse(255);
    }

    private String parseDataType(final Field field) {
        //H2DataTypeMapper는 누가 가지고 있어야 하는지,,?
        return H2DataTypeMapper.getInstance().getDataType(field.getType()).getName();
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        final Object value = columnValue.getValue();
        if (TypeUtils.isString(value)) {
            return "'" + value + "'";
        }

        return Optional.ofNullable(value)
                .map(Object::toString)
                .orElse("");
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
        if ("VARCHAR".equals(this.dataType)) {
            return "(" + this.length + ")";
        }
        return "";
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}