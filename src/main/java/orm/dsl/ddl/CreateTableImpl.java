package orm.dsl.ddl;

import jakarta.persistence.GeneratedValue;
import orm.TableEntity;
import orm.TableField;
import orm.TablePrimaryField;
import orm.dsl.QueryBuilder;
import orm.dsl.ddl.dialect.h2.ColumnTypeMapper;
import orm.exception.InvalidIdGenerationException;

import java.util.StringJoiner;

public abstract class CreateTableImpl<ENTITY> implements CreateTableStep {

    protected final ColumnTypeMapper columnTypeMapper;
    protected final TableEntity<ENTITY> tableEntity;

    protected boolean ifNotExist = false;

    public CreateTableImpl(TableEntity<ENTITY> tableEntity) {
        this.tableEntity = tableEntity;
        this.columnTypeMapper = ColumnTypeMapper.of(tableEntity.getJpaSettings().getDialect());
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

        StringJoiner pkColumn = new StringJoiner(" ")
                .add(id.getFieldName())
                .add(mappedRDBType);

        if (generatedValue == null) {
            return pkColumn.toString();
        }

        final String keyword = switch (generatedValue.strategy()) {
            case IDENTITY -> renderAutoIncrement();
            default -> throw new InvalidIdGenerationException("Unsupported GenerationType: " + generatedValue.strategy());
        };

        return pkColumn.add(keyword)
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
