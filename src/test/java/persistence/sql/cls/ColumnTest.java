package persistence.sql.cls;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class ColumnTest {
    @Id
    private Long id;

    private Long noColumn;

    @Column
    private Long columnWithoutName;

    @Column(name = "column")
    private Long columnWithName;

    @Column
    private Long defaultNullableColumn;

    @Column(nullable = false)
    private Long nonNullableColumn;

    private Long fieldTypeLong;

    private Integer fieldTypeInteger;

    private String fieldTypeString;

    @Transient
    private String transientField;
}
