package orm.dsl;

import jdbc.RowMapper;

import java.util.List;

public interface QueryFetcher<E> {

    // rowMapper를 명시적으로 구현
    <T> E fetch(RowMapper<T> rowMapper);
    <T> List<E> fetchOne(RowMapper<T> rowMapper);

    // TODO: 이거 2-2단계에 구현하는게 있어서 아직 구현하지 않음 (리플랙션으로 하는부분)
    List<E> fetch();
    E fetchOne();
}
