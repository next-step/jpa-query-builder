package persistence.sql.ddl;

import entity.Person;
import org.junit.jupiter.api.Test;

class QueryBuilderDDL2Test {

    @Test
    void createTest() {
        QueryBuilderDDL2 queryBuilderDDL2 = new QueryBuilderDDL2();
        String tableSQL = queryBuilderDDL2.createTableSQL(Person.class);
        System.out.println("tableSQL = " + tableSQL);
    }
}
