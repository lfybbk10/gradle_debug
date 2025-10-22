package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для утилиты работы со строками")
class StringUtilsTest {

    private StringUtils stringUtils; // Тестируемый объект

    // Метод @BeforeEach выполняется перед каждым тестом
    @BeforeEach
    void setUp() {
        stringUtils = new StringUtils(); // Создаем объект перед тестом
    }

    @Test
    @DisplayName("Переворот обычной строки")
    void shouldReverseNormalString() {
        // Arrange
        String original = "hello";
        String expected = "olleh";

        String actual = stringUtils.reverse(original);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Переворот пустой строки")
    void shouldReturnEmptyStringWhenInputIsEmpty() {
        String original = "";
        String expected = "";

        String actual = stringUtils.reverse(original);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Возврат null при null на входе")
    void shouldReturnNullWhenInputIsNull() {
        String original = null;

        String actual = stringUtils.reverse(original);

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Переворот строки с одним символом")
    void shouldReturnSameStringWhenSingleCharacter() {
        // Arrange
        String original = "a";
        String expected = "a";

        String actual = stringUtils.reverse(original);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Переворот строки-палиндрома")
    void shouldReturnSameStringForPalindrome() {
        String original = "madam";
        String expected = "madam";

        String actual = stringUtils.reverse(original);
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Добавь еще один тестовый случай (например, строка с пробелами или цифрами)
    @Test
    @DisplayName("Переворот строки с проблеми и цифрами")
    void shouldReturnSameStringWhenSpacesAndDigits() {
        String original = "ad 12 test";
        String expected = "tset 21 da";
        String actual = stringUtils.reverse(original);
        assertThat(actual).isEqualTo(expected);
    }
}