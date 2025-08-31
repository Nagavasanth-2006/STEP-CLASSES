import java.util.Scanner;

public class CaesarCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to encrypt:");
        String text = scanner.nextLine();

        System.out.println("Enter shift value:");
        int shift = scanner.nextInt();

        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("\nOriginal Text and ASCII:");
        displayAscii(text);

        System.out.println("\nEncrypted Text and ASCII:");
        displayAscii(encrypted);

        System.out.println("\nDecrypted Text and ASCII:");
        displayAscii(decrypted);

        System.out.println("\nDecryption Valid: " + text.equals(decrypted));
    }

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                char c = (char) (((ch - 'A' + shift) % 26 + 26) % 26 + 'A');
                result.append(c);
            } else if (ch >= 'a' && ch <= 'z') {
                char c = (char) (((ch - 'a' + shift) % 26 + 26) % 26 + 'a');
                result.append(c);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }

    public static void displayAscii(String text) {
        System.out.println("Text: " + text);
        System.out.print("ASCII: ");
        for (char ch : text.toCharArray()) {
            System.out.print((int) ch + " ");
        }
        System.out.println();
    }
}
