package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void getDeclaredInfoInCarClass() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        // class 이름 출력
        logger.info("Car 클래스 이름 : {}", carClass.getSimpleName());
        assertThat(carClassName).isEqualTo("Car");

        // 선언된 필드 출력
        List<String> declaredFieldNames = Arrays.stream(carClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        logger.info("선언된 필드 개수 : {}", carClass.getDeclaredFields().length);
        logger.info("선언된 필드 : {}", declaredFieldNames);
        assertThat(declaredFieldNames.contains("price")).isTrue();
        assertThat(declaredFieldNames.contains("name")).isTrue();

        // 메소드 출력
        List<String> declaredMethodNames = Arrays.stream(carClass.getMethods()).map(Method::getName).collect(Collectors.toList());
        logger.info("선언된 메소드 개수 : {}", carClass.getMethods().length);
        logger.info("선언된 메소드 이름 : {}", declaredMethodNames);
        assertThat(declaredMethodNames.contains("printView")).isTrue();
        assertThat(declaredMethodNames.contains("testGetName")).isTrue();
        assertThat(declaredMethodNames.contains("testGetPrice")).isTrue();

        // 생성자 출력
        logger.info("선언된 생성자 개수 : {}", carClass.getConstructors().length);
        logger.info("선언된 생성자에 정의된 파라미터 개수 : {}", Arrays.stream(carClass.getConstructors()).map(Constructor::getParameterCount).collect(Collectors.toList()));
        assertThat(carClass.getConstructors().length).isEqualTo(2);

        // Q. 생성자에 정의된 타입 출력
        // Q. 생성자에 정의된 이름 출력
   }
}
