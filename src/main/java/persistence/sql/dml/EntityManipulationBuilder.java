package persistence.sql.dml;

import persistence.sql.ddl.EntityMetadata;
import utils.CustomStringBuilder;

import static persistence.sql.dml.DataLanguage.*;

public class EntityManipulationBuilder {

    private final EntityMetadata entityMetadata;

    public EntityManipulationBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String insert(Object entity) {
        return new CustomStringBuilder()
                .append(columnsClause(entity))
                .append(valueClause(entity))
                .toString();
    }

    private String columnsClause(Object entity) {
        // TODO 포캣 잡아주는 부분 리팩토링
        return new CustomStringBuilder()
                .append(INSERT.getName())
                .append(entityMetadata.getTableName())
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(entityMetadata.getColumnNames(entity))
                .append(RIGHT_PARENTHESIS.getName())
                .toStringWithoutSpace();

    }

    private String valueClause(Object entity) {
        return new CustomStringBuilder()
                .append(VALUES.getName())
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(entityMetadata.getValueFrom(entity))
                .appendWithoutSpace(RIGHT_PARENTHESIS.getName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public String findAll(Class<?> type) {
        return new CustomStringBuilder()
                .append(SELECT.getName())
                .append(entityMetadata.getColumnNames(type))
                .append(FROM.getName())
                .appendWithoutSpace(entityMetadata.getTableName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public String findById(Class<?> type, long id) {
        return new CustomStringBuilder()
                .append(SELECT.getName())
                .append(entityMetadata.getColumnNames(type))
                .append(FROM.getName())
                .append(entityMetadata.getTableName())
                .append(WHERE.getName())
                .append(entityMetadata.getIdColumnName(type))
                .append(EQUALS.getName())
                .appendWithoutSpace(String.valueOf(id))
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }
}
