package persistence.meta;

import jakarta.persistence.Transient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetaDataColumn {
  public static final String SPACE = " ";
  private static final String COMMA = ",";
  private final String name;
  private final String type;
  private final List<MetaDataColumnConstraint> constraints;

  private MetaDataColumn(String name, String type, List<MetaDataColumnConstraint> constraints) {
    this.name = name;
    this.type = type;
    this.constraints = constraints;
  }

  public static MetaDataColumn of(Field field, String columnType){
    List<MetaDataColumnConstraint> constraints = Arrays.stream(field.getAnnotations())
            .filter(MetaDataColumn::isNotTransient)
            .map(MetaDataColumnConstraint::of)
            .collect(Collectors.toList());
    return new MetaDataColumn(field.getName(), columnType, constraints);
  }
  public String getColumn(){
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(SPACE);
    sb.append(type);
    sb.append(" ");

    for (MetaDataColumnConstraint constraint : constraints){
      sb.append(constraint.getConstraint());
      sb.append(" ");
    }
    sb.append(COMMA);
    return sb.toString();
  }
  private static boolean isNotTransient(Annotation annotation) {
    return !annotation.annotationType().equals(Transient.class);
  }

}
