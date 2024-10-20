package persistence.sql.ddl.query;

import static persistence.validator.AnnotationValidator.notIdentifier;
import static persistence.validator.AnnotationValidator.notPredicate;

import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import persistence.sql.metadata.Identifier;
import persistence.sql.metadata.TableName;

public record CreateQuery(TableName tableName,
                          List<ColumnMeta> columns,
                          Identifier identifier) {

    public CreateQuery(Class<?> clazz) {
        this(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(notIdentifier())
                        .filter(notPredicate(Transient.class))
                        .map(ColumnMeta::new)
                        .toList(),
                Identifier.from(clazz.getDeclaredFields())
        );
    }

}
