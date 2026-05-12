import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            String input = getUserInput(scanner);

            if (isExitCommand(input)) break;

            if (validateInput(input)) continue;

            String normalized = normalizeInput(input);

            double result = parseAndCalculate(normalized);

            calculationResultPrinter(result);

        }
        scanner.close();
    }

    private static String getUserInput(Scanner scanner) {
        System.out.print("Введите выражение: ");
        return scanner.nextLine();
    }

    public static boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("exit")
                || input.equalsIgnoreCase("quit")
                || input.equalsIgnoreCase("q");
    }

    public static String normalizeInput(String input) {
        input = input.trim();
        input = input.replaceAll("[+*/]", " $0 ");
        input = input.replaceAll("(?<=\\d)-", " $0 ");
        input = input.replaceAll(" +", " ");
        return input;
    }

    public static boolean validateInput(String input) {
        String str = (input == null) ? "" : input.trim();

        // 1. Буквы (кириллица или латиница) — неверный формат
        if (str.matches(".*[a-zA-Zа-яА-Я].*")) {
            System.out.println("Ошибка: неверный формат ввода.");
            return true;
        }

        // 2. Символы вне разрешённого набора (не цифры, не . пробел, не + - * /) — некорректный оператор
        if (str.matches(".*[^0-9+\\-*/.\\s].*")) {
            System.out.println("Ошибка: некорректный оператор.");
            return true;
        }

        // 3. Точка в конце числа: "33/3." или ".3/3" — неверный формат
        if (str.matches(".*\\.\\s*[+\\-*/].*") || str.matches(".*[+\\-*/]\\s*\\d*\\.\\s*$")) {
            System.out.println("Ошибка: неверный формат ввода.");
            return true;
        }

        // 4. Подсчёт операторов: должен быть ровно 1 (унарный минус не считается)
        int opsCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0 && (c == '+' || c == '-' || c == '*' || c == '/')) {
                if (c == '-' && "+-*/".indexOf(str.charAt(i - 1)) != -1) continue; // унарный минус
                opsCount++;
            }
        }

        // 5. Структурные ошибки — пустая строка, лишние/отсутствующие операторы, неверное начало/конец
        if (str.isEmpty()
                || str.matches("^[+*/].*")     // начинается с оператора (не минуса)
                || str.matches(".*[+\\-*/]$")  // заканчивается оператором
                || opsCount != 1                     // нет оператора или их несколько
                || str.contains("  ")) {             // два числа через пробел без оператора
            System.out.println("Ошибка: неверный формат ввода.");
            return true;
        }

        // 6. Деление на ноль — ищем '/' за которым стоит '0' (пробелы игнорируются)
        if (str.matches(".*/\\s*0(\\.0*)?($|[^0-9]).*")) {
            System.out.println("Ошибка: деление на ноль невозможно.");
            return true;
        }

        return false; // Ввод прошёл все проверки
    }

    public static double parseAndCalculate(String input) {
        Scanner scanner = new Scanner(input);
        double left = scanner.nextDouble();
        String operator = scanner.next();
        double right = scanner.nextDouble();
        return mathCalculator(operator.charAt(0), left, right);
    }

    public static double mathCalculator(char operator, double left, double right) {
        if (operator == '+') return left + right;
        if (operator == '-') return left - right;
        if (operator == '*') return left * right;
        return left / right;
    }

    public static void calculationResultPrinter(double result) {
        if (result == (long) result) {
            System.out.println("Результат: " + (long) result);
        } else {
            System.out.println("Результат: " + result);
        }
    }
}