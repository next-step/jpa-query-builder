package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        logger.debug("Car 클래스의 모든 필드 정보 출력");
        Field[] fields = carClass.getDeclaredFields();
        for (Field field : fields) {
            logger.debug("field type : {}, field name : {}", field.getType(), field.getName());
        }

        // Car 클래스의 생성자 정보 출력
        logger.debug("Car 클래스의 생성자 정보 출력");
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug("constructor : {}", constructor.getName());
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                logger.debug("parameter type : {}", parameterType.getName());
            }
        }

        logger.debug("Car 클래스의 메서드 정보 출력");
        Method[] declaredMethods = carClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            logger.debug("method name : {}", method.getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                logger.debug("parameter type : {}", parameterType.getName());
            }
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<Car> constructor = carClass.getConstructor();
        Car carInstance = constructor.newInstance();

        Method[] declaredMethods = carClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("test")) {
                logger.debug("method name={}, invoked result => {}", method.getName(), method.invoke(carInstance));
            }
        }
    }
}
