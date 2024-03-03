package persistence.sql.ddl;

import java.util.List;
import persistence.meta.service.ClassMetaData;
import persistence.meta.vo.EntityField;
import persistence.meta.vo.EntityId;
import persistence.meta.vo.EntityMetaData;
import persistence.meta.vo.TableName;
import persistence.sql.dialect.Dialect;

public class DDLQueryGenerator {
    private final Dialect dialect;
    private final ClassMetaData classMetaData;

    public DDLQueryGenerator(Dialect dialect, ClassMetaData classMetaData) {
        this.dialect = dialect;
        this.classMetaData = classMetaData;
    }

    public String generateCreateTableQuery(Class<?> cls) {
        EntityMetaData entityMetaData = classMetaData.getEntityMetaData(cls);
        TableName tableName = entityMetaData.getTableName();
        List<EntityField> entityFields = entityMetaData.getEntityFields();
        EntityId entityId = entityMetaData.getEntityId();
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName.getTableName());
        sb.append("(");
        String idClause = entityId.getEntityField().getTableFieldName() + " " + dialect.getFieldType(entityId.getEntityField());
        sb.append(idClause);
        sb.append(",");
        for (EntityField entityField : entityFields) {
            sb.append(" ");
            String fieldClause = entityField.getTableFieldName() + " " + dialect.getFieldType(entityField);
            sb.append(fieldClause);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }

    public String generateDropTableQuery(Class<?> cls) {
        TableName tableName = classMetaData.getTableName(cls);
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ").append(tableName.getTableName()).append(" if exists;");
        return sb.toString();
    }
}
