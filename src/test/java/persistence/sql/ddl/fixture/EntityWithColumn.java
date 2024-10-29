package persistence.sql.ddl.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityWithColumn {

    @Id
    private Long id;

    @Column(name = "my_column")
    private String withColumn;

    private String withoutColumn;

    @Column(nullable = false)
    private String notNullColumn;

    protected EntityWithColumn() {
    }

    public EntityWithColumn(Long id, String withColumn, String withoutColumn, String notNullColumn) {
        this.id = id;
        this.withColumn = withColumn;
        this.withoutColumn = withoutColumn;
        this.notNullColumn = notNullColumn;
    }

    public Long getId() {
        return id;
    }

    public String getWithColumn() {
        return withColumn;
    }

    public String getWithoutColumn() {
        return withoutColumn;
    }

    public String getNotNullColumn() {
        return notNullColumn;
    }
}
