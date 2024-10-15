package persistence.sql.dml;

public interface SelectQueryBuilder {

    String findAll();

    String findById(Long id);


}
