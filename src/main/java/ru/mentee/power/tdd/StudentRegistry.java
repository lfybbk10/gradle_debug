package ru.mentee.power.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Student {
    private String name;
    private List<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(int grade) {
        this.grades.add(grade);
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    // Метод для расчета средней оценки
    public double getAverageGrade() {
        if (grades == null || grades.isEmpty()) { // Предварительная проверка
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }
}

public class StudentRegistry {

    private Map<Integer, Student> studentMap = new HashMap<>();

    public void addStudent(int id, Student student) {
        studentMap.put(id, student);
    }

    // Метод, который падает с NPE
    public void printAverageGrades(List<Integer> studentIds) {
        System.out.println("--- Средние оценки студентов ---");
        for (Integer id : studentIds) {
            Student student = studentMap.get(id);

            // Добавим проверку перед использованием student
            if (student != null) {
                double average = student.getAverageGrade(); // Теперь вызываем безопасно
                System.out.printf("Студент ID %d (%s): Средний балл %.2f%n",
                        id, student.getName(), average);
            } else {
                System.out.printf("Студент с ID %d не найден.%n", id); // Сообщение об ошибке
            }
        }
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        StudentRegistry registry = new StudentRegistry();

        Student alice = new Student("Алиса");
        alice.addGrade(5);
        alice.addGrade(4);
        registry.addStudent(101, alice);

        Student bob = new Student("Боб"); // Боб без оценок
        registry.addStudent(102, bob);

        // Список ID студентов для вывода
        List<Integer> idsToPrint = new ArrayList<>();
        idsToPrint.add(101); // Алиса существует
        idsToPrint.add(102); // Боб существует, но без оценок
        idsToPrint.add(103); // Студента с ID 103 не существует!

        try {
            registry.printAverageGrades(idsToPrint);
        } catch (NullPointerException e) {
            System.err.println("ОШИБКА: Программа упала с NullPointerException!");
            // e.printStackTrace(); // Можно раскомментировать для просмотра стека вызовов
        }
    }
}