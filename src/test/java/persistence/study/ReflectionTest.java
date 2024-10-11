package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug("getName : {}, getSimpleName : {}", carClass.getName(), carClass.getSimpleName());
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void invokeMethodsStartWithTest() throws Exception {
        Class<Car> carClass = Car.class;
        Car superCar = new Car("SUPER CAR", 100);

        Method[] declaredMethods = carClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith("test")) {
                Object result = declaredMethod.invoke(superCar);
                logger.debug("method name : {}, result :  {}", declaredMethod.getName(), result);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void invokeMethodsWithAnnotation() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = new Car("name", 1);

        for (Method declaredMethod : carClass.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(PrintView.class)) {
                declaredMethod.invoke(car);
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void setValuesToPrivateFields() throws Exception {
        Class<Car> carClass = Car.class;
        Field nameField = carClass.getDeclaredField("name");
        Field priceField = carClass.getDeclaredField("price");
        nameField.setAccessible(true);
        priceField.setAccessible(true);

        Car carInstance = carClass.getDeclaredConstructor().newInstance();
        nameField.set(carInstance, "SUPER CAR");
        priceField.set(carInstance, 100);

        logger.debug("name : {}, price : {}", carInstance.getName(), carInstance.getPrice());

        assertThat("SUPER CAR".equals(carInstance.getName())).isTrue();
        assertThat(100 == carInstance.getPrice()).isTrue();
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> carClass = Car.class;
        int fieldCount = carClass.getDeclaredFields().length;
        for (Constructor<?> declaredConstructor : carClass.getDeclaredConstructors()) {
            if (declaredConstructor.getParameters().length == fieldCount) {
                logger.debug("declaredConstructor : {}", declaredConstructor.toGenericString());
                Car superCar = (Car) declaredConstructor.newInstance("SUPER CAR", 100);
                logger.debug("name : {}, price : {}", superCar.getName(), superCar.getPrice());
            }
        }
    }
}
