package orm.dsl.sql_dialect.h2;

import orm.TableEntity;
import orm.dsl.QueryExecutor;
import orm.dsl.ddl.DropTableImpl;

import java.util.StringJoiner;

public class H2DropTableImpl<ENTITY> extends DropTableImpl<ENTITY> {

    public H2DropTableImpl(TableEntity<ENTITY> tableEntity, QueryExecutor queryExecutor) {
        super(tableEntity, queryExecutor);
    }

    @Override
    public String build() {
        final var stringJoiner = new StringJoiner(" ");
        stringJoiner.add("DROP TABLE");
        if (super.ifNotExist) {
            stringJoiner.add(renderIfNotExist());
        }
        stringJoiner.add(super.tableEntity.getTableName() + ";");
        return stringJoiner.toString();
    }
}
