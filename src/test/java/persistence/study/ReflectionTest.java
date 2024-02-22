package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        logger.debug("Car 클래스 필드 정보");
        for (var field : carClass.getDeclaredFields()) {
            logger.debug(field.getName() + ": " + field.getType().getName());
        }

        logger.debug("Car 클래스 생성자 정보");
        for (var constructor : carClass.getConstructors()) {
            logger.debug(constructor.getName());
            for (var param : constructor.getParameterTypes()) {
                logger.debug(" -- param-type: " + param.getName());
            }
        }

        logger.debug("Car 메소드 정보");
        for (var method : carClass.getMethods()) {
            logger.debug(method.getName());
            for (var param : method.getParameterTypes()) {
                logger.debug(" -- param-type: " + param.getName());
            }
            logger.debug(" -- return-type: " + method.getReturnType().getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행하기")
    void testMethodRun() throws Exception {
        Car car = new Car();
        Method[] methods = Car.class.getDeclaredMethods();

        for (Method method: methods) {
            if (method.getName().startsWith("test")) {
                Object result = method.invoke(car);
                assertThat((String) result).isNotBlank();
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Method[] methods = Car.class.getDeclaredMethods();

        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        for (Method method: methods) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(Car.class.getDeclaredConstructor().newInstance());
                assertThat(out.toString()).isEqualTo("자동차 정보를 출력 합니다.\n");
            }
        }
    }

    @Test
    @DisplayName("private field 값 할당")
    void privateFieldAccess() throws Exception {
        Car car = new Car("도요타", 10_000);
        Car expectedCar = new Car("테슬라", 10_000_000);

        Field name = Car.class.getDeclaredField("name");
        Field price = Car.class.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);

        name.set(car, "테슬라");
        price.set(car, 10_000_000);

        assertThat(car.testGetName()).isEqualTo(expectedCar.testGetName());
        assertThat(car.testGetPrice()).isEqualTo(expectedCar.testGetPrice());
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception{
        Class<Car> clazz = Car.class;
        var constructors = clazz.getConstructors();
        Object newCar = constructors[0].newInstance("테슬라", 10_000_000);

        assertThat(((Car) newCar).testGetName()).isNotBlank();
        assertThat(((Car) newCar).testGetPrice()).isNotBlank();
    }
}
