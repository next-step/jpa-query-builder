package persistence.sql.ddl;

public enum Action {
    NONE,
    CREATE,
    DROP,
    BOTH;

    public boolean doCreate() {
        return this == BOTH || this == CREATE;
    }

    public boolean doDrop() {
        return this == BOTH || this == DROP;
    }

    public static Action parseCommandLineOption(String actionText) {
        if ( actionText.equalsIgnoreCase( "create" ) ) {
            return CREATE;
        }
        else if ( actionText.equalsIgnoreCase( "drop" ) ) {
            return DROP;
        }
        else if ( actionText.equalsIgnoreCase( "drop-and-create" ) ) {
            return BOTH;
        }
        else {
            return NONE;
        }
    }
}
