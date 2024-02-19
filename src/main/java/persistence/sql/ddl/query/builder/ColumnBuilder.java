package persistence.sql.ddl.query.builder;

import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.model.ConstantType;
import persistence.sql.ddl.query.model.DomainType;

public class ColumnBuilder {

    private static final String BLANK = " ";

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
                getPkConstantType()
        ).trim();
    }

    private String getColumnName() {
        return domainType.getName();
    }

    private String getColumnType() {
        return typeMapper.toSqlType(domainType.getClassType());
    }

    private String getPkConstantType() {
        return domainType.getField().isAnnotationPresent(Id.class) ?
                ConstantType.PK.getType() :
                BLANK;
    }

}
