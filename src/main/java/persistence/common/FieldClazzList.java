package persistence.common;

import persistence.annotations.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldClazzList {
    private final List<FieldClazz> fieldClazzList;

    public FieldClazzList(Class<?> clazz) {
        this.fieldClazzList = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(FieldClazz::new)
                .collect(Collectors.toList());
    }

    public List<FieldClazz> getIdFieldList() {
        return fieldClazzList.stream().filter(fc -> fc.isId())
                .collect(Collectors.toList());
    }

    public List<FieldClazz> getFieldClazzList() {
        return fieldClazzList;
    }
}
