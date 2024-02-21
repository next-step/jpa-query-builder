package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.database.ConstraintsMapper;
import persistence.sql.ddl.dialect.database.TypeMapper;

public class H2Dialect implements Dialect {
    private final TypeMapper typeMapper;
    private final ConstraintsMapper constantTypeMapper;

    public H2Dialect() {
        this.typeMapper = H2TypeMapper.newInstance();
        this.constantTypeMapper = H2ConstraintsMapper.newInstance();
    }

    @Override
    public TypeMapper getTypeMapper() {
        return typeMapper;
    }

    @Override
    public ConstraintsMapper getConstantTypeMapper() {
        return constantTypeMapper;
    }
}
