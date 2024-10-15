package persistence.sql.dml;

import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.model.EntityColumnNames;
import persistence.sql.model.EntityColumnValues;
import persistence.sql.model.TableName;

public class H2DeleteQueryBuilder implements DeleteQueryBuilder {

    private static final String SPACE = " ";
    private final Object object;
    private final Class<?> clazz;

    public H2DeleteQueryBuilder(Object object) {
        ExceptionUtil.requireNonNull(object);

        if (object instanceof Class) {
            throw new IllegalArgumentException("잘못된 Object 타입입니다.");
        }

        this.clazz = object.getClass();
        this.object = object;
    }

    @Override
    public String delete() {
        TableName tableName = new TableName(clazz);

        EntityColumnValues entityColumnValues = new EntityColumnValues(object);
        String id = entityColumnValues.getField("id");
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
