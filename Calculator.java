import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print("Введите выражение: ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            double left = 0;
            double right = 0;
            char operator = ' ';
            boolean operatorFound = false;
            boolean hasError = false;

            // Собираем левое число, оператор и правое число
            double currentNumber = 0;
            boolean buildingNumber = false;

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);

                if (c >= '0' && c <= '9') {
                    currentNumber = currentNumber * 10 + (c - '0');
                    buildingNumber = true;

                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (!operatorFound) {
                        left = currentNumber;
                        operator = c;
                        operatorFound = true;
                        currentNumber = 0;
                        buildingNumber = false;
                    } else {
                        // Второй оператор — неверный формат
                        hasError = true;
                        break;
                    }

                } else {
                    // Любой другой символ — неверный формат
                    hasError = true;
                    break;
                }
            }

            if (hasError || !operatorFound || !buildingNumber) {
                System.out.println("Ошибка: неверный формат ввода.");
                continue;
            }

            right = currentNumber;

            // Вычисляем результат
            double result = 0;

            if (operator == '+') {
                result = left + right;
            } else if (operator == '-') {
                result = left - right;
            } else if (operator == '*') {
                result = left * right;
            } else if (operator == '/') {
                if (right == 0) {
                    System.out.println("Ошибка: деление на ноль невозможно.");
                    continue;
                }
                result = left / right;
            } else {
                System.out.println("Ошибка: некорректный оператор.");
                continue;
            }

            // Печатаем красиво: без ".0" если число целое
            if (result == (long) result) {
                System.out.println("Результат: " + (long) result);
            } else {
                System.out.println("Результат: " + result);
            }
        }
        scanner.close();
    }
}