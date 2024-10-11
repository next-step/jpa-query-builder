package persistence.sql.dialect;

public interface Dialect {
    String getIdentifierQuoted(String identifier);

    String getNullString(Boolean isNull);
}
