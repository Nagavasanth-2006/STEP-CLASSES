import java.util.Scanner;

public class CaseConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text:");
        String text = scanner.nextLine();

        String upper = toUpperCase(text);
        String lower = toLowerCase(text);
        String title = toTitleCase(text);

        String builtInUpper = text.toUpperCase();
        String builtInLower = text.toLowerCase();

        boolean upperMatch = compareResults(upper, builtInUpper);
        boolean lowerMatch = compareResults(lower, builtInLower);

        System.out.println("\nConversion Results:");
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s | %-30s\n", "Conversion", "Result");
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s | %-30s\n", "Uppercase", upper);
        System.out.printf("%-15s | %-30s\n", "Lowercase", lower);
        System.out.printf("%-15s | %-30s\n", "Title Case", title);
        System.out.println("-----------------------------------------------");

        System.out.println("\nComparison with Built-in Methods:");
        System.out.println("Uppercase Match: " + upperMatch);
        System.out.println("Lowercase Match: " + lowerMatch);
    }

    public static String toUpperCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                result.append((char)(ch - 32));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String toLowerCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                result.append((char)(ch + 32));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String toTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean startOfWord = true;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == ' ') {
                result.append(ch);
                startOfWord = true;
            } else if (startOfWord && ch >= 'a' && ch <= 'z') {
                result.append((char)(ch - 32));
                startOfWord = false;
            } else if (!startOfWord && ch >= 'A' && ch <= 'Z') {
                result.append((char)(ch + 32));
            } else {
                result.append(ch);
                startOfWord = false;
            }
        }
        return result.toString();
    }

    public static boolean compareResults(String custom, String builtIn) {
        return custom.equals(builtIn);
    }
}
