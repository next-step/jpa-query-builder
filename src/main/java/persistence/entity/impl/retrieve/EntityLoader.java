package persistence.entity.impl.retrieve;

public interface EntityLoader<T> {

    <R> T load(String selectSql);
}
