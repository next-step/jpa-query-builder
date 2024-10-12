package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

class QueryBuilderDDL2Test {

    @Test
    void createTest() {
        QueryBuilderDDL3 queryBuilderDDL3 = new QueryBuilderDDL3();
        String tableSQL = queryBuilderDDL3.createTableSQL(Person.class);
        System.out.println("tableSQL = " + tableSQL);
    }
}
