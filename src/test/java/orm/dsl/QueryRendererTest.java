package orm.dsl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.TableEntity;
import orm.TableField;
import orm.dsl.condition.Condition;
import orm.dsl.condition.Conditions;
import persistence.sql.ddl.Person;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static orm.dsl.DSL.eq;

public class QueryRendererTest {

    @Test
    @DisplayName("bulk insert 를 위한 쿼리 렌더링 테스트")
    void bulk_insert를_위한_쿼리_렌더링_테스트() {

        // given
        QueryRenderer queryRenderer = new QueryRenderer();
        List<TableEntity<Person>> tableEntity = List.of(
                new TableEntity<>(new Person(1L, 30, "설동민")),
                new TableEntity<>(new Person(2L, 30, "설동민2"))
        );

        List<List<? extends TableField>> 벌크인서트용_컬럼들 = tableEntity.stream()
                .map(TableEntity::getAllFields)
                .collect(Collectors.toList());

        // when
        String query = queryRenderer.renderBulkInsertValues(벌크인서트용_컬럼들);

        // then
        assertThat(query).isEqualTo("(1,'설동민',30), (2,'설동민2',30)");
    }

    @Test
    @DisplayName("bulk insert 를 위한 쿼리 렌더링 테스트 - emptyList의 경우")
    void bulk_insert를_위한_쿼리_렌더링_테스트_컬럼값이_없는_경우() {

        // given
        QueryRenderer queryRenderer = new QueryRenderer();
        List<TableEntity<Person>> tableEntity = List.of();

        List<List<? extends TableField>> 벌크인서트용_컬럼들 = tableEntity.stream()
                .map(TableEntity::getAllFields)
                .collect(Collectors.toList());

        // when
        String query = queryRenderer.renderBulkInsertValues(벌크인서트용_컬럼들);

        // then
        assertThat(query).isEmpty();
    }

    @Test
    @DisplayName("컬럼명 리스트 렌더링 테스트")
    void 컬럼명_리스트_렌더링_테스트() {

        // given
        QueryRenderer queryRenderer = new QueryRenderer();
        TableEntity<Person> tableEntity = new TableEntity<>(new Person(1L, 30, "설동민"));

        // when
        String query = queryRenderer.joinColumnNamesWithComma(tableEntity.getAllFields());

        // then
        assertThat(query).isEqualTo("id,name,age");
    }

    @Test
    @DisplayName("컬럼명 & 값 pair 리스트 렌더링 테스트")
    void 컬럼명_과_값_페어리스트_렌더링_테스트() {

        // given
        QueryRenderer queryRenderer = new QueryRenderer();
        TableEntity<Person> tableEntity = new TableEntity<>(new Person(1L, 30, "설동민"));

        // when
        String query = queryRenderer.joinColumnAndValuePairWithComma(tableEntity.getNonIdFields());

        // then
        assertThat(query).isEqualTo("name='설동민',age=30");
    }

    @Test
    @DisplayName("조건절 렌더링 테스트")
    void 조건절_렌더링_테스트() {

        // given
        QueryRenderer queryRenderer = new QueryRenderer();
        List<Condition> conditionList = List.of(
                eq("id", 1L),
                eq("name", "설동민"),
                eq("age", 30)
        );

        // when
        String query = queryRenderer.renderWhere(new Conditions(conditionList));

        // then
        assertThat(query).isEqualTo(" WHERE id = 1 AND name = '설동민' AND age = 30");
    }
}
