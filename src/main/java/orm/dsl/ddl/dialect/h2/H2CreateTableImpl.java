package orm.dsl.ddl.dialect.h2;

import orm.TableEntity;
import orm.TableField;
import orm.dsl.ddl.CreateTableImpl;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class H2CreateTableImpl<ENTITY> extends CreateTableImpl<ENTITY> {

    public H2CreateTableImpl(TableEntity<ENTITY> tableEntity) {
        super(tableEntity);
    }

    @Override
    public String buildQuery() {
        final var stringJoiner = new StringJoiner(" ");
        stringJoiner.add("CREATE TABLE");
        if (super.ifNotExist) {
            stringJoiner.add(renderIfNotExist());
        }
        stringJoiner.add(super.tableEntity.getTableName());
        stringJoiner.add("(%s,PRIMARY KEY (%s));".formatted(renderAllColumns(), tableEntity.getId().getFieldName()));

        return stringJoiner.toString();
    }

    private String renderAllColumns() {
        return tableEntity.getAllFields().stream()
                .map(this::renderColumn)
                .collect(Collectors.joining(","));
    }

    private String renderColumn(TableField tableField) {
        final String rdbType = columnTypeMapper.javaTypeToRDBType(tableField.getFieldType(), tableField.getColumnMeta());
        return tableField.isId()
                ? super.renderPrimaryKeyColumns(rdbType)
                : super.renderColumns(tableField, rdbType);
    }
}
