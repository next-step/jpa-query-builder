package persistence.sql.ddl;

import org.junit.jupiter.api.*;

import java.util.List;

import org.assertj.core.api.Assertions;

class CreateQueryBuilderTest {
    @Test
    @DisplayName("[요구사항 1] @Column 애노테이션이 없는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항1_test() {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS Person " +
                "(id Long AUTO_INCREMENT PRIMARY KEY,name VARCHAR(30) NULL,age INT NULL)";
        // when
        String actualQuery = new CreateQueryBuilder(persistence.entity.basic.Person.class).getQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 3.2] @Transient 애노테이션이 붙은 필드는 제하고 컬럼 만든다.")
    void 요구사항3_2_test() {
        //given
        List<String> expectedColumnQueries = List.of("nick_name VARCHAR(30) NULL",
                "old INT NULL",
                "email VARCHAR(30) NOT NULL");

        // when
        List<String> actualColumnQueries = new Table(persistence.entity.notcolumn.Person.class).getColumnQueries();

        // then
        Assertions.assertThat(expectedColumnQueries.containsAll(actualColumnQueries)).isTrue();
        Assertions.assertThat(actualColumnQueries).hasSize(3);
    }
}