//ass pro-4
import java.util.Scanner;

public class CharFrequencyUsingUnique {

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

    public static char[] uniqueCharacters(String str) {
        int len = getLength(str);
        char[] temp = new char[len];
        int uniqueCount = 0;

        for (int i = 0; i < len; i++) {
            char current = str.charAt(i);
            boolean isUnique = true;
            for (int j = 0; j < uniqueCount; j++) {
                if (temp[j] == current) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                temp[uniqueCount] = current;
                uniqueCount++;
            }
        }

        char[] uniqueChars = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            uniqueChars[i] = temp[i];
        }
        return uniqueChars;
    }

    public static String[][] charFrequency(String str) {
        int[] freq = new int[256];
        int len = getLength(str);

        for (int i = 0; i < len; i++) {
            freq[str.charAt(i)]++;
        }

        char[] uniqueChars = uniqueCharacters(str);
        String[][] result = new String[uniqueChars.length][2];

        for (int i = 0; i < uniqueChars.length; i++) {
            result[i][0] = Character.toString(uniqueChars[i]);
            result[i][1] = String.valueOf(freq[uniqueChars[i]]);
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String[][] frequencies = charFrequency(input);

        System.out.println("Character\tFrequency");
        for (String[] pair : frequencies) {
            System.out.println(pair[0] + "\t\t" + pair[1]);
        }

        scanner.close();
    }
}
