package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.component.EntityData;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String SPACE = " ";
    private static final String CREATE_TABLE = "create table";

    private final EntityData entityData;

    public CreateQueryBuilder(Class<?> objectClass) {
        validateObject(objectClass);
        this.entityData = new EntityData(objectClass);
    }

    private void validateObject(Class<?> objectClass) {
        if (!objectClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해 테이블을 생성할 수 있습니다.");
        }
    }

    @Override
    public String getQuery() {
        return CREATE_TABLE + SPACE +
                entityData.getTableName() + SPACE +
                "(id bigint not null, age integer, name varchar(255), primary key (id))";
    }

}
