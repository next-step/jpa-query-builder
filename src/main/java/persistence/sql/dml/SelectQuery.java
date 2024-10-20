package persistence.sql.dml;

import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.model.EntityColumnNames;
import persistence.sql.model.TableName;

public class SelectQuery {

    private static final String SPACE = " ";

    private final Class<?> clazz;

    public SelectQuery(Class<?> clazz) {
        if (clazz == null) {
            throw new RequiredClassException(ExceptionMessage.REQUIRED_CLASS);
        }
        this.clazz = clazz;
    }

    public String findAll() {
        TableName tableName = new TableName(clazz);
        EntityColumnNames entityColumnNames = new EntityColumnNames(clazz);
        return String.format("SELECT %s FROM %s", entityColumnNames.getColumnNames(), tableName.getValue());
    }


    public String findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id가 존재하지 않습니다.");
        }
        TableName tableName = new TableName(clazz);
        EntityColumnNames entityColumnNames = new EntityColumnNames(clazz);

        StringBuilder findByIdQueryStringBuilder = new StringBuilder();
        findByIdQueryStringBuilder.append("SELECT");
        findByIdQueryStringBuilder.append(SPACE);
        findByIdQueryStringBuilder.append(entityColumnNames.getColumnNames());
        findByIdQueryStringBuilder.append(SPACE);
        findByIdQueryStringBuilder.append("FROM");
        findByIdQueryStringBuilder.append(SPACE);
        findByIdQueryStringBuilder.append(tableName.getValue());
        findByIdQueryStringBuilder.append(SPACE);
        findByIdQueryStringBuilder.append("WHERE");
        findByIdQueryStringBuilder.append(" ");
        findByIdQueryStringBuilder.append("id=");
        findByIdQueryStringBuilder.append(id);
        return findByIdQueryStringBuilder.toString();
    }

}
