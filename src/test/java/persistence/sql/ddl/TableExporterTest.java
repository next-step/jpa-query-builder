package persistence.sql.ddl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TableExporterTest {

    @Test
    @DisplayName("[성공] Person 클래스에 대한 create 쿼리 생성")
    void createQuery() {
        TableExporter exporter = new TableExporter();
        String expectedQuery = "create table person ("
                + "id bigint not null, "
                + "name varchar(255), "
                + "age integer, "
                + "primary key (id)"
                + ")";
        Assertions.assertEquals(exporter.getSqlCreateQueryString(Person.class), expectedQuery);
    }

}
