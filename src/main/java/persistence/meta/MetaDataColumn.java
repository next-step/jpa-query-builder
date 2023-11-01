package persistence.meta;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MetaDataColumn {
  public static final String SPACE = " ";
  private static final String COLUMN_DATA_TYPE = "%s %s";
  private final String name;
  private final String type;
  private final String fieldName;
  private final Field field;
  private final List<MetaDataColumnConstraint> constraints;

  private MetaDataColumn(String name, String type, String fieldName, Field field, List<MetaDataColumnConstraint> constraints) {
    this.name = name;
    this.type = type;
    this.fieldName = fieldName;
    this.field = field;
    this.constraints = constraints;
  }

  public static MetaDataColumn of(Field field, String columnType) {
    List<MetaDataColumnConstraint> constraints = Arrays.stream(field.getAnnotations())
            .map(MetaDataColumnConstraint::of)
            .sorted(Comparator.comparing(MetaDataColumnConstraint::getConstraint).reversed())
            .collect(Collectors.toList());

    return new MetaDataColumn(getColumnName(field), columnType, field.getName(), field, constraints);
  }

  public String getColumn() {

    StringBuilder sb = new StringBuilder();
    sb.append(String.format(COLUMN_DATA_TYPE, name, type));
    sb.append(SPACE);
    sb.append(constraints.stream()
            .map(MetaDataColumnConstraint::getConstraint)
            .collect(Collectors.joining(SPACE)));

    return sb.toString();
  }

  private static String getColumnName(Field field) {

    if (field.isAnnotationPresent(Column.class)
            && !field.getAnnotation(Column.class).name().isEmpty()) {
      return field.getAnnotation(Column.class).name();
    }

    return field.getName().toLowerCase();
  }

  public Object getFieldValue(Object entity) {
    try {
      field.setAccessible(true);
      Object value = field.get(entity);
      if (value.getClass().equals(String.class)) {
        return "'" + value.toString() + "'";
      }
      field.setAccessible(false);
      return value;
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public String getSimpleName() {
    return name;
  }

  public boolean isNotPrimaryKey() {
    return constraints.stream().noneMatch(MetaDataColumnConstraint::isPrimaryKey);
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldValue(Object instance, Object value) {
    field.setAccessible(true);
    try {
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    field.setAccessible(false);
  }
}
