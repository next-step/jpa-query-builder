package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person1;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.model.DomainType;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnBuilderTest {

    private DomainType domainType;

    @BeforeEach
    void setUp() throws Exception{
        this.domainType = new DomainType("name", String.class, Person1.class.getDeclaredField("name"));
    }

    @DisplayName("DB에 컬럼 저장할 쿼리문을 반환한다.")
    @Test
    void getColumnSql() {
        ColumnBuilder columnBuilder = new ColumnBuilder(domainType, H2TypeMapper.newInstance());

        assertThat(columnBuilder.build()).isEqualTo("name VARCHAR");

    }

}