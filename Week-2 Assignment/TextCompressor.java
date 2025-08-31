import java.util.*;

public class TextCompressor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to compress:");
        String input = scanner.nextLine();

        Object[] freqResult = countFrequencies(input);
        char[] chars = (char[]) freqResult[0];
        int[] freqs = (int[]) freqResult[1];

        String[][] codeMap = generateCodes(chars, freqs);
        String compressed = compressText(input, codeMap);
        String decompressed = decompressText(compressed, codeMap);

        displayAnalysis(chars, freqs, codeMap, input, compressed, decompressed);
    }

    public static Object[] countFrequencies(String text) {
        List<Character> charList = new ArrayList<>();
        List<Integer> freqList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int index = charList.indexOf(ch);

            if (index == -1) {
                charList.add(ch);
                freqList.add(1);
            } else {
                freqList.set(index, freqList.get(index) + 1);
            }
        }

        char[] chars = new char[charList.size()];
        int[] freqs = new int[charList.size()];

        for (int i = 0; i < charList.size(); i++) {
            chars[i] = charList.get(i);
            freqs[i] = freqList.get(i);
        }

        // Sort by frequency descending
        for (int i = 0; i < freqs.length - 1; i++) {
            for (int j = i + 1; j < freqs.length; j++) {
                if (freqs[j] > freqs[i]) {
                    int tempFreq = freqs[i];
                    freqs[i] = freqs[j];
                    freqs[j] = tempFreq;

                    char tempChar = chars[i];
                    chars[i] = chars[j];
                    chars[j] = tempChar;
                }
            }
        }

        return new Object[]{chars, freqs};
    }

    public static String[][] generateCodes(char[] chars, int[] freqs) {
        String[] codes = new String[chars.length];
        String symbols = "!@#$%^&*()_+=<>?/1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < chars.length; i++) {
            if (i < symbols.length()) {
                codes[i] = String.valueOf(symbols.charAt(i));
            } else {
                codes[i] = "{" + i + "}"; // fallback if symbols exhausted
            }
        }

        String[][] map = new String[chars.length][2];
        for (int i = 0; i < chars.length; i++) {
            map[i][0] = String.valueOf(chars[i]);
            map[i][1] = codes[i];
        }

        return map;
    }

    public static String compressText(String text, String[][] map) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            for (String[] pair : map) {
                if (pair[0].charAt(0) == ch) {
                    sb.append(pair[1]);
                    break;
                }
            }
        }

        return sb.toString();
    }

    public static String decompressText(String compressed, String[][] map) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < compressed.length()) {
            boolean matched = false;

            for (String[] pair : map) {
                String code = pair[1];

                if (compressed.startsWith(code, i)) {
                    result.append(pair[0]);
                    i += code.length();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                result.append('?'); // unknown character
                i++;
            }
        }

        return result.toString();
    }

    public static void displayAnalysis(char[] chars, int[] freqs, String[][] map,
                                       String original, String compressed, String decompressed) {

        System.out.println("\nCharacter Frequency Table:");
        System.out.println("---------------------------");
        System.out.printf("%-10s | %-10s\n", "Character", "Frequency");
        System.out.println("---------------------------");
        for (int i = 0; i < chars.length; i++) {
            String displayChar = (chars[i] == ' ') ? "[space]" : String.valueOf(chars[i]);
            System.out.printf("%-10s | %-10d\n", displayChar, freqs[i]);
        }

        System.out.println("\nCompression Mapping:");
        System.out.println("---------------------------");
        System.out.printf("%-10s | %-10s\n", "Char", "Code");
        System.out.println("---------------------------");
        for (String[] pair : map) {
            String displayChar = (pair[0].equals(" ")) ? "[space]" : pair[0];
            System.out.printf("%-10s | %-10s\n", displayChar, pair[1]);
        }

        System.out.println("\nOriginal Text:\n" + original);
        System.out.println("\nCompressed Text:\n" + compressed);
        System.out.println("\nDecompressed Text:\n" + decompressed);

        double originalSize = original.length();
        double compressedSize = compressed.length();
        double efficiency = ((originalSize - compressedSize) / originalSize) * 100;

        System.out.printf("\nCompression Efficiency: %.2f%%\n", efficiency);
        System.out.println("Compression Ratio: " + original.length() + " : " + compressed.length());

        System.out.println("\nDecompression " + (original.equals(decompressed) ? "Successful ✅" : "Failed ❌"));
    }
}
