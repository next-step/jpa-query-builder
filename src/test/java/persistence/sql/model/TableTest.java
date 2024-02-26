package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.study.sql.ddl.Person3;

import static org.assertj.core.api.Assertions.assertThat;

public class TableTest {
    private final Table table = new Table(Person3.class);


    @Test
    @DisplayName("올바른 테이블 명이 생성되었는지 확인")
    void getName() {
        String result = table.getName();

        assertThat(result).isEqualTo("users");
    }

}
