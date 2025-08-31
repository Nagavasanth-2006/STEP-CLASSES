import java.util.*;

public class SubstringReplace {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the main text:");
        String text = scanner.nextLine();

        System.out.println("Enter the substring to find:");
        String find = scanner.nextLine();

        System.out.println("Enter the replacement substring:");
        String replace = scanner.nextLine();

        int[] positions = findOccurrences(text, find);
        String customResult = manualReplace(text, find, replace, positions);
        String builtInResult = text.replace(find, replace);
        boolean isEqual = compareResults(customResult, builtInResult);

        System.out.println("Custom replaced text: " + customResult);
        System.out.println("Built-in replaced text: " + builtInResult);
        System.out.println("Match with built-in replace: " + isEqual);
    }

    public static int[] findOccurrences(String text, String target) {
        List<Integer> posList = new ArrayList<>();
        int index = 0;
        while ((index = text.indexOf(target, index)) != -1) {
            posList.add(index);
            index += target.length();
        }
        int[] positions = new int[posList.size()];
        for (int i = 0; i < posList.size(); i++) {
            positions[i] = posList.get(i);
        }
        return positions;
    }

    public static String manualReplace(String text, String find, String replace, int[] positions) {
        StringBuilder result = new StringBuilder();
        int i = 0, posIndex = 0;
        while (i < text.length()) {
            if (posIndex < positions.length && i == positions[posIndex]) {
                result.append(replace);
                i += find.length();
                posIndex++;
            } else {
                result.append(text.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

    public static boolean compareResults(String custom, String builtIn) {
        return custom.equals(builtIn);
    }
}
