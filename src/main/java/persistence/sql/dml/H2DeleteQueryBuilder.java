package persistence.sql.dml;

import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.model.EntityColumnName;
import persistence.sql.model.EntityColumnNames;
import persistence.sql.model.EntityColumnValues;
import persistence.sql.model.TableName;

public class H2DeleteQueryBuilder implements DeleteQueryBuilder {

    private static final String SPACE = " ";
    private final Object object;

    public H2DeleteQueryBuilder(Object object) {
        ExceptionUtil.requireNonNull(object);
        this.object = object;
    }

    @Override
    public String delete() {
        TableName tableName = new TableName(object.getClass());
        StringBuilder deleteStringBuilder = new StringBuilder();
        deleteStringBuilder.append("DELETE FROM");
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append(tableName.getValue());
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append("WHERE");
        deleteStringBuilder.append(SPACE);
        deleteStringBuilder.append(new EntityColumnNames(object.getClass()).getIdColumnName().getValue());
        deleteStringBuilder.append("=");
        deleteStringBuilder.append(new EntityColumnValues(object).getIdFieldValue(new EntityColumnNames(object.getClass()).getIdColumnName().getValue()));
        return deleteStringBuilder.toString();
    }
}
