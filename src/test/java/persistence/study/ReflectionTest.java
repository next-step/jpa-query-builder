package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.study.Car.TEST_METHOD_PREFIX;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionTest {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
	private Class<Car> carClass;

	@BeforeEach
	void setUp() {
		carClass = Car.class;
	}

	@Test
	@DisplayName("Car 객체 정보 가져오기")
	void showClass() {
		logger.debug(carClass.getName());
	}

	@Nested
	@DisplayName("메서드 테스트")
	class MethodTest {
		private Car car;
		private Method[] declaredMethods;

		@BeforeEach
		void setUp() throws Exception {
			// given
			car = createCarInstance();
			declaredMethods = carClass.getDeclaredMethods();
		}

		@Test
		@DisplayName("Car 메서드 네임이 test로 시작하는 메서드 실행하기")
		void executeMethodStartsWithTest() throws Exception {
			// given
			final String targetMethodName = "test";

			// when
			for (Method declaredMethod : declaredMethods) {
				if (declaredMethod.getName().startsWith(targetMethodName)) {
					final String result = (String) declaredMethod.invoke(car);
					assertThat(result).startsWith(TEST_METHOD_PREFIX);
				}
			}
		}

		@Test
		@DisplayName("@PrintView 애노테이션 메소드 실행하기")
		void executeMethodWithPrintView() throws Exception {
			// when
			for (Method declaredMethod : declaredMethods) {
				if (declaredMethod.isAnnotationPresent(PrintView.class)) {
					declaredMethod.invoke(car);
				}
			}
		}
	}

	@Nested
	@DisplayName("필드 테스트")
	class FieldTest {

		private final String carName = "포르쉐";
		private final int price = 1000;

		@Test
		@DisplayName("private field에 값 할당")
		void privateFieldAccess() throws Exception {
			final Car car = createCarInstance();

			// when
			this.injectField(car, "name", carName);
			this.injectField(car, "price", price);

			// then
			this.assertResult(car);
		}


		@Test
		@DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
		void constructorWithArgs() throws Exception {
			// given
			final Constructor<?>[] constructors = carClass.getConstructors();
			final int targetConstructorParameterNumber = 2;
			Car car = null;

			// when
			for (Constructor<?> constructor : constructors) {
				if (constructor.getParameterCount() == targetConstructorParameterNumber) {
					car = (Car) constructor.newInstance(carName, price);
					break;
				}
			}
			if (car == null) {
				throw new IllegalArgumentException("car 인스턴스 생성에 실패했습니다.");
			}

			// then
			this.assertResult(car);
		}


		private void assertResult(final Car car) {
			assertThat(car.testGetPrice()).isEqualTo(TEST_METHOD_PREFIX + price);
			assertThat(car.testGetName()).isEqualTo(TEST_METHOD_PREFIX + carName);
		}


		private void injectField(Car car, String fieldName, Object fieldValue) throws Exception {
			final Field nameClassDeclaredField = carClass.getDeclaredField(fieldName);
			nameClassDeclaredField.setAccessible(true);
			nameClassDeclaredField.set(car, fieldValue);
		}

	}

	private Car createCarInstance() throws Exception {
		final Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor();
		return declaredConstructor.newInstance();
	}
}