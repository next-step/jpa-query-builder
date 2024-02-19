package persistence.sql.dml.keygenerator;

import jakarta.persistence.GenerationType;

public interface KeyGenerator {
    public String generator(final GenerationType strategy);
}
