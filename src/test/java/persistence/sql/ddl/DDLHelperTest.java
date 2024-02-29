package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DDLHelperTest {
    @Test
    void 엔티티_어노테이션이_붙지_않은_클래스가_입력되는_경우_예외를_발생시킨다() {
        var ddlHelper = new DDLHelper();
        assertThrowsExactly(IllegalArgumentException.class, () -> ddlHelper.getCreateQuery(TestClass.class));
    }

    @Test
    void create_쿼리를_생성한다() {
        var ddlHelper = new DDLHelper();
        assertEquals("create table users(id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL);", ddlHelper.getCreateQuery(Person.class));
    }

    @Test
    void drop_쿼리를_생성한다() {
        var ddlHelper = new DDLHelper();
        assertEquals("drop table users;", ddlHelper.getDropQuery(Person.class));
    }
}

class TestClass {

}