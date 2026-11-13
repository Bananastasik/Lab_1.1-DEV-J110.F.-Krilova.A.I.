package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Country {
    private String name;
    private double area;
    private long population;
    private String capitalName;
    private long capitalPopulation;

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public long getPopulation() {
        return population;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public long getCapitalPopulation() {
        return capitalPopulation;
    }

    public double getPopulationDensity() {
        if (population < 0 || area <= 0) {
            throw new IllegalStateException("Невозможно вычислить плотность: некорректные данные");
        }
        return population / area;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название страны не может быть пустым");
        }
        this.name = name;
    }

    public void setArea(double area) {
        if (area <= 0) {
            throw new IllegalArgumentException("Площадь должна быть строго больше нуля");
        }
        this.area = area;
    }

    public void setPopulation(long population) {
        if (population < 0) {
            throw new IllegalArgumentException("Население не может быть отрицательным");
        }
        this.population = population;
    }

    public void setCapitalName(String capitalName) {
        if (capitalName == null) {
            this.capitalName = null;
            this.capitalPopulation = 0;
        } else {
            this.capitalName = capitalName;
        }
    }

    public void setCapital(String capitalName, long capitalPopulation) {
        if (capitalName == null) {
            this.capitalName = null;
            this.capitalPopulation = 0;
            return;
        }
        if (capitalPopulation < 0) {
            throw new IllegalArgumentException("Население столицы не может быть отрицательным");
        }
        this.capitalName = capitalName;
        this.capitalPopulation = capitalPopulation;
    }

    public Country(String name, double area, long population, String capitalName, long capitalPopulation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название страны не может быть пустым");
        }
        this.name = name;

        if (area <= 0) {
            throw new IllegalArgumentException("Площадь должна быть строго больше нуля");
        }
        this.area = area;

        if (population < 0) {
            throw new IllegalArgumentException("Население не может быть отрицательным");
        }
        this.population = population;

        if (capitalName != null) {
            this.capitalName = capitalName;
            if (capitalPopulation < 0) {
                throw new IllegalArgumentException("Население столицы не может быть отрицательным");
            }
            this.capitalPopulation = capitalPopulation;
        }
    }

    public Country(String name, double area, long population) {
        this(name, area, population, null, 0);
    }

    @Override
    public String toString() {
        String result = name + ": " + area + " км², население: " + population;
        if (capitalName != null && capitalPopulation > 0) {
            result += "\nСтолица: " + capitalName + ", население: " + capitalPopulation;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Country[] countries = new Country[100];
        int count = 0;

        while (true) {
            System.out.println("\nВведите команду:\n" +
                    "create - создать новую страну,\n" +
                    "print  - вывести все добавленные страны,\n" +
                    "exit   - завершить программу.");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "create":

                    System.out.print("Введите название страны: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Название не может быть пустым.");
                        break;
                    }

                    double area;
                    while (true) {
                        System.out.print("Введите площадь (км²): ");
                        try {
                            area = scanner.nextDouble();
                            scanner.nextLine();
                            if (area <= 0) {
                                System.out.println("Площадь должна быть больше нуля. Попробуйте снова.");
                                continue;
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректное число. Попробуйте снова.");
                            scanner.nextLine();
                        }
                    }

                    long population;
                    while (true) {
                        System.out.print("Введите население: ");
                        try {
                            population = scanner.nextLong();
                            scanner.nextLine();
                            if (population < 0) {
                                System.out.println("Население не может быть отрицательным. Попробуйте снова.");
                                continue;
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректное число. Попробуйте снова.");
                            scanner.nextLine();
                        }
                    }

                    String answer;
                    while (true) {
                        System.out.print("Есть ли столица? (y/n): ");
                        answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("y") || answer.equals("n")) {
                            break;
                        }
                        System.out.println("Введите 'y' или 'n'.");
                    }

                    Country country;

                    if (answer.equals("y")) {
                        String capitalName;
                        while (true) {
                            System.out.print("Введите название столицы: ");
                            capitalName = scanner.nextLine().trim();
                            if (!capitalName.isEmpty()) {
                                break;
                            }
                            System.out.println("Название столицы не может быть пустым.");
                        }

                        long capitalPopulation;
                        while (true) {
                            System.out.print("Введите население столицы: ");
                            try {
                                capitalPopulation = scanner.nextLong();
                                scanner.nextLine();
                                if (capitalPopulation <= 0) {
                                    System.out.println("Население столицы должно быть больше нуля. Попробуйте снова.");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Некорректное число. Попробуйте снова.");
                                scanner.nextLine();
                            }
                        }

                        country = new Country(name, area, population, capitalName, capitalPopulation);
                    } else {
                        country = new Country(name, area, population);
                    }

                    countries[count++] = country;
                    System.out.println("Страна добавлена: " + name + ".");
                    break;

                case "print":
                    printAll(countries);
                    break;

                case "exit":
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неизвестная команда. Доступные: create, print, exit.");
            }
        }
    }

    public static void printAll(Country[] countries) {
        if (countries.length == 0 || countries[0] == null) {
            System.out.println("Список стран пуст.");
            return;
        }
        for (Country country : countries) {
            if(country != null) {
                System.out.println(country);
            }
        }
    }
}