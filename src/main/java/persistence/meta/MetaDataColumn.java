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
  private final String field;
  private final List<MetaDataColumnConstraint> constraints;

  private MetaDataColumn(String name, String type, String field, List<MetaDataColumnConstraint> constraints) {
    this.name = name;
    this.type = type;
    this.field = field;
    this.constraints = constraints;
  }

  public static MetaDataColumn of(Field field, String columnType) {
    List<MetaDataColumnConstraint> constraints = Arrays.stream(field.getAnnotations())
            .map(MetaDataColumnConstraint::of)
            .sorted(Comparator.comparing(MetaDataColumnConstraint::getConstraint).reversed())
            .collect(Collectors.toList());

    return new MetaDataColumn(setColumnName(field), columnType, field.getName(), constraints);
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

  private static String setColumnName(Field field) {

    if (field.isAnnotationPresent(Column.class)
            && !field.getAnnotation(Column.class).name().isEmpty()) {
      return field.getAnnotation(Column.class).name();
    }

    return field.getName().toLowerCase();
  }

  public String getSimpleName() {
    return name;
  }

  public boolean isNotPrimaryKey() {
    return !constraints.stream().anyMatch(MetaDataColumnConstraint::isPrimaryKey);
  }

  public String getFieldName(){
    return field;
  }
}
