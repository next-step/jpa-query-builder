package persistence.sql.ddl.query.builder;

import jakarta.persistence.Column;
import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.database.TypeMapper;
import persistence.sql.entity.model.Constraints;
import persistence.sql.entity.model.DomainType;

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
        return domainType.getName();
    }

    private String getColumnType() {
        return typeMapper.toSqlType(domainType.getClassType());
    }

    private String getPkConstantType() {
        if (domainType.isNotExistsId()) {
            return EMPTY;
        }

        if (domainType.isNotExistGenerateValue()) {
            return constantTypeMapper.getConstantType(Constraints.PRIMARY_KEY);
        }

        if (domainType.isExistsIdentity()) {
            return constantTypeMapper.getConstantType(Constraints.PRIMARY_KEY);
        }

        return constantTypeMapper.getConstantType(Constraints.PK);
    }

    private String getConstantType() {
        if (domainType.isColumnAnnotation()) {
            Column column = domainType.getAnnotation(Column.class);
            return column.nullable() ? EMPTY : constantTypeMapper.getConstantType(Constraints.NOT_NULL);
        }

        return EMPTY;
    }

}
