//Ass pro-5
import java.util.Scanner;

public class AnagramChecker {

    public static int getLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (Exception e) {}
        return count;
    }

    public static boolean areAnagrams(String s1, String s2) {
        int len1 = getLength(s1);
        int len2 = getLength(s2);
        if (len1 != len2) return false;

        int[] freq1 = new int[256];
        int[] freq2 = new int[256];

        for (int i = 0; i < len1; i++) {
            freq1[s1.charAt(i)]++;
            freq2[s2.charAt(i)]++;
        }

        for (int i = 0; i < 256; i++) {
            if (freq1[i] != freq2[i]) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first text: ");
        String text1 = scanner.nextLine();
        System.out.print("Enter second text: ");
        String text2 = scanner.nextLine();

        if (areAnagrams(text1, text2)) {
            System.out.println("The texts are anagrams.");
        } else {
            System.out.println("The texts are NOT anagrams.");
        }

        scanner.close();
    }
}
