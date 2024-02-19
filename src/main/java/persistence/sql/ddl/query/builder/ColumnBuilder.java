package persistence.sql.ddl.query.builder;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.model.ConstantType;
import persistence.sql.ddl.query.model.DomainType;

public class ColumnBuilder {

    private static final String BLANK = " ";
    private static final String EMPTY = "";

    private final DomainType domainType;
    private final TypeMapper typeMapper;

    public ColumnBuilder(final DomainType domainType,
                         final TypeMapper typeMapper) {
        this.domainType = domainType;
        this.typeMapper = typeMapper;
    }

    public String build() {
        return String.join(BLANK,
                getColumnName(),
                getColumnType(),
                getPkConstantType(),
                getConstantType()
        ).trim();
    }

    private String getColumnName() {
        Column columnAnnotation = domainType.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }
        return domainType.getName();
    }

    private String getColumnType() {
        return typeMapper.toSqlType(domainType.getClassType());
    }

    private String getPkConstantType() {
        if(!domainType.isAnnotation(Id.class)) {
            return EMPTY;
        }

        if(!domainType.isAnnotation(GeneratedValue.class)) {
            return ConstantType.PRIMARY_KEY.getType();
        }

        if(domainType.getAnnotation(GeneratedValue.class).strategy() != GenerationType.IDENTITY) {
            return ConstantType.PRIMARY_KEY.getType();
        }

        return ConstantType.PK.getType();
    }

    private String getConstantType() {
        if(domainType.isAnnotation(Column.class)) {
            Column column = domainType.getAnnotation(Column.class);
            return column.nullable() ? EMPTY : ConstantType.NOT_NULL.getType();
        }

        return EMPTY;
    }

}
