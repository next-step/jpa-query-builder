package persistence.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ReflectionTest {

    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void showClass() {
        Class<Car> clazz = Car.class;

        Field[] declaredFields = clazz.getDeclaredFields();
        Constructor<?>[] constructors = clazz.getConstructors();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        Arrays.stream(declaredFields).forEach(field -> log.debug("field: {}", field));
        Arrays.stream(constructors).forEach(constructor -> log.debug("constructor: {}", constructor));
        Arrays.stream(declaredMethods).forEach(declaredMethod -> log.debug("method: {}", declaredMethod));
    }

    @Test
    @DisplayName("요구사항 2 - test 로 시작하는 메소드 실행")
    void testMethodRun() {
        Class<Car> clazz = Car.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        assertThat(method.invoke(clazz.getConstructor().newInstance())).isInstanceOf(String.class);
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Class<Car> clazz = Car.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.getConstructor().newInstance());
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
