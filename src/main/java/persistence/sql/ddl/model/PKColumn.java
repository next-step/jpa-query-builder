package persistence.sql.ddl.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class PKColumn extends Column {
    private String strategy;

    public PKColumn(String name, Type type, Condition condition, String strategy) {
        super(name, type, condition);
        this.strategy = strategy;
    }

    public static PKColumn from(Field field) {
        var column = Arrays.stream(field.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(jakarta.persistence.Column.class))
                .map(annotation -> (jakarta.persistence.Column) annotation)
                .findFirst();

        var name = column.filter(value -> !value.name().isBlank())
                .map(jakarta.persistence.Column::name)
                .orElseGet(field::getName);

        var type = Type.from(field.getType());
        var condition = column.map(Condition::from)
                .orElse(Condition.DEFAULT_CONDITION);

        var generatedValueAnnotation = Arrays.stream(field.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(GeneratedValue.class))
                .map(annotation -> (jakarta.persistence.GeneratedValue) annotation)
                .findFirst();

        String strategy = "";
        if (generatedValueAnnotation.isPresent()) {
            var strategyValue = generatedValueAnnotation.get().strategy();
            if (strategyValue == GenerationType.IDENTITY) {
                strategy = "AUTO_INCREMENT";
            }
        }

        return new PKColumn(name, type, condition, strategy);
    }

    @Override
    public String getDDLColumnQuery() {
        var sb = new StringBuilder()
                .append(getName())
                .append(" ")
                .append(getType().name());

        if (!strategy.isBlank()) sb.append(" ").append(strategy);
        sb.append(" PRIMARY KEY");
        return sb.toString();
    }
}
