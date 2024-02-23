package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.field.QueryFields;
import persistence.sql.ddl.field.TableField;

// note. 이름만 builder이지 builder 디자인 패턴을 칭하는 것 아님
public class CreateQueryBuilder {

    private final TableField tableField;
    private final QueryFields queryFields;

    private CreateQueryBuilder(Class<?> klass) {

        if (!klass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 존재하지 않습니다");
        }
        // todo :how hibernate did this?
        this.tableField = new TableField(klass.getSimpleName());
        this.queryFields = new QueryFields(klass.getDeclaredFields());
    }

    public String toSQL() {
        return String.join(System.lineSeparator(),
                tableField.toSQL() + " (",
                queryFields.toSQL(),
                ");"
        );
    }

    public static CreateQueryBuilder fromEntityAnnotatedClass(Class<?> klass) {
        return new CreateQueryBuilder(klass);
    }

}
