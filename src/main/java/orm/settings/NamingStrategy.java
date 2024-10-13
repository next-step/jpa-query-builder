package orm.settings;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public interface NamingStrategy {

    String namingColumn(Column column, Field field);

    <ENTITY> String namingTable(Class<ENTITY> entityClass);
}
