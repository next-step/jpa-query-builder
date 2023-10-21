package persistence.sql.dml.where;

import persistence.core.EntityMetadataModel;

import java.util.List;
import java.util.stream.Collectors;

public class FetchWhereQueries {

    private final List<FetchWhereQuery> fetchWhereQueries;

    public FetchWhereQueries(List<FetchWhereQuery> fetchWhereQueries) {
        assert fetchWhereQueries != null && !fetchWhereQueries.isEmpty();
        this.fetchWhereQueries = fetchWhereQueries;
    }


    public List<String> getQueries(EntityMetadataModel entityMetadataModel) {
        return fetchWhereQueries.stream()
                .map(it -> it.makeWhereQuery(entityMetadataModel))
                .collect(Collectors.toUnmodifiableList());
    }
}
