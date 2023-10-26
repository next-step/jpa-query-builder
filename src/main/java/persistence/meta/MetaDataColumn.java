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
  private final List<MetaDataColumnConstraint> constraints;

  private MetaDataColumn(String name, String type, List<MetaDataColumnConstraint> constraints) {
    this.name = name;
    this.type = type;
    this.constraints = constraints;
  }

  public static MetaDataColumn of(Field field, String columnType){
    List<MetaDataColumnConstraint> constraints = Arrays.stream(field.getAnnotations())
            .map(MetaDataColumnConstraint::of)
            .sorted(Comparator.comparing(MetaDataColumnConstraint::getConstraint).reversed())
            .collect(Collectors.toList());

    String name = setColumnName(field);

    return new MetaDataColumn(name, columnType, constraints);
  }
  public String getColumn(){

    StringBuilder sb = new StringBuilder();
    sb.append(String.format(COLUMN_DATA_TYPE, name,type));

    for (MetaDataColumnConstraint constraint : constraints){
      sb.append(SPACE);
      sb.append(constraint.getConstraint());
    }

    return sb.toString();
  }

  private static String setColumnName(Field field){

    if (field.isAnnotationPresent(Column.class)
            && !field.getAnnotation(Column.class).name().isEmpty()){
      return field.getAnnotation(Column.class).name();
    }

    return field.getName().toLowerCase();
  }

}
