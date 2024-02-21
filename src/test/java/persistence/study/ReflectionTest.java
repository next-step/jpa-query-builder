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

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        // 필드
        Field[] fields = carClass.getDeclaredFields();
        System.out.println("필드:");
        for (Field field : fields) {
            System.out.println(field);
        }

        // 생성자
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        System.out.println("생성자:");
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        // 메서드
        Method[] methods = carClass.getDeclaredMethods();
        System.out.println("메서드");
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    @Test
    @DisplayName("test 로 시작하는 메소드 실행")
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            String s = method.getName();
            if (s.startsWith("test")) {
                Object invoke = method.invoke(constructors[0].newInstance());
                System.out.println(invoke);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            String s = method.getName();
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(constructors[0].newInstance());
            }
        }
    }


    @Test
    @DisplayName("private field에 값 할당")
    public void privateFieldAccess() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Car car = (Car) constructors[0].newInstance();

        // 필드
        Field[] fields = carClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("name")) {
                field.set(car, "소나타");
            } else if (field.getName().equals("price")) {
                field.set(car, 123);
            }
        }

        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 123")
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();

        Constructor<?> constructor = Arrays.stream(constructors).filter(constructor1 -> {
            int count = constructor1.getParameterCount();
            return count == 2;
        }).findFirst().get();

        Car car = (Car) constructor.newInstance("소나타", 123);

        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 123")
        );
    }
}
