//Lab pro-6
import java.util.Scanner;

public class CustomTrim {

    public static int[] findTrimIndexes(String str) {
        int start = 0;
        int end = 0;
        int len = 0;

        try {
            while (true) {
                str.charAt(len);
                len++;
            }
        } catch (Exception e) {
            // Done counting
        }

        while (start < len && str.charAt(start) == ' ') {
            start++;
        }

        end = len - 1;
        while (end >= 0 && str.charAt(end) == ' ') {
            end--;
        }

        return new int[] {start, end};
    }

    public static String customSubstring(String str, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static boolean compareStrings(String a, String b) {
        int len1 = 0, len2 = 0;

        try {
            while (true) {
                a.charAt(len1);
                len1++;
            }
        } catch (Exception e) {}

        try {
            while (true) {
                b.charAt(len2);
                len2++;
            }
        } catch (Exception e) {}

        if (len1 != len2) return false;

        for (int i = 0; i < len1; i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string with leading and trailing spaces:\n> ");
        String input = scanner.nextLine();

        int[] indexes = findTrimIndexes(input);
        String customTrimmed = customSubstring(input, indexes[0], indexes[1]);
        String builtInTrimmed = input.trim();

        System.out.println("\nCustom Trimmed String: '" + customTrimmed + "'");
        System.out.println("Built-in Trimmed String: '" + builtInTrimmed + "'");

        boolean isSame = compareStrings(customTrimmed, builtInTrimmed);
        System.out.println("\nAre both trimmed results same? " + isSame);

        scanner.close();
    }
}
