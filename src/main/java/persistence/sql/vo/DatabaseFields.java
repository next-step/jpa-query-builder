package persistence.sql.vo;

import java.util.Collections;
import java.util.List;
import persistence.sql.ddl.exception.FieldShouldHaveOnlyOnePrimaryKeyException;
import persistence.sql.ddl.exception.FieldNotEnoughException;

public class DatabaseFields {
    private final List<DatabaseField> databaseFields;

    private DatabaseFields(List<DatabaseField> databaseFields) {
        this.databaseFields = validate(databaseFields);
    }

    public static DatabaseFields of(List<DatabaseField> databaseFields) {
        return new DatabaseFields(databaseFields);
    }

    public List<DatabaseField> getDatabaseFields() {
        return databaseFields;
    }

    private List<DatabaseField> validate(List<DatabaseField> databaseFields) {
        if(databaseFields == null || databaseFields.size() == 0) {
            throw new FieldNotEnoughException("필드가 충분하지 않습니다");
        }
        long primaryCount = databaseFields.stream().filter(DatabaseField::isPrimary).count();
        if(primaryCount != 1) {
            throw new FieldShouldHaveOnlyOnePrimaryKeyException("여러개의 pk가 정의되었습니다");
        }
        return Collections.unmodifiableList(databaseFields);
    }
}
