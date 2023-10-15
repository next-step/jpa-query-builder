package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.SoftAssertions.assertSoftly;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구사항 1 - Car 클래스 정보 출력")
    void testCarPrint() throws NoSuchMethodException {
        //given
        Class<Car> carClass = Car.class;

        //when
        //1. 필드 정보를 출력
        Field[] fields = carClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> logger.info("fields : {}", field));

        //2. 생성자 정보를 출력
        Constructor<Car> carConstructor = carClass.getConstructor();
        String constructorName = carConstructor.getName();
        logger.info("carConstructor : {}", constructorName);

        //3. 메소드 정보를 출력
        Method[] methods = carClass.getMethods();
        Arrays.stream(methods).forEach(method -> logger.info("method : {}", method));

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(fields).isNotEmpty();
            softAssertions.assertThat(constructorName).isNotNull();
            softAssertions.assertThat(methods).isNotEmpty();
        });
    }
}
