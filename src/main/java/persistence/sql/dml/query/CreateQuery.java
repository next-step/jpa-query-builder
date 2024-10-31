package persistence.sql.dml.query;

import jakarta.persistence.Transient;
import persistence.metadata.ColumnMeta;
import persistence.metadata.Identifier;
import persistence.metadata.TableName;

import java.util.Arrays;
import java.util.List;

import static common.AnnotationValidation.notIdentifier;
import static common.AnnotationValidation.notPredicate;

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
