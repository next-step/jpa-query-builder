package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.LegacyPerson;
import persistence.entity.Person;
import persistence.entity.User;
import persistence.sql.ddl.dialect.database.ConstraintsMapper;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.dialect.h2.H2ConstraintsMapper;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    private EntityMappingTable legacyPersonDomain;
    private EntityMappingTable personDomain;
    private EntityMappingTable userDomain;

    private TypeMapper typeMapper;
    private ConstraintsMapper constraints;

    @BeforeEach
    void setUp() {
        this.legacyPersonDomain = EntityMappingTable.from(LegacyPerson.class);
        this.personDomain = EntityMappingTable.from(Person.class);
        this.userDomain = EntityMappingTable.from(User.class);

        this.typeMapper = H2TypeMapper.newInstance();
        this.constraints = H2ConstraintsMapper.newInstance();
    }

    @DisplayName("일반적인 Entity에서 테이블을 만드는 쿼리를 반환한다.")
    @Test
    void createQuery() {
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(
                legacyPersonDomain,
                typeMapper,
                constraints
        );

        final String expected = "CREATE TABLE LegacyPerson(\n" +
                "id BIGINT PRIMARY KEY,\n" +
                "name VARCHAR,\n" +
                "age INTEGER\n" +
                ");";

        assertThat(createQueryBuilder.toSql()).isEqualTo(expected);
    }

    @DisplayName("컬럼 name으로 쿼리문을 반환한다.")
    @Test
    void createColumnNameSql() {
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(
                personDomain,
                typeMapper,
                constraints
        );

        final String expected = "CREATE TABLE Person(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "nick_name VARCHAR,\n" +
                "old INTEGER,\n" +
                "email VARCHAR  NOT NULL\n" +
                ");";

        assertThat(createQueryBuilder.toSql()).isEqualTo(expected);
    }

    @DisplayName("Table name으로 쿼리문 반환한다.")
    @Test
    void createTableNameSql() {
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(
                userDomain,
                typeMapper,
                constraints
        );

        final String expected = "CREATE TABLE users(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "nick_name VARCHAR,\n" +
                "old INTEGER,\n" +
                "email VARCHAR  NOT NULL\n" +
                ");";

        assertThat(createQueryBuilder.toSql()).isEqualTo(expected);
    }
}
