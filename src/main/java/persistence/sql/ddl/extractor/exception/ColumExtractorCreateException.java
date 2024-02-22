package persistence.sql.ddl.extractor.exception;

public class ColumExtractorCreateException extends RuntimeException {
    public ColumExtractorCreateException(final String message) {
        super(message);
    }
}
