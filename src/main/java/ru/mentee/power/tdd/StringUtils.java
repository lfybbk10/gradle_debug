package ru.mentee.power.tdd;

/**
 * Утилитарный класс для работы со строками.
 */
public class StringUtils {

    /**
     * Переворачивает переданную строку.
     *
     * @param str Строка для переворота.
     * @return Перевернутая строка, или null если на входе был null.
     */
    public String reverse(String str) {
        return new StringBuilder(str).reverse().toString(); // Пример возможной реализации
    }
}