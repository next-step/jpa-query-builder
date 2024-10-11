package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }

    @Test
    @DisplayName("Car 객체의 메서드 중 test로 시작하는 메소드 실행한다.")
    void testMethodRun() throws Exception {
        // given
        Class<Car> carClass = Car.class;
        Method[] declaredMethods = carClass.getDeclaredMethods();
        var car = carClass.getDeclaredConstructors()[1].newInstance("BMW", 100_000);

        // when
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("test")) {
                Object result = method.invoke(car);
                logger.debug("method name : {}, result :  {}", method.getName(), result);
            }
        }
    }
}
