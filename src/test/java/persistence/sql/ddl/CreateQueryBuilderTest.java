package persistence.sql.ddl;

import domain.Person;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryBuilder;
import persistence.sql.meta.Table;
import persistence.sql.dialect.h2.H2Dialect;

@DisplayName("CreateQueryBuilder class 의")
class CreateQueryBuilderTest {

    private QueryBuilder builder;

    @BeforeEach
    public void setup() {
        builder = CreateQueryBuilder.from(H2Dialect.getInstance());
    }


    @DisplayName("generateQuery 메서드는")
    @Nested
    class GenerateQuery {
        @DisplayName("Person Entity로 테이블 생성 ddl이 만들어지는지 확인한다.")
        @Test
        void testGenerateQuery_WhenPersonEntity_ThenGenerateDdl() {
            // given
            Class<?> clazz = Person.class;
            Table table = Table.from(Person.class);

            //when
            String ddl = builder.generateQuery(clazz);

            //then
            assertThat(ddl).isEqualTo("CREATE TABLE " + table.getTableName()+ " (id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR,old INTEGER,email VARCHAR NOT NULL)");
        }
    }
}
