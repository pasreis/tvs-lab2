package app;

import static org.testng.Assert.*;
import org.testng.annotations.*;

public class CalculatorTest {
	private Calculator calculator;
	private IllegalArgumentException exception;

	@DataProvider
	private Object[][] getConstructorValues() {
		return new Object[][] {
			{ "Tejo" },
			{ "Oi "},
			{ "Troia"}
		};
	}

	@DataProvider
	private Object[][] getConstructorValuesInvalidValues() {
		return new Object[][] {
			{ "P" },
			{ "Lisboa" }
		};
	}

	@DataProvider
	private Object[][] getSumValues() {
		return new Object[][] {
			{ 1, 1, 2 },
			{ 0, 1, 1 },
			{ 0, 0, 0 },
			{ -1, -1, -2 },
			{ -1,  1,  0 },
			{  1, -1,  0 },
			{ -1,  0, -1 },
			{  0, -1, -1 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, -2 },
			{ Integer.MIN_VALUE, Integer.MIN_VALUE,  0 },
			{ Integer.MAX_VALUE, Integer.MIN_VALUE, -1 },
			{ Integer.MIN_VALUE, Integer.MAX_VALUE, -1 },
			{ Integer.MAX_VALUE,  1, Integer.MIN_VALUE },
			{ Integer.MAX_VALUE, -1, Integer.MAX_VALUE - 1 },
			{ Integer.MAX_VALUE,  0, Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, null, Integer.MAX_VALUE },
			{  1, Integer.MAX_VALUE, Integer.MIN_VALUE },
			{ -1, Integer.MAX_VALUE, Integer.MAX_VALUE - 1 },
			{  0, Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ null, Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ Integer.MIN_VALUE,  1, Integer.MIN_VALUE + 1 },
			{ Integer.MIN_VALUE, -1, Integer.MAX_VALUE },
			{ Integer.MIN_VALUE,  0, Integer.MIN_VALUE },
			{ Integer.MIN_VALUE, null, Integer.MIN_VALUE },
			{  1, Integer.MIN_VALUE, Integer.MIN_VALUE + 1 },
			{ -1, Integer.MIN_VALUE, Integer.MAX_VALUE },
			{  0, Integer.MIN_VALUE, Integer.MIN_VALUE },
			{ null, Integer.MIN_VALUE, Integer.MIN_VALUE },
			{ null, null, 0 },
			{ null, 1, 1 },
			{ 1, null, 1 },
			{ null, -1, -1 },
			{ -1, null, -1 }
		};
	}

	@DataProvider
	private Object[][] getDivideExceptionValues() {
		return new Object[][] {
			{  2, 0 },
			{ -2, 0 },
			{  0, 0 },
			{ Integer.MAX_VALUE, 0 },
			{ Integer.MIN_VALUE, 0 },
			{ Integer.MAX_VALUE, null },
			{ Integer.MIN_VALUE, null },
			{ null, 0 },
			{  4, null },
			{ -4, null },
			{  0, null },
			{ null, null }
		};
	}

	@DataProvider
	private Object[][] getDivideValues() {
		return new Object[][] {
			{  4,  2,  2 },
			{ -4,  2, -2 },
			{  0,  2,  0 },
			{  4, -2, -2 },
			{ -4, -2,  2 },
			{  0, -2,  0 },
			{  4, Integer.MAX_VALUE,  4 / Integer.MAX_VALUE },
			{ -4, Integer.MAX_VALUE, -4 / Integer.MAX_VALUE },
			{  0, Integer.MAX_VALUE, 0 },
			{  4, Integer.MIN_VALUE,  4 / Integer.MIN_VALUE },
			{ -4, Integer.MIN_VALUE, -4 / Integer.MIN_VALUE },
			{  0, Integer.MIN_VALUE,  0 / Integer.MIN_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE,  1 },
			{ Integer.MIN_VALUE, Integer.MIN_VALUE,  1 },
			{ Integer.MAX_VALUE, Integer.MIN_VALUE,  0 },
			{ Integer.MIN_VALUE, Integer.MAX_VALUE, -1 },
			{ null,  2, 0 },
			{ null, -2, 0 }
		};
	}

	@BeforeMethod
	public void setup() {
		calculator = new Calculator("Tejo");
	}

	@Test(
		groups = { "constructor" },
		dataProvider = "getConstructorValues")
	public void testConstructorWithNameOK(String name) {
		// Act
		calculator = new Calculator(name);

		// Assert
		assertNotNull(calculator);
		assertEquals(calculator.getName(), name);
		assertEquals(calculator.getNumberOfOperations(), 0);
	}

	@Test(groups = { "constructor" })
	public void testConstructorWithNullName() {
		// Arrange
		String name = null;

		// Act
		exception = expectThrows(IllegalArgumentException.class, () -> { new Calculator(name); });

		// Assert
		assertTrue(exception.getMessage().contains("no name"));
	}

	@Test(
		groups = { "constructor" },
		dataProvider = "getConstructorValuesInvalidValues")
	public void testConstructorWithNameNOK(String name) {
		// Act
		exception = expectThrows(IllegalArgumentException.class, () -> { new Calculator(name); });

		// Assert
		assertTrue(exception.getMessage().contains("no name"));
	}

	@Test(
		groups = { "sum" },
		dataProvider = "getSumValues")
	public void testSum(Integer a, Integer b, Integer expectedResult) {
		// Act
		Integer result = calculator.sum(a, b);

		// Assert
		assertEquals(result, expectedResult);
		assertEquals(calculator.getNumberOfOperations(), 1);
	}

	@Test(
		groups = { "divide" },
		dataProvider = "getDivideExceptionValues")
	public void testDivideExceptions(Integer a, Integer b) {
		// Act
		exception = expectThrows(IllegalArgumentException.class, () -> { calculator.divide(a, b); });

		// Assert
		assertTrue(exception.getMessage().contains("b is zero"));
		assertEquals(calculator.getNumberOfOperations(), 0);
	}

	@Test(
		groups = { "divide" },
		dataProvider = "getDivideValues")
	public void testDivide(Integer a, Integer b, Integer expectedResult) {
		// Act
		Integer result = calculator.divide(a, b);

		// Assert
		assertEquals(result, expectedResult);
		assertEquals(calculator.getNumberOfOperations(), 1);
	}
}