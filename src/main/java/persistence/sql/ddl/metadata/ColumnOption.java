package persistence.sql.ddl.metadata;

public enum ColumnOption {
    NOT_NULL("not null"),
    AUTO_INCREMENT("auto_increment"),
    ;

    private final String option;

    ColumnOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
