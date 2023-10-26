package persistence.meta;

import persistence.dialect.h2.H2Dialect;

import java.util.List;
import java.util.Map;

public class MetaEntity<T> {
  private final T entity;
  private final MetaDataTable metaDataTable;
  private final MetaDataColumns metaDataColumns;

  private MetaEntity(T entity, MetaDataTable metaDataTable, MetaDataColumns metaDataColumns) {
    this.entity = entity;
    this.metaDataTable = metaDataTable;
    this.metaDataColumns = metaDataColumns;
  }

  public static <T> MetaEntity of(T entity){
    Class<?> clazz = entity.getClass();

    return new MetaEntity(entity, MetaDataTable.of(clazz), MetaDataColumns.of(clazz, new H2Dialect()));
  }

  public String getTableName(){
    return metaDataTable.getName();
  }

  public List<String> getEntityFields(){
    return metaDataColumns.getSimpleColumns();
  }

  public Map<String, String> getFieldMapping(){
    return metaDataColumns.getFieldToDBColumnMap();
  }
}
