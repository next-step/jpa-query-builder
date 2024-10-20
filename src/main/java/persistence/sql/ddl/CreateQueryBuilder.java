package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.model.TableName;

public class CreateQueryBuilder implements QueryBuilder {
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String SPACE = " ";

    private final Class<?> clazz;
    private final Dialect dialect;

    public CreateQueryBuilder(Class<?> clazz, Dialect dialect) {
        if (clazz == null) {
            throw new RequiredClassException(ExceptionMessage.REQUIRED_CLASS);
        }

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }

        this.clazz = clazz;
        this.dialect = dialect;
    }

    @Override
    public String build() {
        StringBuilder makeStringBuilder = new StringBuilder();
        makeStringBuilder.append(createTableIfNotExistsStatement());
        makeStringBuilder.append(generateColumnDefinitions());
        return makeStringBuilder.toString();
    }

    private String createTableIfNotExistsStatement() {
        TableName tableName = new TableName(clazz);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS");
        stringBuilder.append(SPACE);
        stringBuilder.append(tableName.getValue());

        return stringBuilder.toString();
    }

    private String generateColumnDefinitions() {
        DDLColumn ddlColumns = new DDLColumn(this.clazz.getDeclaredFields(), dialect);

        StringBuilder columnDefinitionStringBuilder = new StringBuilder();
        columnDefinitionStringBuilder.append(LEFT_PARENTHESIS);
        columnDefinitionStringBuilder.append(ddlColumns.makeColumnsDDL());
        columnDefinitionStringBuilder.append(RIGHT_PARENTHESIS);
        return columnDefinitionStringBuilder.toString();
    }


}
