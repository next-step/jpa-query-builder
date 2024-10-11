package persistence.sql.dialect;

import java.util.List;

public interface Dialect {
    String getIdentifierQuoted(String identifier);

    String getNullPhrase(Boolean isNull);

    String getAutoGeneratedIdentityPhrase();

    String getCreateTablePhrase();

    String buildPrimaryKeyPhrase(List<String> columnNames);

    Boolean shouldSpecifyNotNullOnIdentity();
}
