package persistence.sql.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.sql.*;
import java.time.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public enum SqlType {
    BIT,
    TINYINT,
    SMALLINT,
    INTEGER,
    BIGINT,
    FLOAT,
    REAL,
    DOUBLE,
    NUMERIC,
    DECIMAL,
    CHAR,
    VARCHAR,
    LONGVARCHAR,
    DATE,
    TIME,
    TIMESTAMP,
    BINARY,
    VARBINARY,
    LONGVARBINARY,
    NULL,
    OTHER,
    JAVA_OBJECT,
    DISTINCT,
    STRUCT,
    ARRAY,
    BLOB,
    CLOB,
    REF,
    DATALINK,
    BOOLEAN,
    ROWID,
    NCHAR,
    NVARCHAR,
    LONGNVARCHAR,
    NCLOB,
    SQLXML,
    REF_CURSOR,
    TIME_WITH_TIMEZONE,
    TIMESTAMP_WITH_TIMEZONE,
    INTERVAL_SECOND,
    INET,
    TIMESTAMP_UTC,
    UUID;

    private static final HashMap<Class<?>, SqlType> javaClassToJdbcTypeCodeMap = buildJavaClassToJdbcTypeCodeMappings();

    private static HashMap<Class<?>, SqlType> buildJavaClassToJdbcTypeCodeMappings() {
        final HashMap<Class<?>, SqlType> workMap = new HashMap<>();

        workMap.put(String.class, SqlType.VARCHAR);
        workMap.put(BigDecimal.class, SqlType.NUMERIC);
        workMap.put(BigInteger.class, SqlType.NUMERIC);
        workMap.put(Boolean.class, SqlType.BIT);
        workMap.put(Byte.class, SqlType.TINYINT);
        workMap.put(Short.class, SqlType.SMALLINT);
        workMap.put(Integer.class, SqlType.INTEGER);
        workMap.put(Long.class, SqlType.BIGINT);
        workMap.put(Float.class, SqlType.REAL);
        workMap.put(Double.class, SqlType.DOUBLE);
        workMap.put(byte[].class, SqlType.VARBINARY);
        workMap.put(Date.class, SqlType.DATE);
        workMap.put(Time.class, SqlType.TIME);
        workMap.put(Timestamp.class, SqlType.TIMESTAMP);
        workMap.put(LocalTime.class, SqlType.TIME);
        workMap.put(OffsetTime.class, SqlType.TIME_WITH_TIMEZONE);
        workMap.put(LocalDate.class, SqlType.DATE);
        workMap.put(LocalDateTime.class, SqlType.TIMESTAMP);
        workMap.put(OffsetDateTime.class, SqlType.TIMESTAMP_WITH_TIMEZONE);
        workMap.put(ZonedDateTime.class, SqlType.TIMESTAMP_WITH_TIMEZONE);
        workMap.put(Instant.class, SqlType.TIMESTAMP_UTC);
        workMap.put(Blob.class, SqlType.BLOB);
        workMap.put(Clob.class, SqlType.CLOB);
        workMap.put(Array.class, SqlType.ARRAY);
        workMap.put(Struct.class, SqlType.STRUCT);
        workMap.put(Ref.class, SqlType.REF);
        workMap.put(Class.class, SqlType.JAVA_OBJECT);
        workMap.put(RowId.class, SqlType.ROWID);
        workMap.put(SQLXML.class, SqlType.SQLXML);
        workMap.put(UUID.class, SqlType.UUID);
        workMap.put(InetAddress.class, SqlType.INET);
        workMap.put(Inet4Address.class, SqlType.INET);
        workMap.put(Inet6Address.class, SqlType.INET);
        workMap.put(Duration.class, SqlType.INTERVAL_SECOND);

        workMap.put(Character.class, SqlType.CHAR);
        workMap.put(char[].class, SqlType.VARCHAR);
        workMap.put(java.util.Date.class, SqlType.TIMESTAMP);
        workMap.put(Calendar.class, SqlType.TIMESTAMP);

        return workMap;
    }

    public static SqlType of(Class<?> clazz) {
        SqlType type = javaClassToJdbcTypeCodeMap.get(clazz);

        if (type == null) {
            throw new IllegalArgumentException("No mapping for jdbc type: " + clazz.getSimpleName());
        }

        return type;
    }

    public String getQuery() {
        return this.name()
                .toLowerCase();
    }
}
