package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.Person;
import persistence.sql.ddl.entity.Person2;
import persistence.sql.ddl.entity.Person3;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    @Test
    @DisplayName("기본 Person 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest() {
        // given
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
        String expectedQuery = "CREATE TABLE PERSON (ID BIGINT PRIMARY KEY, NAME VARCHAR(255), AGE INTEGER);";

        // when
        String actualQuery = createQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@GeneratedValue, @Column 추가된 Person2 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest2() {
        // given
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person2.class);
        String expectedQuery = "CREATE TABLE PERSON2 (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NICK_NAME VARCHAR(255), OLD INTEGER, EMAIL VARCHAR(255) NOT NULL);";

        // when
        String actualQuery = createQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Table, @Transient 추가된 Person3 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest3() {
        // given
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person3.class);
        String expectedQuery = "CREATE TABLE USERS (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NICK_NAME VARCHAR(255), OLD INTEGER, EMAIL VARCHAR(255) NOT NULL);";

        // when
        String actualQuery = createQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

}
