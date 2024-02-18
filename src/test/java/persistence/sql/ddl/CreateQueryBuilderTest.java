package persistence.sql.ddl;

import domain.Person;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.wrapper.Table;

class CreateQueryBuilderTest {

    private QueryBuilder builder;

    @BeforeEach
    public void setup() {
        builder = CreateQueryBuilder.getInstance();
    }


    @DisplayName("Person Entity로 테이블 생성 ddl이 만들어지는지 확인한다.")
    @Test
    void testBuilder_WhenPersonEntity_ThenGenerateDdl() {
        // given
        Class<?> clazz = Person.class;
        Table table = Table.of(Person.class);

        //when
        String ddl = builder.builder(clazz);

        //then
        assertThat(ddl).isEqualTo("CREATE TABLE " + table.getTableName()+ " (id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR,old INTEGER,email VARCHAR NOT NULL)");
    }
}
