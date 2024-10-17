package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.*;

import static org.assertj.core.api.Assertions.*;

public class QueryBuilderTest {
    @Test
    @DisplayName("Person 엔티티 create 쿼리를 생성한다")
    void testCreatePersonQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "CREATE TABLE Person (id BIGINT PRIMARY KEY, name VARCHAR(255), age INTEGER);";
        assertThat(queryBuilder.create(Person.class)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Table이 선언되었지만 name이 없는 경우 entity name이 테이블 이름이 되어야한다")
    void testWithoutTableEntityAnnotation() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "CREATE TABLE Person (id BIGINT PRIMARY KEY, name VARCHAR(255), age INTEGER);";
        assertThat(queryBuilder.create(Person.class)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Entity가 없는 경우 에러가 발생한다")
    void testWithoutEntity() {
        QueryBuilder queryBuilder = new QueryBuilder();
        assertThatIllegalArgumentException().isThrownBy(() -> queryBuilder.create(PersonWithoutEntity.class));
    }

    @Test
    @DisplayName("NewPerson 엔티티 create 쿼리를 생성한다")
    void testCreateNewPersonQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "CREATE TABLE NewPerson (id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);";
        assertThat(queryBuilder.create(NewPerson.class)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("BrandNewPerson 엔티티 create 쿼리를 생성한다")
    void testCreateBrandNewPersonQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);";
        assertThat(queryBuilder.create(BrandNewPerson.class)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("BrandNewPerson 엔티티의 drop 쿼리를 생성한다")
    void testDropBrandNewPersonQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "DROP TABLE users;";
        assertThat(queryBuilder.drop(BrandNewPerson.class)).isEqualTo(expectedQuery);
    }
}
