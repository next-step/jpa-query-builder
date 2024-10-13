package persistence.model.meta;

public record DataType(int sqlTypeCode, Class<?> javaType, String namePattern) {
    public String getFullName(int length) {
        if (namePattern.contains("%d")) {
            return String.format(namePattern, length);
        }
        return namePattern;
    }
}
