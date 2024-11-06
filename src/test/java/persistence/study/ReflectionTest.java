package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구사항1. Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }

    @Test
    @DisplayName("요구사항2. test로 시작하는 메소드 실행")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        for (Method declaredMethods : carClass.getDeclaredMethods()) {
            invokeStartingTestCarInstance(declaredMethods, car);
        }
    }

    private static void invokeStartingTestCarInstance(Method declaredMethods, Car car) throws IllegalAccessException, InvocationTargetException {
        if (declaredMethods.getName().startsWith("test")) {
            declaredMethods.invoke(car);
        }
    }

    @Test
    @DisplayName("요구사항3. @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();
        Method[] methods = carClass.getDeclaredMethods();

        for(Method method : methods) {
            invokeAnnotationPresentInstance(method, car);
        }

    }

    private static void invokeAnnotationPresentInstance(Method method, Car car) throws IllegalAccessException, InvocationTargetException {
        if(method.isAnnotationPresent(PrintView.class)) {
            method.invoke(car);
        }
    }

    @Test
    @DisplayName("요구사항4. private필드에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<Car> clazz = Car.class;
        Car car = clazz.getDeclaredConstructor().newInstance();
        Field name = clazz.getDeclaredField("name");
        Field price = clazz.getDeclaredField("price");
        name.setAccessible(true);
        price.setAccessible(true);
        name.set(car, "Model Y");
        price.set(car, 60000000);

        assertAll("값 검증",
                () -> assertEquals(car.getName(), name.get(car)),
                () -> assertEquals(car.getPrice(), price.get(car))
        );
    }

    @Test
    @DisplayName("요구사항5. 인자를 가진 생성자 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> clazz = Car.class;
        Constructor<Car> constructor = (Constructor<Car>) Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 2)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        Car car = constructor.newInstance("Model Y", 60000000);

        assertAll("값 검증",
                () -> assertEquals(car.getName(), "Model Y"),
                () -> assertEquals(car.getPrice(), 60000000)
                );
    }
}
