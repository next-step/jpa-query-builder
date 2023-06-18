package persistence.sql.ddl.column.option;

import java.lang.reflect.Field;

public class NoneOptionStrategy implements OptionStrategy {

    public static final OptionStrategy INSTANCE = new NoneOptionStrategy();
    private static final String EMPTY = "";

    private NoneOptionStrategy() {
    }

    @Override
    public Boolean supports(Field field) {
        return false;
    }

    @Override
    public String options(Field field) {
        return EMPTY;
    }
}
