package persistence.sql.usecase;

import persistence.sql.vo.DatabaseField;
import persistence.sql.vo.DatabaseFields;

import java.util.List;
import java.util.stream.Collectors;

public class GetIdDatabaseFieldUseCase {
    private final GetFieldFromClassUseCase getFieldFromClassUseCase;

    public GetIdDatabaseFieldUseCase(GetFieldFromClassUseCase getFieldFromClassUseCase) {
        this.getFieldFromClassUseCase = getFieldFromClassUseCase;
    }

    public DatabaseField execute(Class<?> cls) {
        DatabaseFields databaseFields = getFieldFromClassUseCase.execute(cls);
        List<DatabaseField> primaryFields = databaseFields.getDatabaseFields().stream().filter(DatabaseField::isPrimary).collect(Collectors.toList());
        if (primaryFields.size() != 1) {
            throw new RuntimeException("Id should be only one");
        }
        return primaryFields.get(0);
    }
}
