package persistence.sql.ddl;

public class DatabaseAbstractFactoryImpl implements DatabaseAbstractFactory {

    @Override
    public H2QueryBuilder createH2Database() {
        return new H2QueryBuilderImpl();
    }
}
