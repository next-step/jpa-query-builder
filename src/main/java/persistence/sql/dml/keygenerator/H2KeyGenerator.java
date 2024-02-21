package persistence.sql.dml.keygenerator;

import jakarta.persistence.GenerationType;

import java.util.UUID;

public class H2KeyGenerator implements KeyGenerator {
    @Override
    public String generator(final GenerationType strategy) {
        if (strategy == GenerationType.IDENTITY) {
            return "default";
        }

        return UUID.randomUUID().toString();
    }
}
