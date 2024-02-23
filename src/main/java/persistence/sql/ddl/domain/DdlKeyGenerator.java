package persistence.sql.ddl.domain;

import jakarta.persistence.GenerationType;

public interface DdlKeyGenerator {
    public String generator(final GenerationType strategy);
}
