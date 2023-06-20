package persistence.sql.ddl.builder;

import fixture.PersonV1;
import fixture.PersonV2;
import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.column.DdlColumn;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnBuilderTest {

    @Test
    @DisplayName("기본적인 column 쿼리 만들기")
    public void create_column() {
        ColumnBuilder columnBuilder = new ColumnBuilder(DdlColumn.ofList(PersonV1.class));

        assertThat(columnBuilder.build()).isEqualToIgnoringCase(
                "id bigint primary key, name varchar(255), age integer"
        );
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 column 쿼리 만들기 (@GeneratedValue, @Column)")
    public void created_ddl_2() {
        ColumnBuilder columnBuilder = new ColumnBuilder(DdlColumn.ofList(PersonV2.class));

        assertThat(columnBuilder.build()).isEqualToIgnoringCase(
                "id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 column 쿼리 만들기 (@Transient)")
    public void created_ddl_3() {
        ColumnBuilder columnBuilder = new ColumnBuilder(DdlColumn.ofList(PersonV3.class));

        assertThat(columnBuilder.build()).isEqualToIgnoringCase(
                "id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null"
        );
    }
}