//Ass pro - 6
import java.util.Scanner;

public class CharFrequencyNestedLoops {

    public static String[] charFrequency(String str) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int[] freq = new int[len];

        for (int i = 0; i < len; i++) {
            freq[i] = 1;
        }

        for (int i = 0; i < len; i++) {
            if (chars[i] == '0') continue;
            for (int j = i + 1; j < len; j++) {
                if (chars[i] == chars[j]) {
                    freq[i]++;
                    chars[j] = '0';
                }
            }
        }

        int uniqueCount = 0;
        for (int i = 0; i < len; i++) {
            if (chars[i] != '0') uniqueCount++;
        }

        String[] result = new String[uniqueCount];
        int index = 0;
        for (int i = 0; i < len; i++) {
            if (chars[i] != '0') {
                result[index] = chars[i] + " : " + freq[i];
                index++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String[] frequencies = charFrequency(input);

        System.out.println("Character : Frequency");
        for (String s : frequencies) {
            System.out.println(s);
        }

        scanner.close();
    }
}
