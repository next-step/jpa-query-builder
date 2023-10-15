package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

<<<<<<< HEAD
import java.lang.reflect.Field;
=======
>>>>>>> f026bf9 (printView 어노테이션 메서드 실행)
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD
import static org.assertj.core.api.Assertions.assertThat;

=======
>>>>>>> f026bf9 (printView 어노테이션 메서드 실행)
public class ReflectionTest {

    @DisplayName("test로 시작하는 메소드 실행")
    @Test
    void invokeMethodsStartWithTest() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<Car> carClass = Car.class;
        List<Method> methods = Arrays.stream(carClass
                        .getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        for (Method method : methods) {
            method.invoke(carClass.newInstance());
        }
    }

    @DisplayName("printView 어노테이션이 되어있는 메서드 실행")
    @Test
    void invokePrintViewMethod() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClazz = Car.class;
        List<Method> methods = Arrays.stream(carClazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        for (Method method : methods) {
            method.invoke(carClazz.newInstance());
        }
    }
<<<<<<< HEAD

    @DisplayName("name, price field에 값을 할당한다")
    @Test
    void privateFieldAccess() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Car> carClazz = Car.class;
        Car car = carClazz.newInstance();
        Field name = carClazz.getDeclaredField("name");
        Field price = carClazz.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);
        name.set(car, "sonata");
        price.set(car, 10000);

        assertThat(car.getName()).isEqualTo("sonata");
        assertThat(car.getPrice()).isEqualTo(10000);
    }
=======
>>>>>>> f026bf9 (printView 어노테이션 메서드 실행)
}
