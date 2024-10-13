package orm.dsl.ddl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static util.SQLUtil.SQL_노멀라이즈;

public class DDLCreateQueryBuilderTest {

    @Test
    @DisplayName("CREATE 문 생성 테스트")
    void DDL_CREATE_문_테스트_1() {

        // given
        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder();
        Class<DummyEntity> entityClass = DummyEntity.class;
        String expectedQuery = SQL_노멀라이즈(
                """
                        CREATE TABLE test_table (
                            id BIGINT AUTO_INCREMENT,
                            annotated_string VARCHAR(255),
                            annotated_length_string VARCHAR(120),
                            property1str_with_length VARCHAR(120),
                            property1str_with_no VARCHAR(255),
                            property2no_annotation VARCHAR(255),
                            PRIMARY KEY (id)
                        );
                        """
        );

        // when
        String query = SQL_노멀라이즈(
                ddlQueryBuilder.createTable(entityClass)
                        .buildQuery()
        );

        // then
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("CREATE 문 생성 테스트 + IF NOT EXISTS 문 추가")
    void DDL_CREATE_문_테스트_2() {

        // given
        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder();
        Class<DummyEntity> entityClass = DummyEntity.class;
        String expectedQuery = SQL_노멀라이즈(
                """
                        CREATE TABLE IF NOT EXISTS test_table (
                            id BIGINT AUTO_INCREMENT,
                            annotated_string VARCHAR(255),
                            annotated_length_string VARCHAR(120),
                            property1str_with_length VARCHAR(120),
                            property1str_with_no VARCHAR(255),
                            property2no_annotation VARCHAR(255),
                            PRIMARY KEY (id)
                        );
                        """
        );

        // when
        String query = SQL_노멀라이즈(
                ddlQueryBuilder.createTable(entityClass)
                        .ifNotExist()
                        .buildQuery()
        );

        // then
        assertThat(query).isEqualTo(expectedQuery);
    }
}

@Entity
@Table(name = "test_table")
class DummyEntity {

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
