import java.util.*;

public class TextCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of expressions to evaluate:");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter expression #" + (i + 1) + ":");
            String expression = scanner.nextLine().replaceAll("\\s+", "");

            if (!isValidExpression(expression)) {
                System.out.println("Invalid expression. Skipping...");
                continue;
            }

            System.out.println("\nEvaluation steps:");
            StringBuilder steps = new StringBuilder();
            int result = evaluateWithParentheses(expression, steps);

            System.out.println(steps);
            System.out.println("Final Result: " + result);
        }
    }

    // a. Validate characters and parentheses
    public static boolean isValidExpression(String expr) {
        int parenBalance = 0;

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            int ascii = (int) ch;

            if (!(ascii >= 48 && ascii <= 57) && // digits
                ch != '+' && ch != '-' && ch != '*' && ch != '/' &&
                ch != '(' && ch != ')') {
                return false;
            }

            if (ch == '(') parenBalance++;
            if (ch == ')') parenBalance--;

            if (parenBalance < 0) return false; // unmatched closing
        }

        return parenBalance == 0;
    }

    // b. Parse numbers and operators
    public static void parseExpression(String expr, List<Integer> nums, List<Character> ops) {
        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                int start = i;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    i++;
                }
                nums.add(Integer.parseInt(expr.substring(start, i)));
            } else {
                ops.add(ch);
                i++;
            }
        }
    }

    // d. Evaluate without parentheses
    public static int evaluateFlat(String expr, StringBuilder steps) {
        List<Integer> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        parseExpression(expr, nums, ops);

        // First pass: * and /
        for (int i = 0; i < ops.size(); ) {
            char op = ops.get(i);
            if (op == '*' || op == '/') {
                int a = nums.get(i);
                int b = nums.get(i + 1);
                int res = (op == '*') ? a * b : a / b;

                steps.append(String.format("=> %d %c %d = %d\n", a, op, b, res));

                nums.set(i, res);
                nums.remove(i + 1);
                ops.remove(i);
            } else {
                i++;
            }
        }

        // Second pass: + and -
        int result = nums.get(0);
        for (int i = 0; i < ops.size(); i++) {
            char op = ops.get(i);
            int next = nums.get(i + 1);
            int prev = result;
            if (op == '+') result += next;
            if (op == '-') result -= next;
            steps.append(String.format("=> %d %c %d = %d\n", prev, op, next, result));
        }

        return result;
    }

    // e. Handle parentheses
    public static int evaluateWithParentheses(String expr, StringBuilder steps) {
        while (expr.contains("(")) {
            int close = expr.indexOf(')');
            int open = expr.lastIndexOf('(', close);

            String inner = expr.substring(open + 1, close);
            int innerResult = evaluateFlat(inner, steps);

            steps.append("Evaluated (" + inner + ") = " + innerResult + "\n");

            expr = expr.substring(0, open) + innerResult + expr.substring(close + 1);
        }

        return evaluateFlat(expr, steps);
    }
}
