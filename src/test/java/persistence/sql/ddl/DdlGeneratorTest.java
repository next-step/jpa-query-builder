package persistence.sql.ddl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.core.EntityMetadata;
import domain.FixtureEntity;
import persistence.dialect.h2.H2Dialect;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DdlGeneratorTest {
    DdlGenerator generator;
    EntityMetadata<?> entityMetadata;

    @BeforeEach
    void setUp() {
        final H2Dialect dialect = new H2Dialect();
        generator = new DdlGenerator(dialect);
    }

    @Test
    @DisplayName("Person class create 쿼리 생성 테스트")
    void generatePersonCreateDdlTest() {
        entityMetadata = new EntityMetadata<>(FixtureEntity.Person.class);
        final String query = generator.generateCreateDdl(entityMetadata);

        assertThat(query).isEqualToIgnoringCase("create table users (id bigint not null auto_increment,nick_name varchar(255),old int,email varchar(255) not null,CONSTRAINT PK_Users PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("Person class drop 쿼리 생성 테스트")
    void generatePersonDropDdlTest() {
        entityMetadata = new EntityMetadata<>(FixtureEntity.Person.class);
        final String query = generator.generateDropDdl(entityMetadata);

        assertThat(query).isEqualToIgnoringCase("drop table users");
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("fixtureArgumentProvider")
    @DisplayName("class create 쿼리 생성 테스트")
    void generateFixtureCreateDdlTest(final Class<?> clazz, final String expected, final String message) {
        entityMetadata = new EntityMetadata<>(clazz);
        final String query = generator.generateCreateDdl(entityMetadata);

        assertThat(query).isEqualToIgnoringCase(expected);
    }

    private static Stream<Arguments> fixtureArgumentProvider() {
        return Stream.of(
                Arguments.of(FixtureEntity.WithId.class, "create table WithId (id bigint not null,CONSTRAINT PK_WithId PRIMARY KEY (id))", "Id 컬럼만 있을경우"),
                Arguments.of(FixtureEntity.WithIdAndColumn.class, "create table WithIdAndColumn (test_id bigint not null,CONSTRAINT PK_WithIdAndColumn PRIMARY KEY (test_id))", "Id 컬럼에 @Column(name) 설정이 있을 경우"),
                Arguments.of(FixtureEntity.IdWithGeneratedValue.class, "create table IdWithGeneratedValue (id bigint not null auto_increment,CONSTRAINT PK_IdWithGeneratedValue PRIMARY KEY (id))", "Id 컬럼이 GeneratedValue(IDENTITY) 가 있을 경우"),
                Arguments.of(FixtureEntity.WithoutColumn.class, "create table WithoutColumn (id bigint not null,column varchar(255),CONSTRAINT PK_WithoutColumn PRIMARY KEY (id))", "일반 필드에 @Column 이 없을경우"),
                Arguments.of(FixtureEntity.WithColumn.class, "create table WithColumn (id bigint not null,test_column varchar(255),notNullColumn varchar(255) not null,CONSTRAINT PK_WithColumn PRIMARY KEY (id))", "일반 필드에 @Column(name, nullable) 이 있을경우"),
                Arguments.of(FixtureEntity.WithTransient.class, "create table WithTransient (id bigint not null,CONSTRAINT PK_WithTransient PRIMARY KEY (id))", "일반 필드에 @Transient 가 있을경우"),
                Arguments.of(FixtureEntity.WithoutTableAnnotation.class, "create table WithoutTableAnnotation (id bigint not null,CONSTRAINT PK_WithoutTableAnnotation PRIMARY KEY (id))", "클래스에 @Table 이 없을경우"),
                Arguments.of(FixtureEntity.WithTable.class, "create table test_table (id bigint not null,CONSTRAINT PK_test_table PRIMARY KEY (id))", "클래스에 @Table(name) 이 있을경우"),
                Arguments.of(FixtureEntity.WithColumnLength.class, "create table WithColumnLength (id bigint not null,column_length_hundred varchar(100),defaultLength varchar(255),CONSTRAINT PK_WithColumnLength PRIMARY KEY (id))", "일반 필드에 @Column(name, nullable) 이 있을경우")
        );
    }
}
