package persistence.sql.ddl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DDLQueryGeneratorTest {
    DDLQueryGenerator sut = new DDLQueryGenerator(new H2Dialect());

    @Test
    @DisplayName("요구사항1: 기본 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery() {
        String expect = "CREATE TABLE requirement1 (id BIGINT, name VARCHAR, age INTEGER, PRIMARY KEY (id))";

        String query = sut.generateCreateQuery(Requirement1.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항2: 추가된 정보 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery2() {
        String expect = "CREATE TABLE requirement2 (id BIGINT generated by default as identity, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";

        String query = sut.generateCreateQuery(Requirement2.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항3: 추가된 정보 테이블 생성 쿼리 생성 테스트2")
    void testGenerateCreateQuery3() {
        String expect = "CREATE TABLE users (id BIGINT generated by default as identity, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";

        String query = sut.generateCreateQuery(Requirement3.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항4: 정보를 바탕으로 drop 쿼리 만들어보기")
    void testDropQueryGenerate() {
        String expect = "drop table users";
        
        String query = sut.generateDropTableQuery(Requirement4.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("기본키 없으면 에러")
    void throwErrorWhenPrimaryKeyIsNotDefined() {
        assertThrows(IdAnnotationMissingException.class, () -> {
            sut.generateCreateQuery(NoPrimaryKeyTest.class);
        });
    }

    @Test
    @DisplayName("Entity 클래스가 아니면 에러")
    void throwErrorWhenClassIsNotForEntity() {
        assertThrows(AnnotationMissingException.class, () -> {
            sut.generateCreateQuery(NoEntityAnnotationTest.class);
        });
    }
}
