package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private QueryBuilder createQueryBuilder;
    private QueryBuilder dropQueryBuilder;
    private QueryBuilder insertQueryBuilder;
    private QueryBuilder selectQueryBuilder;
    private QueryBuilder deleteQueryBuilder;
    private Person person;

    @BeforeEach
    void setUp() {
        person = Person.of("crong", 35, "test@123.com");

        createQueryBuilder = CreateQueryBuilder.of(Person.class);
        dropQueryBuilder = DropQueryBuilder.of(Person.class);
        insertQueryBuilder = InsertQueryBuilder.of(person);
    }

    @Test
    @DisplayName("요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2")
    void generateCreateTableQuery_WithTableAndTransientAnnotation() {
        String createTableQuery = createQueryBuilder.build();

        assertThat(createTableQuery).isEqualTo("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL, PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기")
    void generateDropTableQuery() {
        String dropTableQuery = dropQueryBuilder.build();

        assertThat(dropTableQuery).isEqualTo("DROP TABLE users");
    }

    @Test
    @DisplayName("요구사항 1 - insert 문 구현해보기")
    void generateInsertQuery() {
        String insertQuery = insertQueryBuilder.build();

        assertThat(insertQuery).isEqualTo("INSERT INTO users (id, nick_name, old, email) VALUES (default, 'crong', 35, 'test@123.com')");
    }

    @Test
    @DisplayName("요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기")
    void generateSelectAllQuery() {
        selectQueryBuilder = SelectQueryBuilder.of(Person.class);
        String selectAllQuery = selectQueryBuilder.build();

        assertThat(selectAllQuery).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    @DisplayName("요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기")
    void generateSelectOneQuery() {
        selectQueryBuilder = SelectQueryBuilder.of(person.getClass(), 1L);
        String selectOneQuery = selectQueryBuilder.build();

        assertThat(selectOneQuery).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }

    @Test
    @DisplayName("요구사항 4 - delete 쿼리 만들어보기 (id가 없을 경우 전체 삭제)")
    void generateDeleteAllQuery() {
        deleteQueryBuilder = DeleteQueryBuilder.of(Person.class);
        String deleteAllQuery = deleteQueryBuilder.build();

        assertThat(deleteAllQuery).isEqualTo("DELETE FROM users");
    }

    @Test
    @DisplayName("요구사항 4 - delete 쿼리 만들어보기 (id가 있을 경우 해당 id 삭제)")
    void generateDeleteOneQuery() {
        deleteQueryBuilder = DeleteQueryBuilder.of(Person.class, 1L);
        String deleteOneQuery = deleteQueryBuilder.build();

        assertThat(deleteOneQuery).isEqualTo("DELETE FROM users WHERE id = 1");
    }
}
