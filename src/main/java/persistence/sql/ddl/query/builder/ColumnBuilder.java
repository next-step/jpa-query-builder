package persistence.sql.ddl.query.builder;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.database.ConstraintsMapper;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.model.Constraints;
import persistence.sql.ddl.query.model.DomainType;

public class ColumnBuilder {

    private static final String BLANK = " ";
    private static final String EMPTY = "";

    private final DomainType domainType;
    private final TypeMapper typeMapper;
    private final ConstraintsMapper constantTypeMapper;

    public ColumnBuilder(final DomainType domainType,
                         final TypeMapper typeMapper,
                         final ConstraintsMapper constantTypeMapper) {
        this.domainType = domainType;
        this.typeMapper = typeMapper;
        this.constantTypeMapper = constantTypeMapper;
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
        if (!domainType.isAnnotation(Id.class)) {
            return EMPTY;
        }

        if (!domainType.isAnnotation(GeneratedValue.class)) {
            return constantTypeMapper.getConstantType(Constraints.PRIMARY_KEY);
        }

        if (domainType.getAnnotation(GeneratedValue.class).strategy() != GenerationType.IDENTITY) {
            return constantTypeMapper.getConstantType(Constraints.PRIMARY_KEY);
        }

        return constantTypeMapper.getConstantType(Constraints.PK);
    }

    private String getConstantType() {
        if (domainType.isAnnotation(Column.class)) {
            Column column = domainType.getAnnotation(Column.class);
            return column.nullable() ? EMPTY : constantTypeMapper.getConstantType(Constraints.NOT_NULL);
        }

        return EMPTY;
    }

}
