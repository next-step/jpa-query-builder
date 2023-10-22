package persistence.sql.dialect;

public interface Dialect {

    TypeDialect getTypeDialect();

    String getGenerationTypeIdentity();
}
