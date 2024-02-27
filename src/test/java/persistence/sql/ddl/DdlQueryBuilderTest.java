package persistence.sql.ddl;

import domain.Person1;
import domain.Person2;
import domain.Person3;
import domain.step2.dialect.H2Dialect;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DdlQueryBuilderTest {

    private static final DdlQueryBuilder DDL_QUERY_BUILDER = new DdlQueryBuilder(new H2Dialect());

    Class<?> person1 = Person1.class;
    Class<?> person2 = Person2.class;
    Class<?> person3 = Person3.class;

    @Test
    void 요구사항_1_DDL_테스트() {
        String ddlQuery = "CREATE TABLE person1 ( id BIGINT NOT NULL PRIMARY KEY, name VARCHAR NULL, age INT NULL );";
        assertThat(DDL_QUERY_BUILDER.createTable(person1)).isEqualTo(ddlQuery);
    }

    @Test
    void 요구사항_2_DDL_테스트() {
        String ddlQuery = "CREATE TABLE person2 ( id BIGINT NOT NULL PRIMARY KEY auto_increment, nick_name VARCHAR (255) NULL, old INT NULL, email VARCHAR (255) NOT NULL );";
        assertThat(DDL_QUERY_BUILDER.createTable(person2)).isEqualTo(ddlQuery);
    }

    @Test
    void 요구사항_3_DDL_테스트() {
        String ddlQuery = "CREATE TABLE users ( id BIGINT NOT NULL PRIMARY KEY auto_increment, nick_name VARCHAR (255) NULL, old INT NULL, email VARCHAR (255) NOT NULL );";
        assertThat(DDL_QUERY_BUILDER.createTable(person3)).contains(ddlQuery);
    }

    @Test
    void 요구사항_4_Drop_테스트() {
        assertAll(
                () -> assertThat(DDL_QUERY_BUILDER.dropTable(person1)).contains("DROP TABLE person1 IF EXISTS;"),
                () -> assertThat(DDL_QUERY_BUILDER.dropTable(person2)).contains("DROP TABLE person2 IF EXISTS;"),
                () -> assertThat(DDL_QUERY_BUILDER.dropTable(person3)).contains("DROP TABLE users IF EXISTS;")
        );
    }

    @Test
    void 엔티티_체크_테스트() {
        assertAll(
                () -> assertThat(person1.isAnnotationPresent(Entity.class)).isTrue(),
                () -> assertThat(person2.isAnnotationPresent(Entity.class)).isTrue(),
                () -> assertThat(person3.isAnnotationPresent(Entity.class)).isTrue()
        );
    }

    @Test
    void 테이블명_조회_테스트() {
        assertAll(
                () -> assertThat(getTableName(person1)).isEqualTo("person1"),
                () -> assertThat(getTableName(person2)).isEqualTo("person2"),
                () -> assertThat(getTableName(person3)).isEqualTo("users")
        );
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }
}
