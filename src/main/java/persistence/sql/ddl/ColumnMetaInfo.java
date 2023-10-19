package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.annotation.Annotation;

public class ColumnMetaInfo {

    private String value;
    private int priority;

    public ColumnMetaInfo(Annotation annotation) {
        // TODO 리팩토링
        if (annotation instanceof GeneratedValue) {
            GeneratedValue generatedValue = (GeneratedValue) annotation;
            if (generatedValue.strategy().name().equals(GenerationType.IDENTITY.name())) {
                value = "AUTO_INCREMENT";
                priority = 1;
            }
        }

        if (annotation instanceof Id) {
            value = "PRIMARY KEY";
            priority = 2;
        }

        if (annotation instanceof Column) {
            Column column = (Column) annotation;
            if (!column.nullable()) {
                value = "NOT NULL";
                priority = 3;
            }
        }

        if (value == null) {
            value = "";
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isValuePresent() {
        return value != null;
    }

    public int compareTo(ColumnMetaInfo columnMetaInfo) {
        return Integer.compare(priority, columnMetaInfo.priority);
    }

}
