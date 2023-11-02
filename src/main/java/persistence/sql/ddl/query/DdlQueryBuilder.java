package persistence.sql.ddl.query;

import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.IdentityConstraint;
import persistence.sql.ddl.constraint.NotNullConstraint;
import persistence.sql.ddl.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.List;

public class DdlQueryBuilder {

    private final ColumnQueryBuilder columnQueryBuilder;

    private static final String CREATE_QUERY_FORMAT = "create table %s (%s)";
    private static final String DROP_QUERY_FORMAT = "drop table %s";

    private DdlQueryBuilder() {
        columnQueryBuilder = new ColumnQueryBuilder(generateConstraints());
    }


    public String createTable(EntityMetaData entityMetaData) {
        return String.format(CREATE_QUERY_FORMAT, entityMetaData.getTableName(), createColumnsDdl(entityMetaData));
    }

    public String dropTable(EntityMetaData entityMetaData) {
        return String.format(DROP_QUERY_FORMAT, entityMetaData.getTableName());
    }

    public String createColumnsDdl(EntityMetaData entityMetaData) {
        String idColumnsQuery = String.join(",", columnQueryBuilder.generateDdlQueryRows(entityMetaData.getIdColumns()));
        String fieldColumnsQuery = String.join(", ", columnQueryBuilder.generateDdlQueryRows(entityMetaData.getFieldColumns()));
        return String.format("%s, %s" , idColumnsQuery , fieldColumnsQuery);
    }

    private List<Constraint> generateConstraints() {
        final List<Constraint> constraints = new ArrayList<>();
        constraints.add(new NotNullConstraint());
        constraints.add(new IdentityConstraint());
        constraints.add(new PrimaryKeyConstraint());
        return constraints;
    }

    public static DdlQueryBuilder getInstance() {
        return DdlQueryBuilder.DdlQueryBuilderHolder.INSTANCE;
    }

    private static class DdlQueryBuilderHolder {
        private static final DdlQueryBuilder INSTANCE = new DdlQueryBuilder();
    }

}
