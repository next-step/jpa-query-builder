package persistence.sql;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface ColumnDefinitionAware {

    String name();

    String declaredName();

}
