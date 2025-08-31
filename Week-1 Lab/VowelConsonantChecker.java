//LAB PRO - 4
import java.util.Scanner;

public class VowelConsonantChecker {

    public static String getCharType(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char)(c + 32);
        }

        if (c >= 'a' && c <= 'z') {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return "Vowel";
            } else {
                return "Consonant";
            }
        } else {
            return "Not a Letter";
        }
    }

    public static String[][] analyzeString(String str) {
        int len = 0;
        try {
            while (true) {
                str.charAt(len);
                len++;
            }
        } catch (Exception e) {}

        String[][] result = new String[len][2];
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            result[i][0] = String.valueOf(ch);
            result[i][1] = getCharType(ch);
        }
        return result;
    }

    public static void displayResult(String[][] data) {
        System.out.println("\nCharacter\tType");
        System.out.println("-------------------------");
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0] + "\t\t" + data[i][1]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String[][] analysis = analyzeString(input);
        displayResult(analysis);

        scanner.close();
    }
}
