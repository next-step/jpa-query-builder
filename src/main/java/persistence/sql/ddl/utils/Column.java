package persistence.sql.ddl.utils;

import jakarta.persistence.Id;
import persistence.sql.ddl.type.DataType;
import persistence.sql.ddl.type.DataTypeMapper;
import persistence.sql.ddl.type.H2DataTypeMapper;

import java.lang.reflect.Field;
import java.util.Optional;

public class Column {
    private String name;
    private boolean primaryKey;
    private DataType dataType;
    private Integer length;

    private final DataTypeMapper dataTypeMapper;

    public Column(final Field field) {
        this.dataTypeMapper = H2DataTypeMapper.getInstance();
        setName(field);
        setPrimaryKey(field);
        setDataType(field);
        setLength();
    }

    public String getName() {
        return name;
    }

    public void setName(final Field field) {
        this.name = field.getName();
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(final Field field) {
        this.primaryKey = field.isAnnotationPresent(Id.class);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(final Field field) {
        this.dataType = this.dataTypeMapper.getDataType(field.getType());
    }

    public Integer getLength() {
        return length;
    }

    public void setLength() {
        this.length = this.dataType.getDefaultLength();
    }

    public String getTypeName() {
        return this.dataType.getName();
    }
}
