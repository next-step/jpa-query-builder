package persistence.meta;

import persistence.dialect.h2.H2Dialect;

import java.util.List;
import java.util.Map;

public class MetaEntity<T> {
  private final MetaDataTable metaDataTable;
  private final MetaDataColumns metaDataColumns;

  private MetaEntity(MetaDataTable metaDataTable, MetaDataColumns metaDataColumns) {
    this.metaDataTable = metaDataTable;
    this.metaDataColumns = metaDataColumns;
  }

  public static <T> MetaEntity<T> of(T entity){
    Class<?> clazz = entity.getClass();

    return new MetaEntity<>(MetaDataTable.of(clazz), MetaDataColumns.of(clazz, new H2Dialect()));
  }

  public String getTableName(){
    return metaDataTable.getName();
  }

  public List<String> getEntityColumns(){
    return metaDataColumns.getSimpleColumns();
  }

  public List<String> getEntityFields(){
    return metaDataColumns.getFields();
  }
  public Map<String, String> getFieldMapping(){
    return metaDataColumns.getFieldToDBColumnMap();
  }

  public List<String> getEntityColumnsWithId(){
    return metaDataColumns.getColumnsWithId();
  }
}
