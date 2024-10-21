package H2QueryBuilder;

import common.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static H2QueryBuilder.fixtures.BuilderDDLFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2QueryBuilderDDLTest {
    @DisplayName("create 쿼리 검증 1")
    @Test
    void confirmIdAnnotationTest() {
        //given
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when
        String createQuery = h2QueryBuilderDDL.create(아이디만_존재하는_클래스());

        //then
        assertThat(createQuery).isEqualTo(
                "CREATE TABLE person (id BIGINT NOT NULL PRIMARY KEY);"
        );
    }

    @DisplayName("create 쿼리 검증 2")
    @Test
    void notExistColumnAnnotationTest() {
        //given
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when
        assertThat(h2QueryBuilderDDL.create(컬럼어노테이션_존재하지않는_클래스())).isEqualTo(
                "CREATE TABLE person (id BIGINT NOT NULL PRIMARY KEY, name VARCHAR, age INT, email VARCHAR);"
        );
    }

    @DisplayName("create 쿼리 검증 3")
    @Test
    void existGeneratedValueAnnotationOverTwoTest() {
        //given
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThat(h2QueryBuilderDDL.create(모든어노테이션_존재하는_클래스())).isEqualTo(
                "CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INT, email VARCHAR NOT NULL);"
        );
    }

    @DisplayName("drop 쿼리 검증 1")
    @Test
    void createDropQueryTest() {
        //given
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThat(h2QueryBuilderDDL.drop(모든어노테이션_존재하는_클래스())).isEqualTo(
                "DROP TABLE users;"
        );
    }

    @DisplayName("지원하지 않는 SQL 타입 Excpetion 검증")
    @Test
    void notSupplySqlTypeTest() {
        //given
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThatThrownBy(() -> h2QueryBuilderDDL.create(타입을_지원하지않는_클래스()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NOT_ALLOWED_DATATYPE.getErrorMsg());
    }

}