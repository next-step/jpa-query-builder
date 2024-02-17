package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        Field[] declaredFields = carClass.getDeclaredFields();
        for (Field field : declaredFields) {
            logger.debug(field.getName());
        }

        Method[] declaredMethods = carClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            logger.debug(method.getName());
        }

        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            logger.debug(constructor.getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행")
    void testMethodRun() throws InvocationTargetException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        String testName = "test name";
        int testPrice = 1000;
        Method[] declaredMethods = carClass.getDeclaredMethods();
        Car car = new Car(testName, testPrice);
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("test")) {
                Object result = method.invoke(car);
                if (method.getName().equals("testGetName")) {
                    assertThat(result).isEqualTo("test : " + testName);
                }
                if (method.getName().equals("testGetPrice")) {
                    assertThat(result).isEqualTo("test : " + testPrice);
                }
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws InvocationTargetException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        String testName = "test name";
        int testPrice = 1000;
        Method[] declaredMethods = carClass.getDeclaredMethods();
        Car car = new Car(testName, testPrice);
        boolean invoked = false;
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
                invoked = true;
            }
        }
        assertThat(invoked).isTrue();
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = new Car();

        Field nameField = carClass.getDeclaredField("name");
        Field priceField = carClass.getDeclaredField("price");
        nameField.setAccessible(true);
        nameField.set(car, "testName");
        priceField.setAccessible(true);
        priceField.set(car, 1000);

        Object name = nameField.get(car);
        Object price = priceField.get(car);

        assertThat(name).isEqualTo("testName");
        assertThat(price).isEqualTo(1000);
        logger.debug(car.toString());
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
        for(Constructor<?> constructor : declaredConstructors) {
            if(constructor.getParameterCount() > 0) {
                Object car = constructor.newInstance("testName", 1000);
                logger.debug(car.toString());
            }
        }
    }
}
