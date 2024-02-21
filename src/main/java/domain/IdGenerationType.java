package domain;

import jakarta.persistence.GenerationType;

public enum IdGenerationType {

    IDENTITY(GenerationType.IDENTITY, "auto_increment");
    private final GenerationType generationType;
    private final String value;

    IdGenerationType(GenerationType generationType, String value) {
        this.generationType = generationType;
        this.value = value;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public String getValue() {
        return value;
    }

    public static IdGenerationType from(GenerationType generationType) {
        for (IdGenerationType idGenerationType : IdGenerationType.values()) {
            if (idGenerationType.getGenerationType().equals(generationType)) {
                return idGenerationType;
            }
        }
        throw new IllegalStateException("지원하는 GenerationType 이 아닙니다.");
    }
}
