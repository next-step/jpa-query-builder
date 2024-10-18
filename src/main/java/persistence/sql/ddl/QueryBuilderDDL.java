package persistence.sql.ddl;

import org.h2.util.StringUtils;
import persistence.sql.meta.ColumnField;
import persistence.sql.meta.ColumnFields;
import persistence.sql.meta.TableInfo;

import java.util.stream.Collectors;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    public static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        String createFormat = "create table %s (%s)";
        TableInfo tableInfo = new TableInfo(clazz);
        return String.format(createFormat, tableInfo.getTableName(), generateDdlQuery(clazz));
    }

    public String buildDropDdl(Class<?> clazz) {
        String dropFormat = "drop table if exists %s";
        TableInfo tableInfo = new TableInfo(clazz);
        return String.format(dropFormat, tableInfo.getTableName());
    }

    private String generatePrimaryKeyQuery(ColumnFields fields) {
        String primaryFormat = "primary key (%s)";
        return String.format(primaryFormat, fields.getPrimary().stream()
                .map(ColumnField::getName)
                .collect(Collectors.joining(", ")));
    }

    private String generateDdlQuery(Class<?> clazz) {
        ColumnFields fields = new ColumnFields(clazz);
        String columnQuery = fields.getColumnFields().stream()
                .map(this::generateColumnDdlQuery)
                .collect(Collectors.joining(", "));
        String primaryQuery = generatePrimaryKeyQuery(fields);
        return String.join(", ", columnQuery, primaryQuery);
    }

    private String generateColumnDdlQuery(ColumnField columnInfo) {
        String columnQuery = String.join(" ", columnInfo.getName(), columnInfo.getColumnType().getQueryDefinition());
        String optionQuery = columnInfo.getOptions().stream().collect(Collectors.joining(" "));
        if(StringUtils.isNullOrEmpty(optionQuery)) {
            return columnQuery;
        }
        return String.join(" ", columnQuery, optionQuery);
    }
}
