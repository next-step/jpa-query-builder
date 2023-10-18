package persistence.sql.ddl.generator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.ddl.ColumnAnnotationHelper;
import persistence.sql.ddl.dialect.ColumnType;
import persistence.sql.ddl.exception.RequiredAnnotationException;
import persistence.sql.ddl.schema.ColumnMeta;
import persistence.sql.ddl.schema.TableMeta;

public class CreateDDLQueryGenerator {

    private final ColumnType columnType;
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    public static final String CREATE_TABLE_FORMAT = "CREATE TABLE %s (%s);";

    public CreateDDLQueryGenerator(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String create(Class<?> entityClazz) {
        return String.format(CREATE_TABLE_FORMAT,
            TableMeta.of(entityClazz).getTableName(),
            appendField(entityClazz)
        );
    }

    private String appendField(Class<?> entityClazz) {
        final List<Field> createDDlTargetFieldList = Arrays.stream(entityClazz.getDeclaredFields())
            .filter(field -> !ColumnAnnotationHelper.isTransient(field))
            .collect(Collectors.toList());

        validateHasIdAnnotation(createDDlTargetFieldList);

        return createDDlTargetFieldList.stream()
            .map(targetField -> ColumnMeta.of(targetField, columnType).getColumn())
            .collect(Collectors.joining(COMMA + SPACE));
    }

    private static void validateHasIdAnnotation(List<Field> targetFieldList) {
        final boolean hasIdAnnotation = targetFieldList.stream().anyMatch(ColumnAnnotationHelper::isPrimaryKey);
        if (!hasIdAnnotation) {
            throw new RequiredAnnotationException("@Id must be contained in entity");
        }
    }
}
