package persistence.sql.ddl;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryException;
import persistence.sql.query.Query;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class DdlQueryBuilderTest {

    private DdlQueryBuilder testDdlQueryBuilder = new DdlQueryBuilder() {

        @Override
        public String buildCreateQuery(final Query query) {
            return null;
        }

        @Override
        public String buildDropQuery(final Query query) {
            return null;
        }
    };

    @DisplayName("Entity 의 class 를 이용해 Table, Column 이 포함된 Query 객체를 생성한다")
    @Test
    public void createQueryByEntity() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final String tableName = clazz.getAnnotation(Table.class).name();
        final long fieldsSize = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .count();

        // when
        final Query query = testDdlQueryBuilder.generateQuery(clazz);

        // then
        assertAll(
                () -> assertThat(query.getTableName()).isEqualTo(tableName),
                () -> assertThat(query.getColumns()).hasSize((int) fieldsSize)
        );
    }

    @DisplayName("Entity 가 아닌 객체의 class 로 Query 를 생성하려 하면 예외를 반환한다")
    @Test
    public void throwExceptionToGenerateQueryByNoEntity() throws Exception {
        // given
        final Class<PersonV0> clazz = PersonV0.class;

        // when then
        assertThatThrownBy(() -> testDdlQueryBuilder.generateQuery(clazz))
                .isInstanceOf(QueryException.class)
                .hasMessage("PersonV0 is not entity");
    }

}