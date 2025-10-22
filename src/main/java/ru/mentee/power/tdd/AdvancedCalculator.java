package ru.mentee.power.tdd;

import java.util.List;

/**
 * Суммирует числа в списке, игнорируя значения > 1000.
 */
public class AdvancedCalculator {

    public int sumIgnoringOver1000(List<Integer> numbers) {
        if (numbers == null) { // Обработка null списка
            return 0;
        }
        int sum = 0;
        for (Integer number : numbers) {
            if (number != null && number <= 1000) {
                sum += number;
            }
        }
        // (В данном случае int достаточно, но это пример возможной проблемы)
        return sum;
    }
}