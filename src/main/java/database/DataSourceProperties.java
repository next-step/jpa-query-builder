package database;

public class DataSourceProperties {
    private final String url;
    private final String username;
    private final String password;

    public DataSourceProperties(final String url, final String username, final String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
