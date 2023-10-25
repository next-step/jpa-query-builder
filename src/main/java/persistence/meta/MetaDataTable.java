package persistence.meta;

import jakarta.persistence.Table;

import java.lang.annotation.Annotation;

public class MetaDataTable {

  private final String name;

  public String getName() {
    return name;
  }

  private MetaDataTable(String name) {
    this.name = name;
  }

  public static MetaDataTable of(Class<?> clazz){

    if(clazz.isAnnotationPresent(Table.class)){
      Table annotation = clazz.getAnnotation(Table.class);
      return new MetaDataTable(annotation.name().toUpperCase());
    }

    return new MetaDataTable(clazz.getName().toUpperCase());
  }
}
