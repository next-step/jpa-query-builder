package persistence.sql.ddl.builder;


import persistence.sql.ddl.attribute.EntityAttribute;

interface DDLQueryBuilder {

    String prepareStatement(EntityAttribute entityAttribute);
}
