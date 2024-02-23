package persistence.sql.ddl.table;

import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TableNameTest {

    @Test
    @DisplayName("클래스에 @Table 이 없는 경우 테이블명이 클래스명의 카멜케이스로 된다.")
    void from_1() {
        // given
        Class<TableNameTest> aClass = TableNameTest.class;

        // when
        TableName result = TableName.from(aClass);

        //then
        assertThat(result.getName()).isEqualTo("table_name_test");
    }

    @Test
    @DisplayName("클래스에 @Table 이 있고 name 속성이 없는 경우 테이블명이 클래스명의 카멜케이스로 된다.")
    void from_2() {
        // given
        Class<UnspecifiedName> aClass = UnspecifiedName.class;

        // when
        TableName result = TableName.from(aClass);

        //then
        assertThat(result.getName()).isEqualTo("table_name_test$_unspecified_name");
    }

    @Test
    @DisplayName("클래스에 @Table 이 있고 name 속성이 있는 경우 테이블명이 name 속성 값으로 된다.")
    void from_3() {
        // given
        Class<SpecifiedName> aClass = SpecifiedName.class;

        // when
        TableName result = TableName.from(aClass);

        //then
        assertThat(result.getName()).isEqualTo("time_table");
    }

    @Table
    static class UnspecifiedName {
    }

    @Table(name = "time_table")
    static class SpecifiedName {
    }
}
