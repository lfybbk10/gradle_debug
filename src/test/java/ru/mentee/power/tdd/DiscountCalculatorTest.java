package ru.mentee.power.tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты для DiscountCalculator")
class DiscountCalculatorTest {

  private DiscountCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new DiscountCalculator();
  }

  @Test
  @DisplayName("Скидка 0% для суммы <= 1000")
  void shouldApplyZeroDiscountForAmountLessOrEqual1000() {
    // Arrange
    double amount = 800.0;
    double expectedPrice = 800.0;
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Скидка 10% для суммы > 1000 и <= 5000")
  void shouldApply10PercentDiscountForAmountBetween1000And5000() {
    // Arrange
    double amount = 1200.0;
    double expectedPrice = 1080.0; // 1200 * 0.9
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Скидка 20% для суммы > 5000")
  void shouldApply20PercentDiscountForAmountGreaterThan5000() {
    // Arrange
    double amount = 6000.0;
    double expectedPrice = 4800.0; // 6000 * 0.8
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Граничный случай: Скидка 0% для суммы ровно 1000")
  void shouldApplyZeroDiscountForAmountExactly1000() {
    // Arrange
    double amount = 1000.0;
    double expectedPrice = 1000.0;
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Граничный случай: Скидка 10% для суммы 1000.1")
  void shouldApplyZeroDiscountForAmountGreaterThan1000() {
    // Arrange
    double amount = 1000.1;
    double expectedPrice = 900.09;
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Граничный случай: Скидка 10% для суммы 5000")
  void shouldApplyZeroDiscountForAmountExactly5000() {
    // Arrange
    double amount = 5000;
    double expectedPrice = 4500;
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }

  @Test
  @DisplayName("Граничный случай: Скидка 20% для суммы 5000.1")
  void shouldApplyZeroDiscountForAmountGreaterThan5000() {
    // Arrange
    double amount = 5000.1;
    double expectedPrice = 4000.08;
    // Act
    double actualPrice = calculator.calculateDiscountedPrice(amount);
    // Assert
    assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
  }
}
