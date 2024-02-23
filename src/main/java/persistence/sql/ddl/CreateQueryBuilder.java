package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.field.QueryFields;

// note. 이름만 builder이지 builder 디자인 패턴을 칭하는 것 아님
public class CreateQueryBuilder {

    private static final String DDL_CREATE_FORMAT = "CREATE TABLE %s (\n%s\n);";

    private final String tableName;
    private final QueryFields queryFields;

    private CreateQueryBuilder(Class<?> klass) {

        if (!klass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 존재하지 않습니다");
        }
        // todo : Hibernate는 어떻게 Entity annotated class로부터 구현하고 있나?
        this.tableName = klass.getSimpleName();
        this.queryFields = new QueryFields(klass.getDeclaredFields());
    }

    public static CreateQueryBuilder from(Class<?> klass) {
        return new CreateQueryBuilder(klass);
    }

    public String toSQL() {
        return String.format(DDL_CREATE_FORMAT, tableName, queryFields.toSQL());
    }


}
