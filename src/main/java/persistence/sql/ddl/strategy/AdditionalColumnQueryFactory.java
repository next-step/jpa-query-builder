package persistence.sql.ddl.strategy;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdditionalColumnQueryFactory {

    private AdditionalColumnQueryFactory() {
    }

    public static List<AdditionalColumQueryStrategy> getStrategies(Field field) {
        Reflections reflections = new Reflections("persistence.sql.ddl.strategy", new SubTypesScanner(false));

        Set<Class<? extends AdditionalColumQueryStrategy>> classes = reflections.getSubTypesOf(AdditionalColumQueryStrategy.class);

        return classes.stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(AdditionalColumnQueryFactory::instantiateStrategy)
                .filter(strategy -> strategy.isRequired(field))
                .collect(Collectors.toList());
    }

    private static AdditionalColumQueryStrategy instantiateStrategy(Class<? extends AdditionalColumQueryStrategy> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
