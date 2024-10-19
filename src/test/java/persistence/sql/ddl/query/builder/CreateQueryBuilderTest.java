package persistence.sql.ddl.query.builder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.NotExistException;
import persistence.sql.ddl.fixture.PersonNotEntityFixture;
import persistence.sql.ddl.fixture.PersonWithEntityIdFixture;
import persistence.sql.ddl.fixture.PersonWithGeneratedValueColumnFixture;
import persistence.sql.ddl.fixture.PersonWithTableTransientFixture;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.dialect.H2Dialect;

public class CreateQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person @Entity @Id 클래스에 대한 create query 검증")
    void createQueryWithEntityId() {
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        String expectedQuery = """
                create table PersonWithEntityIdFixture (id bigint auto_increment, name varchar(255), age integer, primary key (id))""";
        assertEquals(queryBuilder.build(PersonWithEntityIdFixture.class, new H2Dialect()), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person @GeneratedValue @Column 클래스에 대한 create query 검증")
    void createQueryWithGeneratedValueColumn() {
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        String expectedQuery = """
                create table PersonWithGeneratedValueColumnFixture (id bigint auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id))""";
        assertEquals(queryBuilder.build(PersonWithGeneratedValueColumnFixture.class, new H2Dialect()), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person @Table @Transient 클래스에 대한 create query 검증")
    void createQueryWithTableTransient() {
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        String expectedQuery = """
                create table users (id bigint auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id))""";

        assertEquals(queryBuilder.build(PersonWithTableTransientFixture.class, new H2Dialect()).trim(), expectedQuery.trim());
    }

    @Test
    @DisplayName("[실패] @Entity 애노테이션이 없는 클래스에 대한 create 쿼리 생성 시도 시, RuntimeException 발생")
    void createQueryWithoutEntityAnnotation() {
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        assertThatThrownBy(() -> queryBuilder.build(PersonNotEntityFixture.class, new H2Dialect()))
                .isInstanceOf(NotExistException.class)
                .hasMessage("Not exist @Entity annotation. class = persistence.sql.ddl.fixture.PersonNotEntityFixture");

    }

}
