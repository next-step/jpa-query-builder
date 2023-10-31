package persistence.sql.ddl;

import persistence.sql.ddl.scheme.ColumnScheme;

public enum ColumnOption {

    AUTO_INCREMENT("AUTO_INCREMENT", 1),
    NOT_NULL("NOT NULL", 3),
    PRIMARY_KEY("PRIMARY KEY", 2);

    private final String option;
    private final int priority;

    ColumnOption(String option, int priority) {
        this.option = option;
        this.priority = priority;
    }

    public String getOption() {
        return option;
    }

    public int getPriority() {
        return priority;
    }

    public static ColumnOption valueOf(ColumnScheme columnScheme) {
        for (ColumnOption columnOption : ColumnOption.values()) {
            if (columnOption.getOption().equals(columnScheme.getValue())) {
                return columnOption;
            }
        }

        return null;
    }

}

