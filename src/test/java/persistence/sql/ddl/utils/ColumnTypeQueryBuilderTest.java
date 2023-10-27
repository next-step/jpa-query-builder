package persistence.sql.ddl.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.IdentityConstraint;
import persistence.sql.ddl.constraint.NotNullConstraint;
import persistence.sql.ddl.constraint.PrimaryKeyConstraint;
import persistence.sql.ddl.query.ColumnQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnTypeQueryBuilderTest {

    @BeforeEach
    void setUp() {

    }
    @Test
    @DisplayName("컬럼 행별 ddl쿼리를 추출한다.")
    void query() {
        EntityMetaData entityMetaData = new EntityMetaData(Person.class);
        ColumnQueryBuilder columnQueryBuilder = new ColumnQueryBuilder(generateConstraints());
        List<String> fields = columnQueryBuilder.generateDdlQueryRows(entityMetaData.getFieldColumns());
        List<String> ids = columnQueryBuilder.generateDdlQueryRows(entityMetaData.getIdColumns());
        fields.addAll(ids);
        assertThat(fields).containsOnly("email VARCHAR(255) not null",
                "old INTEGER",
                "nick_name VARCHAR(255)",
                "id BIGINT auto_increment primary key"
        );
    }

    private List<Constraint> generateConstraints() {
        final List<Constraint> constraints = new ArrayList<>();
        constraints.add(new NotNullConstraint());
        constraints.add(new IdentityConstraint());
        constraints.add(new PrimaryKeyConstraint());
        return constraints;
    }
}