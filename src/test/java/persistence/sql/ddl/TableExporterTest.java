package persistence.sql.ddl;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("[실패] @Entity 애노테이션이 없는 클래스에 대한 create 쿼리 생성 시도 시, RuntimeException 발생")
    void createQueryWithoutEntityAnnotation() {
        TableExporter exporter = new TableExporter();
        assertThatThrownBy(() -> exporter.getSqlCreateQueryString(NotEntityPerson.class))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("@Entity not exist. class = " + NotEntityPerson.class.getName());

    }

    static class NotEntityPerson {

    }
}
