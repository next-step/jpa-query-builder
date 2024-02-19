package persistence.sql.dml;

import jakarta.persistence.GenerationType;

public interface KeyGenerator {
    public String generator(final GenerationType strategy);
}
