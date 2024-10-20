package persistence.sql.dml;

import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredObjectException;
import persistence.sql.model.EntityColumnNames;
import persistence.sql.model.EntityColumnValues;
import persistence.sql.model.TableName;

public class DeleteQuery {

    private static final String SPACE = " ";
    private final Object object;
    private final Class<?> clazz;

    public DeleteQuery(Object object) {
        if (object == null) {
            throw new RequiredObjectException(ExceptionMessage.REQUIRED_OBJECT);
        }

        if (object instanceof Class) {
            throw new IllegalArgumentException("잘못된 Object 타입입니다.");
        }

        this.clazz = object.getClass();
        this.object = object;
    }

    public String makeQuery() {
        TableName tableName = new TableName(clazz);

        StringBuilder deleteStringBuilder = new StringBuilder();
        deleteStringBuilder.append("DELETE FROM");
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append(tableName.getValue());
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append("WHERE");
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append(new EntityColumnNames(clazz).getIdColumnName().getValue());
        deleteStringBuilder.append("=");
        deleteStringBuilder.append(new EntityColumnValues(object).getField(new EntityColumnNames(clazz).getIdColumnName().getValue()));
        return deleteStringBuilder.toString();
    }
}
