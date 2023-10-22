package hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityClassTest {

    @Test
    void Entity_어노테이션이_없으면_생성_시_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityClass<>(EntityClass.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
    }

    @Test
    void Table_어노테이션의_name이_있으면_생성_시_tableName이_된다() {
        String actual = new EntityClass<>(TableEntity.class).tableName();
        assertThat(actual).isEqualTo("new_table");
    }

    @Test
    void Table_어노테이션의_name이_없으면_tableName은_클래스명이_된다() {
        String actual = new EntityClass<>(NoTableEntity.class).tableName();
        assertThat(actual).isEqualTo("NoTableEntity");
    }

    @Test
    void 새로운_인스턴스를_생성한다()  {
        TableEntity actual = new EntityClass<>(TableEntity.class).newInstance();
        assertThat(actual).isInstanceOf(TableEntity.class);
    }

    @Test
    void 인스턴스_생성_시_기본생성자가_존재하지않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityClass<>(NoConstructorEntity.class).newInstance())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("기본 생성자가 존재하지 않습니다.");
    }

    @Entity
    @Table(name = "new_table")
    static class TableEntity {
        @Id
        private Long id;
    }

    @Entity
    static class NoTableEntity {
        @Id
        private Long id;
    }

    @Entity
    static class NoConstructorEntity {
        @Id
        private Long id;

        public NoConstructorEntity(Long id) {
            this.id = id;
        }
    }
}
