package persistence.sql.ddl.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.entity.Person;
import persistence.sql.ddl.entity.Person4;

class DefaultQueryBuilderTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DefaultQueryBuilderTest.class);

    private final Class<?> entityClass = Person.class;

    private final QueryBuilder queryBuilder = new DefaultQueryBuilder();

    @Test
    @DisplayName("@Entity, @Table, @Id, @Column, @Transient 어노테이션을 바탕으로 create 쿼리 만들어보기")
    void createDDL() {
        String ddl = queryBuilder.buildDDL(entityClass);

        log.debug("DDL: {}", ddl);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) UNIQUE NOT NULL)");
    }

    @Test
    @DisplayName("@Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 create 쿼리 만들어보기")
    void createDDLWithSchema() {
        String ddl = queryBuilder.buildDDL(Person4.class);

        log.debug("DDL: {}", ddl);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE schema.users (id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) UNIQUE NOT NULL)");
    }

    @Test
    @DisplayName("@Entity, @Table, @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
    void buildDropQuery() {
        String dropQuery = queryBuilder.buildDropQuery(entityClass);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE users");
    }

    @Test
    @DisplayName("@Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
    void buildDropQueryWithSchema() {
        String dropQuery = queryBuilder.buildDropQuery(Person4.class);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE schema.users");
    }

    @Test
    @DisplayName("클래스 정보와 @Table 어노테이션을 바탕으로 테이블명 가져오기")
    void getTableName() {
        String tableName = queryBuilder.getTableNameFrom(entityClass);

        log.debug("Table name: {}", tableName);

        assertThat(tableName).isEqualTo("users");
    }

    @Test
    @DisplayName("클래스 정보와 @Table(schema) 어노테이션을 바탕으로 테이블명 가져오기")
    void getTableNameWithSchema() {
        String tableName = queryBuilder.getTableNameFrom(Person4.class);

        log.debug("Table name: {}", tableName);

        assertThat(tableName).isEqualTo("schema.users");
    }

    @Test
    @DisplayName("클래스 정보와 @Id, @Column, @Transient 어노테이션을 바탕으로 컬럼 선언문 가져오기")
    void getColumnDefinitionStatement() {
        String columnDefinitionStatement = queryBuilder.getTableColumnDefinitionFrom(entityClass);

        log.debug("Column definition statement: {}", columnDefinitionStatement);

        assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) UNIQUE NOT NULL");
    }

    @ParameterizedTest(name = "{0} 필드 정보를 바탕으로 컬럼 선언문 가져오기")
    @CsvSource({
        "id,id BIGINT AUTO_INCREMENT",
        "name,nick_name VARCHAR(255)",
        "age,old INTEGER",
        "email,email VARCHAR(255) UNIQUE NOT NULL"
    })
    @DisplayName("클래스의 필드 정보를 바탕으로 컬럼 선언문 가져오기")
    void getColumnDefinitionStatementFromField(
        String fieldName, String expectedColumnDefinitionStatement
    ) throws NoSuchFieldException {
        Field field = entityClass.getDeclaredField(fieldName);

        String actualColumnDefinitionStatement = queryBuilder.getColumnDefinitionFrom(field);

        assertThat(actualColumnDefinitionStatement).isEqualTo(expectedColumnDefinitionStatement);
    }
}
