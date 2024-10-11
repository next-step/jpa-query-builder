package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

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

        List<Method> methods = extractMethod(carClass, m -> m.getName().startsWith("test"));
        for (Method m : methods) {
            logger.debug("method name={}, invoked result => {}", m.getName(), m.invoke(carInstance));
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<Car> constructor = carClass.getConstructor();
        Car carInstance = constructor.newInstance();

        List<Method> method = extractMethod(carClass, m -> m.isAnnotationPresent(PrintView.class));
        for (Method m : method) {
            m.invoke(carInstance);
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<Car> constructor = carClass.getConstructor();
        Car carInstance = constructor.newInstance();

        for (Field field : carClass.getDeclaredFields()) {
            if (!field.canAccess(carInstance)) {
                field.setAccessible(true);

                if (field.getName().equals("name")) {
                    field.set(carInstance, "BMW");
                } else if (field.getName().equals("price")) {
                    field.set(carInstance, 1000);
                }
            }
        }

        assertThat(carInstance.getName()).isEqualTo("BMW");
        assertThat(carInstance.getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> carClass = Car.class;
        Car carInstance = null;
        for (Constructor<?> constructor : carClass.getDeclaredConstructors()) {
            if (constructor.getParameterCount() != 0) {
                carInstance = (Car) constructor.newInstance("BMW", 1000);
            }
        }

        assertThat(carInstance).isNotNull();
        assertThat(carInstance.getName()).isEqualTo("BMW");
        assertThat(carInstance.getPrice()).isEqualTo(1000);
    }

    private List<Method> extractMethod(Class<Car> carClass, Predicate<Method> predicate) {
        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Method> result = new ArrayList<>(declaredMethods.length);

        for (Method method : declaredMethods) {
            if (predicate.test(method)) {
                result.add(method);
            }
        }
        return result;
    }
}
