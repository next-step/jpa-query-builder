package persistence.study;

import annotation.PrintView;
import domain.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private Class<Car> carClass;

    @BeforeEach
    void setUp() {
        carClass = Car.class;
    }

    @Test
    @DisplayName("클래스 정보 출력")
    void testClass() {
        logger.info("Car 객체의 모든 필드");
        final Field[] fields = carClass.getDeclaredFields();
        for (Field field : fields) {
            logger.info(field.getName());
        }

        logger.info("Car 객체의 모든 생성자");
        final Constructor[] constructors = carClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            logger.info("생성자: {}, 파라미터 갯수: {}", constructor.getName(), constructor.getParameterCount());
        }

        logger.info("Car 객체의 모든 메서드");
        final Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            logger.info(method.getName());
        }
    }

    @Test
    @DisplayName("test 로 시작하는 메서드 실행")
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final Method[] methods = carClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                logger.info("methodName: {}, invoke: {}", method.getName(), method.invoke(carClass.getDeclaredConstructor().newInstance()));
            }
        }
    }

    @Test
    @DisplayName("@PrintView 어노테이션 메서드 실행")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Method[] methods = carClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(carClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    @Test
    @DisplayName("private field 에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Car car = carClass.getDeclaredConstructor().newInstance();
        final var expected = "페라리";
        Field name = carClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(car, expected);

        assertThat(car.getName()).isEqualTo(expected);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Constructor constructor = carClass.getDeclaredConstructor(String.class, int.class);
        final Car car = (Car) constructor.newInstance("페라리", 100);

        assertThat(car.getName()).isEqualTo("페라리");
    }

}
