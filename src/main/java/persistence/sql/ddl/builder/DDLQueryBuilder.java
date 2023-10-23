package persistence.sql.ddl.builder;


import persistence.entity.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;

public interface DDLQueryBuilder {
    String prepareStatement(EntityAttribute entityAttribute, SqlConverter sqlConverter);
}
