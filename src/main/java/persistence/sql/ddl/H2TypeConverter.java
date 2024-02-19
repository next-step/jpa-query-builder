package persistence.sql.ddl;

public class H2TypeConverter implements TypeConverter {

    @Override
    public String convert(Class<?> type) {
        return H2Type.converter(type);
    }
}
