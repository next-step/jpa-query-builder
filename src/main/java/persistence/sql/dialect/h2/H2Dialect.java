package persistence.sql.dialect.h2;

import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.database.TypeMapper;

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
