//2-practice pro -2
import java.util.Scanner;

public class ASCIIProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;
            System.out.println("Character: '" + ch + "' ASCII: " + ascii);
            String type = classifyCharacter(ch);
            System.out.println("Type: " + type);
            if (type.equals("Uppercase Letter")) {
                char lower = toggleCase(ch);
                System.out.println("Lowercase: '" + lower + "' ASCII: " + (int) lower);
                System.out.println("Difference: " + ((int) lower - ascii));
            } else if (type.equals("Lowercase Letter")) {
                char upper = toggleCase(ch);
                System.out.println("Uppercase: '" + upper + "' ASCII: " + (int) upper);
                System.out.println("Difference: " + (ascii - (int) upper));
            }
            System.out.println();
        }

        System.out.println("ASCII Table (32-126):");
        displayASCIITable(32, 126);

        System.out.print("Enter text for Caesar cipher: ");
        String text = scanner.nextLine();
        System.out.print("Enter shift value: ");
        int shift = scanner.nextInt();
        String ciphered = caesarCipher(text, shift);
        System.out.println("Ciphered text: " + ciphered);

        scanner.close();
    }

    public static String classifyCharacter(char ch) {
        if (ch >= 'A' && ch <= 'Z') return "Uppercase Letter";
        else if (ch >= 'a' && ch <= 'z') return "Lowercase Letter";
        else if (ch >= '0' && ch <= '9') return "Digit";
        else return "Special Character";
    }

    public static char toggleCase(char ch) {
        if (ch >= 'A' && ch <= 'Z') return (char)(ch + 32);
        else if (ch >= 'a' && ch <= 'z') return (char)(ch - 32);
        else return ch;
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char shifted = (char) ('A' + (ch - 'A' + shift + 26) % 26);
                result.append(shifted);
            } else if (ch >= 'a' && ch <= 'z') {
                char shifted = (char) ('a' + (ch - 'a' + shift + 26) % 26);
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.printf("%3d : %c    ", i, (char)i);
            if ((i - start + 1) % 6 == 0) System.out.println();
        }
        System.out.println();
    }

    public static int[] stringToASCII(String text) {
        int[] asciiArr = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiArr[i] = (int) text.charAt(i);
        }
        return asciiArr;
    }

    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int val : asciiValues) {
            sb.append((char) val);
        }
        return sb.toString();
    }
}
