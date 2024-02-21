package persistence.sql.ddl.converter;

import persistence.sql.ddl.model.H2Type;

public class H2TypeConverter implements TypeConverter {

    @Override
    public String convert(Class<?> type) {
        return H2Type.converter(type);
    }
}
