package persistence.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LoggingPermission;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보를 출력하라")
    void showClass() {
        Class<Car> carClass = Car.class;
        Assertions.assertEquals(carClass.getName(), "persistence.study.Car");
    }

    @Test
    @DisplayName("test로 시작하는 메소드를 실행하라")
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<Car> carClass = Car.class;

        for (Method declaredMethod : carClass.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test")){
                declaredMethod.invoke(carClass.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 1000));
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션이 붙은 메소드를 실행하라")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;

        for (Method declaredMethod : carClass.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(PrintView.class)){
                declaredMethod.invoke(carClass.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 1000));
            }
        }
    }

    @Test
    @DisplayName("private field에 값을 할당하라")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Car car = new Car();

        Field name = car.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(car, "기아자동차");

        Field price = car.getClass().getDeclaredField("price");
        price.setAccessible(true);
        price.set(car, 1000);

        logger.debug(car.getName());
        logger.debug(String.valueOf(car.getPrice()));
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성하라")
    void constructorWithArgs() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Car.class.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 10000);
    }
}
