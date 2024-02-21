package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.LegacyPerson;
import persistence.entity.Person;
import persistence.entity.User;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    private EntityMappingTable entityMappingTable;
    private EntityMappingTable existColumnEntityMapping;
    private EntityMappingTable existTableEntityMapping;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(LegacyPerson.class);
        this.existColumnEntityMapping = EntityMappingTable.from(Person.class);
        this.existTableEntityMapping = EntityMappingTable.from(User.class);
    }

    @DisplayName("테이블 만드는 쿼리문을 반환한다.")
    @Test
    void createQuery() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityMappingTable);

        final String expected = "CREATE TABLE LegacyPerson(\n" +
                "id BIGINT PRIMARY KEY,\n" +
                "name VARCHAR,\n" +
                "age INTEGER\n" +
                ");";

        assertThat(createQueryBuilder.toSql(H2TypeMapper.newInstance())).isEqualTo(expected);
    }

    @DisplayName("컬럼 name으로 쿼리문을 반환한다.")
    @Test
    void createColumnNameSql() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(existColumnEntityMapping);

        final String expected = "CREATE TABLE Person(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "nick_name VARCHAR,\n" +
                "old INTEGER,\n" +
                "email VARCHAR  NOT NULL\n" +
                ");";

        assertThat(createQueryBuilder.toSql(H2TypeMapper.newInstance())).isEqualTo(expected);
    }

    @DisplayName("Table name으로 쿼리문 반환한다.")
    @Test
    void createTableNameSql() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(existTableEntityMapping);

        final String expected = "CREATE TABLE users(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "nick_name VARCHAR,\n" +
                "old INTEGER,\n" +
                "email VARCHAR  NOT NULL\n" +
                ");";

        assertThat(createQueryBuilder.toSql(H2TypeMapper.newInstance())).isEqualTo(expected);
    }

}