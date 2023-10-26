package persistence.sql.dbms;

import persistence.sql.dbms.mapper.name.NameMapper;
import persistence.sql.dbms.mapper.name.UpperSnakeCaseNameMapper;
import persistence.sql.dbms.mapper.type.H2TypeMapper;
import persistence.sql.dbms.mapper.type.TypeMapper;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityTable;

public enum Dialect {
    H2(new UpperSnakeCaseNameMapper(), new H2TypeMapper());

    Dialect(NameMapper nameMapper, TypeMapper typeMapper) {
        this.nameMapper = nameMapper;
        this.typeMapper = typeMapper;
    }

    private final NameMapper nameMapper;
    private final TypeMapper typeMapper;

    public String defineTableName(EntityTable<?> entityTable) {
        return nameMapper.create(entityTable.getName());
    }

    public String defineColumnName(EntityColumn<?, ?> entityColumn) {
        return nameMapper.create(entityColumn.getDbColumnName());
    }

    public String defineColumnType(EntityColumn<?, ?> entityColumn) {
        return typeMapper.create(entityColumn.getType());
    }
}
