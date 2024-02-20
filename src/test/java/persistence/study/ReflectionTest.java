package persistence.study;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("Car 객체 정보 가져오기")
	void showCarClass() {
		Class<Car> carClass = Car.class;
		logger.debug(carClass.getName());

		Field[] fields = carClass.getDeclaredFields();
		System.out.println("필드:");
		for (Field field : fields) {
			System.out.println(field);
		}

		Constructor<?>[] constructors = carClass.getDeclaredConstructors();
		System.out.println("생성자:");
		for (Constructor<?> constructor : constructors) {
			System.out.println(constructor);
		}

		Method[] methods = carClass.getDeclaredMethods();
		System.out.println("메서드");
		for (Method method : methods) {
			System.out.println(method);
		}
	}

	@Test
	void test_로_시작하는_메소드_실행() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<Car> carClass = Car.class;
		Car car = carClass.getConstructor().newInstance();
		Arrays.stream(carClass.getDeclaredMethods())
			.filter(x -> x.getName().contains("test"))
			.forEach(x -> {
				try {
					x.invoke(car);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
	}
}
