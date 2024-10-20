package orm.dsl;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static util.SQLUtil.SQL_노멀라이즈;

public class QueryBuilderDropTest {

    QueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        queryBuilder = new QueryBuilder();
    }

    @Test
    @DisplayName("DROP 절 생성 테스트")
    void DDL_DROP_절_테스트_1() {

        // given
        Class<DummyEntity> entityClass = DummyEntity.class;
        String expectedQuery = SQL_노멀라이즈(
                """
                        DROP TABLE test_table;
                        """
        );

        // when
        String query = SQL_노멀라이즈(
                queryBuilder.dropTable(entityClass)
                        .extractSql()
        );

        // then
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("DROP 절 생성 테스트 + IF NOT EXISTS 문 추가")
    void DDL_DROP_절_테스트_2() {

        // given
        Class<DummyEntity> entityClass = DummyEntity.class;
        String expectedQuery = SQL_노멀라이즈(
                """
                        DROP TABLE IF NOT EXISTS test_table;
                        """
        );

        // when
        String query = SQL_노멀라이즈(
                queryBuilder.dropTable(entityClass)
                        .ifNotExist()
                        .extractSql()
        );

        // then
        assertThat(query).isEqualTo(expectedQuery);
    }
}

@Entity
@Table(name = "test_table")
class DummyEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotated_string")
    private String col1;

    @Column(name = "annotated_length_string", length = 120)
    private String col2;

    @Column(length = 120)
    private String property1StrWithLength;

    @Column
    private String property1StrWithNo;

    private String property2NoAnnotation;
}
