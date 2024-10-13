package orm.dsl.ddl;

import jakarta.persistence.GeneratedValue;
import orm.TableEntity;
import orm.TableField;
import orm.TablePrimaryField;
import orm.dsl.JpaSettings;
import orm.dsl.QueryBuilder;
import orm.dsl.ddl.dialect.h2.ColumnTypeMapper;
import orm.exception.InvalidIdGenerationException;

import java.util.StringJoiner;

public abstract class CreateTableImpl<ENTITY> implements CreateTableStep {

    protected final JpaSettings settings;
    protected final ColumnTypeMapper columnTypeMapper;

    protected boolean ifNotExist = false;
    protected TableEntity<ENTITY> tableEntity;

    public CreateTableImpl(JpaSettings settings, TableEntity<ENTITY> tableEntity) {
        this.settings = settings;
        this.tableEntity = tableEntity;
        this.columnTypeMapper = ColumnTypeMapper.of(settings.getDialect());
    }

    @Override
    public QueryBuilder ifNotExist() {
        this.ifNotExist = true;
        return this;
    }

    protected String renderColumns(TableField column, String mappedRDBType) {
        return column.getFieldName() + " " + mappedRDBType;
    }

    /**
     * PK 컬럼 생성 렌더링
     *
     * @param mappedRDBType
     * @return
     */
    protected String renderPrimaryKeyColumns(String mappedRDBType) {
        TablePrimaryField id = tableEntity.getId();
        GeneratedValue generatedValue = id.getGeneratedValue();

        if (generatedValue == null) {
            return "";
        }

        final String keyword = switch (generatedValue.strategy()) {
            case IDENTITY -> renderAutoIncrement();
            default -> throw new InvalidIdGenerationException("Unsupported GenerationType: " + generatedValue.strategy());
        };

        return new StringJoiner(" ")
                .add(id.getFieldName())
                .add(mappedRDBType)
                .add(keyword)
                .toString();
    }

    /**
     * PK AI 키워드 반환
     */
    protected String renderAutoIncrement() {
        return "AUTO_INCREMENT";
    }

    protected String renderIfNotExist() {
        return ifNotExist ? "IF NOT EXISTS" : "";
    }
}
