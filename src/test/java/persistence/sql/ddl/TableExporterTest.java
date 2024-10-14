package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.PersonWithEntityIdFixture;
import persistence.sql.ddl.fixture.PersonWithGeneratedValueColumnFixture;
import persistence.sql.ddl.fixture.PersonWithTableTransientFixture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableExporterTest {

    @Test
    @DisplayName("[성공] Person @Entity @Id 클래스에 대한 create query 검증")
    void createQueryWithEntityId() {
        TableExporter exporter = new TableExporter();
        String expectedQuery = "create table PersonWithEntityIdFixture ("
                + "id bigint auto_increment, "
                + "name varchar(255), "
                + "age integer, "
                + "primary key (id)"
                + ")";
        assertEquals(exporter.getSqlCreateQueryString(PersonWithEntityIdFixture.class), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person @GeneratedValue @Column 클래스에 대한 create query 검증")
    void createQueryWithGeneratedValueColumn() {
        TableExporter exporter = new TableExporter();
        String expectedQuery = "create table PersonWithGeneratedValueColumnFixture ("
                + "id bigint auto_increment, "
                + "nick_name varchar(255), "
                + "old integer, "
                + "email varchar(255) not null, "
                + "primary key (id)"
                + ")";
        assertEquals(exporter.getSqlCreateQueryString(PersonWithGeneratedValueColumnFixture.class), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person @Table @Transient 클래스에 대한 create query 검증")
    void createQueryWithTableTransient() {
        TableExporter exporter = new TableExporter();
        String expectedQuery = "create table users ("
                + "id bigint auto_increment, "
                + "nick_name varchar(255), "
                + "old integer, "
                + "email varchar(255) not null, "
                + "primary key (id)"
                + ")";
        assertEquals(exporter.getSqlCreateQueryString(PersonWithTableTransientFixture.class), expectedQuery);
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
