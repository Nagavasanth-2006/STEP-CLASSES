//Ass pro=2
import java.util.Scanner;

public class FirstNonRepeatingChar {

    public static char firstNonRepeatingChar(String str) {
        int[] freq = new int[256];
        int len = 0;

        try {
            while (true) {
                str.charAt(len);
                len++;
            }
        } catch (Exception e) {}

        for (int i = 0; i < len; i++) {
            freq[str.charAt(i)]++;
        }

        for (int i = 0; i < len; i++) {
            if (freq[str.charAt(i)] == 1) {
                return str.charAt(i);
            }
        }

        return '\0';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        char result = firstNonRepeatingChar(input);
        if (result == '\0') {
            System.out.println("No non-repeating character found.");
        } else {
            System.out.println("First non-repeating character: " + result);
        }

        scanner.close();
    }
}
