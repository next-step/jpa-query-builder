package hibernate;

import domain.Person;
import domain.Person2;
import domain.Person3;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CreateQueryBuilderTest {

    private final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();

    @Test
    void Entity가_걸린_클래스의_create_쿼리를_생성한다() {
        // given
        Pattern expectedPattern = Pattern.compile("create table person \\(([^)]+(?:,\\s*[^)]+)*)\\);");
        List<String> expectedColumns = List.of("id bigint", "age integer", "name varchar");

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person.class)
                .toLowerCase();

        // then
        assertAll(
                () -> assertThat(actual).matches(expectedPattern),
                () -> assertThat(actual).contains(expectedColumns)
        );
    }

    @Test
    void Id가_걸린_필드는_기본키로_create_쿼리를_생성한다() {
        // given
        String expectedColumn = "id bigint primary key";

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person.class);

        // then
        assertThat(actual).contains(expectedColumn);
    }

    @Test
    void Column이_걸린_필드는_컬럼명이_해당값으로_create_쿼리가_생성된다() {
        // given
        List<String> expectedColumns = List.of("nick_name varchar", "old integer", "email varchar");

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person2.class)
                .toLowerCase();

        // then
        assertThat(actual).contains(expectedColumns);
    }

    @Test
    void Column에서_nullable이_false인_경우_NOT_NULL_create_쿼리가_생성된다() {
        // given
        String expectedColumn = "email varchar not null";

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person2.class)
                .toLowerCase();

        // then
        assertThat(actual).contains(expectedColumn);
    }

    @Test
    void Id의_생성전략이_IDENTITY인_경우_auto_increment_create_쿼리가_생성된다() {
        // given
        String expectedColumn = "id bigint primary key auto_increment";

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person2.class)
                .toLowerCase();

        // then
        assertThat(actual).contains(expectedColumn);
    }

    @Test
    void Table_어노테이션에_name이_있는_경우_테이블의_이름이_해당_값으로_create_쿼리가_생성된다() {
        // given
        String expected = "create table users";

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person3.class)
                .toLowerCase();

        // then
        assertThat(actual).startsWith(expected);
    }

    @Test
    void Transient_어노테이션이_달린_필드는_create_쿼리에서_제외한다() {
        // given
        String expected = "index integer";

        // when
        String actual = createQueryBuilder.generateCreateQuery(Person3.class)
                .toLowerCase();

        // then
        assertThat(actual).doesNotContain(expected);
    }
}
