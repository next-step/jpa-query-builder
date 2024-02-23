package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        // Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        for (Method method : carClass.getDeclaredMethods()) {
            logger.debug(method.getName());
        }
        for (Field field : carClass.getFields()) {
            logger.debug(field.getName());
        }
        for (Constructor<?> constructor : carClass.getConstructors()) {
            logger.debug(constructor.getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    public void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = Car.class.getConstructor().newInstance();
        for (Method method : carClass.getDeclaredMethods()) {
            if (isTestMethod(method)) {
                methodRun(car, method);
            }
        }
    }

    @Test
    @DisplayName("PrintView 어노테이션 메소드 실행")
    public void printViewMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = Car.class.getConstructor().newInstance();
        for (Method method : carClass.getDeclaredMethods()){
            if (method.isAnnotationPresent(PrintView.class)) {
                methodRun(car, method);
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    public void privateFieldAccess() throws Exception{
        final String name = "타이칸";
        final int price = 10000;

        Class<Car> carClass = Car.class;
        Car car = Car.class.getConstructor().newInstance();

        setFieldValue(car, carClass.getDeclaredField("name"), name);
        setFieldValue(car, carClass.getDeclaredField("price"), price);

        assertAll(
                () -> assertThat(name).isEqualTo(car.getName()),
                () -> assertThat(price).isEqualTo(car.getPrice())
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    public void constructorWithArgs() throws Exception {
        final String name = "타이칸";
        final int price = 10000;

        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance(name, price);

        assertAll(
                () -> assertThat(name).isEqualTo(car.getName()),
                () -> assertThat(price).isEqualTo(car.getPrice())
        );
    }

    private void methodRun(Object instance, Method method) throws Exception {
        method.setAccessible(true);
        method.invoke(instance);
    }

    private boolean isTestMethod(Method method) {
        return method.getName().startsWith("test");
    }

    private void setFieldValue(Object instance, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }
}
