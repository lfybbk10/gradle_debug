package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для AdvancedCalculator")
class AdvancedCalculatorTest {

    private AdvancedCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new AdvancedCalculator();
    }

    @Test
    @DisplayName("Суммирование обычных чисел")
    void shouldSumNormalNumbers() {
        // Arrange
        List<Integer> numbers = List.of(1, 2, 3);
        int expectedSum = 6;
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        assertThat(actualSum).isEqualTo(expectedSum); // Этот тест должен проходить
    }

    @Test
    @DisplayName("Игнорирование чисел > 1000")
    void shouldIgnoreNumbersGreaterThan1000() {
        // Arrange
        List<Integer> numbers = List.of(2, 1001, 5, 2000);
        int expectedSum = 7;
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        assertThat(actualSum).isEqualTo(expectedSum); // Этот тест тоже должен проходить
    }

    @Test
    @DisplayName("Обработка пустого списка")
    void shouldReturnZeroForEmptyList() {
        // Arrange
        List<Integer> numbers = Collections.emptyList(); // Используем Collections.emptyList()
        int expectedSum = 0;
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        assertThat(actualSum).isEqualTo(expectedSum); // И этот
    }

    // --- Тесты с ошибками и граничные случаи --- //

    @Test
    @DisplayName("Граничный случай: Обработка null списка")
    void shouldReturnZeroForNullList() {
        // Arrange
        List<Integer> numbers = null;
        int expectedSum = 0;
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        // (Подсказка: посмотри внимательно на код AdvancedCalculator)
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Граничный случай: Число ровно 1000")
    void shouldIncludeNumberExactly1000() {
        // Arrange
        List<Integer> numbers = List.of(5, 1000, 10);
        int expectedSum = 1015; // Ожидаем, что 1000 будет включено
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Граничный случай: Список содержит null элементы")
    void shouldHandleNullElementsInList() {
        // Arrange
        List<Integer> numbers = new ArrayList<>(); // Нужен изменяемый список
        numbers.add(10);
        numbers.add(null); // Добавляем null
        numbers.add(20);
        int expectedSum = 30; // Ожидаем, что null будет проигнорирован

        // Act & Assert
        // если элемент списка - null? Как это исправить?
        // Запусти в отладке и посмотри, где падает.
        assertThatCode(() -> calculator.sumIgnoringOver1000(numbers))
                .doesNotThrowAnyException(); // Проверяем, что не падает NPE

        // Дополнительная проверка суммы, если не упало
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Тест с отрицательными числами")
    void shouldHandleNegativeNumbers() {
        // Arrange
        List<Integer> numbers = List.of(-5, 10, -2, 1005);
        int expectedSum = 3; // -5 + 10 - 2 = 3 (1005 игнорируется)
        // Act
        int actualSum = calculator.sumIgnoringOver1000(numbers);
        // Assert
        // Проверь расчеты.
        assertThat(actualSum).isEqualTo(expectedSum);
    }
}