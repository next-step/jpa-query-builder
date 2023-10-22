package persistence.sql.ddl.builder;


import persistence.entitiy.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;

public interface DDLQueryBuilder {
    String prepareStatement(EntityAttribute entityAttribute, SqlConverter sqlConverter);
}
