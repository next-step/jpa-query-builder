package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.study.Car.TEST_METHOD_PREFIX;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("Car 객체 정보 가져오기")
	void showClass() {
		Class<Car> carClass = Car.class;

		logger.debug(carClass.getName());
	}

	@Test
	@DisplayName("Car 메서드 네임이 test로 시작하는 메서드 실행하기")
	void executeMethodStartsWithTest() throws Exception {
		// given
		Class<Car> carClass = Car.class;
		Method[] declaredMethods = carClass.getDeclaredMethods();
		Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor();
		Car car = declaredConstructor.newInstance();

		// when
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.getName().startsWith("test")) {
				declaredMethod.invoke(car);
			}
		}
	}

	@Test
	@DisplayName("@PrintView 애노테이션 메소드 실행하기")
	void executeMethodWithPrintView() throws Exception {
		// given
		Class<Car> carClass = Car.class;
		Method[] declaredMethods = carClass.getDeclaredMethods();
		Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor();
		Car car = declaredConstructor.newInstance();

		// when
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isAnnotationPresent(PrintView.class)) {
				declaredMethod.invoke(car);
			}
		}
	}

	@Test
	@DisplayName("private field에 값 할당")
	void privateFieldAccess() throws Exception {
		// given
		Class<Car> carClass = Car.class;
		Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor();
		Car car = declaredConstructor.newInstance();
		String carName = "포르쉐";
		int price = 1000;

		// when
		Field nameClassDeclaredField = carClass.getDeclaredField("name");
		Field priceClassDeclaredField = carClass.getDeclaredField("price");
		nameClassDeclaredField.setAccessible(true);
		nameClassDeclaredField.set(car, carName);
		priceClassDeclaredField.setAccessible(true);
		priceClassDeclaredField.set(car, price);

		// then
		assertThat(car.testGetPrice()).isEqualTo(TEST_METHOD_PREFIX + price);
		assertThat(car.testGetName()).isEqualTo(TEST_METHOD_PREFIX + carName);
	}
}
