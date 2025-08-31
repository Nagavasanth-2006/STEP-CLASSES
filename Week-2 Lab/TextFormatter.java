import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFormatter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to format:");
        String inputText = scanner.nextLine();

        System.out.println("Enter desired line width:");
        int width = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<String> words = splitWords(inputText);
        List<String> justifiedText = justifyText(words, width);
        List<String> centerAlignedText = centerAlignText(justifiedText, width);

        System.out.println("\nOriginal Text:");
        System.out.println(inputText);

        System.out.println("\nJustified Text:");
        displayFormattedText(justifiedText);

        System.out.println("\nCenter-Aligned Text:");
        displayFormattedText(centerAlignedText);

        comparePerformance(words, width);
    }

    public static List<String> splitWords(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (start < i) {
                    words.add(text.substring(start, i));
                }
                start = i + 1;
            }
        }

        if (start < text.length()) {
            words.add(text.substring(start));
        }

        return words;
    }

    public static List<String> justifyText(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int i = 0;

        while (i < words.size()) {
            int lineLength = words.get(i).length();
            int j = i + 1;

            while (j < words.size() && (lineLength + 1 + words.get(j).length()) <= width) {
                lineLength += 1 + words.get(j).length();
                j++;
            }

            int spaceSlots = j - i - 1;
            StringBuilder line = new StringBuilder();

            if (j == words.size() || spaceSlots == 0) {
                for (int k = i; k < j; k++) {
                    line.append(words.get(k));
                    if (k != j - 1) line.append(' ');
                }

                while (line.length() < width) {
                    line.append(' ');
                }
            } else {
                int totalSpaces = width;
                for (int k = i; k < j; k++) {
                    totalSpaces -= words.get(k).length();
                }

                int spacePerSlot = totalSpaces / spaceSlots;
                int extra = totalSpaces % spaceSlots;

                for (int k = i; k < j; k++) {
                    line.append(words.get(k));
                    if (k != j - 1) {
                        int spaces = spacePerSlot + (extra-- > 0 ? 1 : 0);
                        for (int s = 0; s < spaces; s++) line.append(' ');
                    }
                }
            }

            lines.add(line.toString());
            i = j;
        }

        return lines;
    }

    public static List<String> centerAlignText(List<String> lines, int width) {
        List<String> centeredLines = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.stripTrailing();
            int padding = (width - trimmed.length()) / 2;
            StringBuilder centered = new StringBuilder();

            for (int i = 0; i < padding; i++) centered.append(' ');
            centered.append(trimmed);
            centeredLines.add(centered.toString());
        }

        return centeredLines;
    }

    public static void displayFormattedText(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.printf("Line %2d (%2d chars): %s\n", i + 1, line.length(), line);
        }
    }

    public static void comparePerformance(List<String> words, int width) {
        long startBuilder = System.nanoTime();
        justifyText(words, width);
        long endBuilder = System.nanoTime();

        long startConcat = System.nanoTime();
        justifyTextUsingConcat(words, width);
        long endConcat = System.nanoTime();

        long timeBuilder = endBuilder - startBuilder;
        long timeConcat = endConcat - startConcat;

        System.out.println("\nPerformance Comparison:");
        System.out.println("--------------------------");
        System.out.println("Using StringBuilder: " + timeBuilder + " ns");
        System.out.println("Using String Concatenation: " + timeConcat + " ns");
        System.out.println("StringBuilder is " + (timeConcat / (double) timeBuilder) + "x faster (approx)");
    }

    public static List<String> justifyTextUsingConcat(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int i = 0;

        while (i < words.size()) {
            int lineLength = words.get(i).length();
            int j = i + 1;

            while (j < words.size() && (lineLength + 1 + words.get(j).length()) <= width) {
                lineLength += 1 + words.get(j).length();
                j++;
            }

            int spaceSlots = j - i - 1;
            String line = "";

            if (j == words.size() || spaceSlots == 0) {
                for (int k = i; k < j; k++) {
                    line += words.get(k);
                    if (k != j - 1) line += ' ';
                }

                while (line.length() < width) {
                    line += ' ';
                }
            } else {
                int totalSpaces = width;
                for (int k = i; k < j; k++) {
                    totalSpaces -= words.get(k).length();
                }

                int spacePerSlot = totalSpaces / spaceSlots;
                int extra = totalSpaces % spaceSlots;

                for (int k = i; k < j; k++) {
                    line += words.get(k);
                    if (k != j - 1) {
                        int spaces = spacePerSlot + (extra-- > 0 ? 1 : 0);
                        for (int s = 0; s < spaces; s++) line += ' ';
                    }
                }
            }

            lines.add(line);
            i = j;
        }

        return lines;
    }
}
