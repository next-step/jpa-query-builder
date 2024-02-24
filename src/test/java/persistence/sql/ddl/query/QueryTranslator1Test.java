package persistence.sql.ddl.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import persistence.sql.ddl.entity.Person1;

@DisplayName("1단계 요구사항 - @Entity, @Id 어노테이션을 바탕으로 create 쿼리 만들어보기")
class QueryTranslator1Test {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryTranslator2Test.class);

    private final Class<?> entityClass = Person1.class;

    private final QueryTranslator queryTranslator = new QueryTranslator();

    @Test
    @DisplayName("@Entity, @Id 어노테이션을 바탕으로 create 쿼리 만들어보기")
    void createDDL() {
        String ddl = queryTranslator.getCreateTableQuery(entityClass);

        log.debug("DDL: {}", ddl);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE Person1 (id BIGINT AUTO_INCREMENT, name VARCHAR(255), age INTEGER)");
    }

    @Test
    @DisplayName("@Entity, @Id 어노테이션을 바탕으로 drop 쿼리 만들어보기")
    void buildDropQuery() {
        String dropQuery = queryTranslator.getDropTableQuery(entityClass);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE Person1");
    }

    @Test
    @DisplayName("클래스 정보를 바탕으로 테이블명 가져오기")
    void getTableNameByClassName() {
        String tableName = queryTranslator.getTableNameFrom(entityClass);

        log.debug("Table name: {}", tableName);

        assertThat(tableName).isEqualTo("Person1");
    }


    @Test
    @DisplayName("클래스 정보를 바탕으로 컬럼 선언문 가져오기")
    void getColumnDefinitionStatement() {
        String columnDefinitionStatement = queryTranslator.getTableColumnDefinitionFrom(entityClass);

        log.debug("Column definition statement: {}", columnDefinitionStatement);

        assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT, name VARCHAR(255), age INTEGER");
    }

    @ParameterizedTest(name = "{0} 필드 정보를 바탕으로 컬럼 선언문 가져오기")
    @CsvSource({
        "id,id BIGINT AUTO_INCREMENT",
        "name,name VARCHAR(255)",
        "age,age INTEGER"
    })
    @DisplayName("클래스의 필드 정보를 바탕으로 컬럼 선언문 가져오기")
    void getColumnDefinitionStatementFromField(
        String fieldName, String expectedColumnDefinitionStatement
    ) throws NoSuchFieldException {
        Field field = entityClass.getDeclaredField(fieldName);

        String actualColumnDefinitionStatement = queryTranslator.getColumnDefinitionFrom(field);

        assertThat(actualColumnDefinitionStatement).isEqualTo(expectedColumnDefinitionStatement);
    }
}
