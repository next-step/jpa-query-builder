package persistence.sql.ddl;

import domain.EntityMetaData;
import domain.Person1;
import domain.Person2;
import domain.Person3;
import domain.dialect.Dialect;
import domain.dialect.H2Dialect;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DdlQueryBuilderTest {

    EntityMetaData entityMetaData;
    Dialect dialect;
    DdlQueryBuilder ddlQueryBuilder;

    Class<?> person1 = Person1.class;
    Class<?> person2 = Person2.class;
    Class<?> person3 = Person3.class;

    private void init(Class<?> clazz) {
        entityMetaData = new EntityMetaData(clazz);
        dialect = new H2Dialect();
        ddlQueryBuilder = new DdlQueryBuilder(new H2Dialect(), new EntityMetaData(clazz));
    }

    @Test
    void 요구사항_1_DDL_테스트() {
        init(person1);
        String ddlQuery = "CREATE TABLE person1 ( id bigint NOT NULL PRIMARY KEY, name varchar NULL, age int NULL );";
        assertThat(ddlQueryBuilder.createTable(person1)).isEqualTo(ddlQuery);
    }

    @Test
    void 요구사항_2_DDL_테스트() {
        init(person2);
        String ddlQuery = "CREATE TABLE person2 ( id bigint NOT NULL PRIMARY KEY auto_increment, nick_name varchar (255) NULL, old int NULL, email varchar (255) NOT NULL );";
        assertThat(ddlQueryBuilder.createTable(person2)).isEqualTo(ddlQuery);
    }

    @Test
    void 요구사항_3_DDL_테스트() {
        init(person3);
        String ddlQuery = "CREATE TABLE users ( id bigint NOT NULL PRIMARY KEY auto_increment, nick_name varchar (255) NULL, old int NULL, email varchar (255) NOT NULL );";
        assertThat(ddlQueryBuilder.createTable(person3)).contains(ddlQuery);
    }

    @Test
    void 테이블명_조회_drop_테스트() {
        init(person1);
        assertAll(
                () -> assertThat(entityMetaData.getTableName()).isEqualTo("person1"),
                () -> assertThat(ddlQueryBuilder.dropTable()).contains("DROP TABLE person1 IF EXISTS;")
        );

        init(person2);
        assertAll(
                () -> assertThat(entityMetaData.getTableName()).isEqualTo("person2"),
                () -> assertThat(ddlQueryBuilder.dropTable()).contains("DROP TABLE person2 IF EXISTS;")
        );

        init(person3);
        assertAll(
                () -> assertThat(entityMetaData.getTableName()).isEqualTo("users"),
                () -> assertThat(ddlQueryBuilder.dropTable()).contains("DROP TABLE users IF EXISTS;")
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
}
