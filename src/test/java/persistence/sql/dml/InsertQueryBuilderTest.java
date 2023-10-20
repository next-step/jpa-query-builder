package persistence.sql.dml;

import entity.Person;
import fixture.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    @DisplayName("insert 쿼리 생성 테스트")
    @Test
    void build() {
        //given
        Person changgunyee = PersonFixture.changgunyee();

        //when
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        String insertQuery = insertQueryBuilder.getQuery(changgunyee);

        //then
        assertThat(insertQuery).isEqualToIgnoringWhitespace("" +
                "INSERT INTO users " +
                "(id,nick_name,old,email) " +
                "values (1,'changgunyee',29,'minyoung403@naver.com');"
        );

    }
}