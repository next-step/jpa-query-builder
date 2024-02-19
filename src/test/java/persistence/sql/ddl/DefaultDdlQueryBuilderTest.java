package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.query.Query;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDdlQueryBuilderTest {

    private final Dialect dialect = new H2Dialect();
    private final DdlQueryBuilder queryBuilder = new DefaultDdlQueryBuilder(dialect);

    @DisplayName("Entity 의 기본 JAVA 객체 정보만을 가지고 create 쿼리를 만든다")
    @Test
    public void buildCreateQueryV1() throws Exception {
        // given
        final Class<PersonV1> clazz = PersonV1.class;
        final Query query = queryBuilder.generateQuery(clazz);

        final String ddl = """
                CREATE TABLE PersonV1 (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(255),
                    age INTEGER
                );""".trim();

        // when
        final String createQuery = queryBuilder.buildCreateQuery(query);

        // then
        assertThat(createQuery).isEqualTo(ddl);
    }

    @DisplayName("Entity 의 @Column, @GeneratedValue 정보를 추가로 create 쿼리를 만든다")
    @Test
    public void buildCreateQueryV2() throws Exception {
        // given
        final Class<PersonV2> clazz = PersonV2.class;
        final Query query = queryBuilder.generateQuery(clazz);

        final String ddl = """
                CREATE TABLE PersonV2 (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nick_name VARCHAR(255),
                    old INTEGER,
                    email VARCHAR(255) NOT NULL
                );""".trim();

        // when
        final String createQuery = queryBuilder.buildCreateQuery(query);

        // then
        assertThat(createQuery).isEqualTo(ddl);
    }

    @DisplayName("Entity 의 @Table, @Transient 정보를 추가로 create 쿼리를 만든다")
    @Test
    public void buildCreateQueryV3() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Query query = queryBuilder.generateQuery(clazz);

        final String ddl = """
                CREATE TABLE users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nick_name VARCHAR(255),
                    old INTEGER,
                    email VARCHAR(255) NOT NULL
                );""".trim();

        // when
        final String createQuery = queryBuilder.buildCreateQuery(query);

        // then
        assertThat(createQuery).isEqualTo(ddl);
    }

    @DisplayName("Entity 의 기본 JAVA 객체 정보만을 가지고 drop 쿼리를 만든다")
    @Test
    public void buildPersonV1DropQuery() throws Exception {
        // given
        final Class<PersonV1> clazz = PersonV1.class;
        final Query query = queryBuilder.generateQuery(clazz);

        final String ddl = "DROP TABLE IF EXISTS PersonV1;";

        // when
        final String dropQuery = queryBuilder.buildDropQuery(query);

        // then
        assertThat(dropQuery).isEqualTo(ddl);
    }

    @DisplayName("Entity @Table 정보로 drop 쿼리를 만든다")
    @Test
    public void buildPersonV3DropQuery() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Query query = queryBuilder.generateQuery(clazz);

        final String ddl = "DROP TABLE IF EXISTS users;";

        // when
        final String dropQuery = queryBuilder.buildDropQuery(query);

        // then
        assertThat(dropQuery).isEqualTo(ddl);
    }

}