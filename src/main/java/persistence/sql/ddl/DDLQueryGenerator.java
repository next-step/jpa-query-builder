package persistence.sql.ddl;

import static persistence.sql.Constant.EMPTY;
import static persistence.sql.Constant.NOT_NULL;
import static persistence.sql.Constant.WHITE_SPACE;

import java.util.List;
import persistence.meta.vo.EntityField;
import persistence.meta.vo.EntityId;
import persistence.meta.vo.EntityMetaData;
import persistence.meta.vo.TableName;
import persistence.sql.dialect.Dialect;

public class DDLQueryGenerator {
    private final Dialect dialect;

    public DDLQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateCreateTableQuery(Class<?> cls) {
        EntityMetaData entityMetaData = EntityMetaData.createFromClass(cls);
        TableName tableName = entityMetaData.getTableName();
        List<EntityField> entityFields = entityMetaData.getEntityFields();
        EntityId entityId = entityMetaData.getEntityId();
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName.getTableName());
        sb.append("(");
        sb.append(getIdClause(entityId));
        sb.append(",");
        for (EntityField entityField : entityFields) {
            fillEntityFieldInCreateTableSQL(entityField, sb);
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }

    public String generateDropTableQuery(Class<?> cls) {
        TableName tableName = EntityMetaData.createFromClass(cls).getTableName();
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ").append(tableName.getTableName()).append(" if exists;");
        return sb.toString();
    }

    private void fillEntityFieldInCreateTableSQL(EntityField entityField, StringBuilder sb) {
        sb.append(" ");
        sb.append(getFieldClause(entityField));
        sb.append(",");
    }

    private String getFieldClause(EntityField entityField) {
        return entityField.getTableFieldName() + " " + dialect.getFieldType(entityField) + (entityField.isNullable()
            ? EMPTY : WHITE_SPACE + NOT_NULL);
    }

    private String getIdClause(EntityId entityId) {
        return
            entityId.getTableFieldName() + " " + dialect.getFieldType(entityId.getEntityField())
                + dialect.getGenerationTypeSql(entityId.getGeneratedValue());
    }
}
