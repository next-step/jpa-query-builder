package persistence.sql.mapping;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryException;
import persistence.sql.ddl.PersonV0;
import persistence.sql.ddl.PersonV3;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableBinderTest {

    private final ColumnTypeMapper columnTypeMapper = mock(ColumnTypeMapper.class);

    private final TableBinder tableBinder = new TableBinder(columnTypeMapper);

    @DisplayName("Entity 의 class 를 이용해 able 객체를 생성한다")
    @Test
    public void createTableByEntity() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final String tableName = clazz.getAnnotation(Table.class).name();
        final long fieldsSize = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .count();

        when(columnTypeMapper.toSqlType(any(Type.class))).thenReturn(Types.VARCHAR);

        // when
        final persistence.sql.mapping.Table result = tableBinder.createTable(clazz);

        // then
        assertAll(
                () -> assertThat(result.getName()).isEqualTo(tableName),
                () -> assertThat(result.getColumns()).hasSize((int) fieldsSize)
        );
    }

    @DisplayName("Entity 가 아닌 객체의 class 로 Table 를 생성하려 하면 예외를 반환한다")
    @Test
    public void throwExceptionToGenerateTableByNoEntity() throws Exception {
        // given
        final Class<PersonV0> clazz = PersonV0.class;

        // when then
        assertThatThrownBy(() -> tableBinder.createTable(clazz))
                .isInstanceOf(QueryException.class)
                .hasMessage("PersonV0 is not entity");
    }


}
