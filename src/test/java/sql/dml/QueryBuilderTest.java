package sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import persistence.sql.dml.Person;
import persistence.sql.dml.QueryBuilder;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder(Person.class);
    Class<?> clazz = Person.class;


    @Test
    @DisplayName("findAll 테스트")
    void findAll() {
        assertThat(queryBuilder.findAll()).isEqualTo("");
    }

    @Test
    @DisplayName("insert문 스트링 체크")
    void insert() {
        assertThat(queryBuilder.insert()).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com')");
    }

    @Test
    @DisplayName("column파트 스트링 체크")
    void getColumnClaus() {
        String testString = queryBuilder.columnsClause(clazz);
        assertThat(testString).isEqualTo("nick_name, old, email");
    }

    @Test
    @DisplayName("테이블 이름 확인")
    void getTablename() {
        assertThat(queryBuilder.getTableName()).isEqualTo("users");
    }

    @Test
    @DisplayName("테이블에 넣을 값 비교")
    void getValueClause() {
        String query = queryBuilder.valueClause(queryBuilder.setValueData());
        assertThat(query).isEqualTo("'jskim', 33, 'qazwsx3745@naver.com'");
    }
//INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com');
}
