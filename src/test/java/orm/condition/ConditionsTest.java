package orm.condition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.dsl.condition.Conditions;
import orm.dsl.condition.EqualCondition;
import orm.dsl.condition.NoCondition;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionsTest {

    @Test
    @DisplayName("조건문 렌더링 테스트")
    void 조건문_랜더링_테스트() {
        // given
        Conditions conditions = new Conditions();

        // when
        conditions.add(new EqualCondition("name", "설동민"));
        conditions.add(new EqualCondition("age", 30));

        // then
        assertThat(conditions.renderCondition()).isEqualTo("name = '설동민' AND age = 30");
    }

    @Test
    @DisplayName("조건문 렌더링 테스트 - 조건이 없는 경우")
    void 조건문_랜더링_테스트_조건이_없는_경우() {
        // given
        Conditions conditions = new Conditions();

        // then
        assertThat(conditions.renderCondition()).isEmpty();
    }

    @Test
    @DisplayName("조건문 렌더링 테스트 - NoCondition이 있으면, 있어도 조건이 없는것으로 판단한다.")
    void 조건문_랜더링_테스트_조건이_없는_경우_noCondition_추가() {
        // given
        Conditions conditions = new Conditions();
        conditions.add(new NoCondition());

        // then
        assertThat(conditions.hasCondition()).isFalse();
    }
}
