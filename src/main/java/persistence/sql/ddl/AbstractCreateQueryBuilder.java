package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.model.TableName;

public abstract class AbstractCreateQueryBuilder implements CreateQueryBuilder {
    protected final Class<?> clazz;
    private static final String SPACE = " ";

    public AbstractCreateQueryBuilder(Class<?> clazz) {
        ExceptionUtil.requireNonNull(clazz);

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }

        this.clazz = clazz;
    }

    protected String createTableStatement() {
        TableName tableName = new TableName(clazz);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE");
        stringBuilder.append(SPACE);
        stringBuilder.append(tableName.getValue());

        return "CREATE TABLE IF NOT EXISTS " + tableName.getValue();
    }
    protected abstract String generateColumnDefinitions();

    @Override
    public String makeQuery() {
        return createTableStatement() + generateColumnDefinitions();
    }
}
