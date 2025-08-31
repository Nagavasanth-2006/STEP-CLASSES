//program 3
public class StringArrays {
    public static String findLongestString(String[] names) {
        String longest = "";
        for (String name : names) {
            if (name.length() > longest.length()) {
                longest = name;
            }
        }
        return longest;
    }
    public static int countNamesStartingWith(String[] names, char letter) {
        int count = 0;
        for (String name : names) {
            if (name.charAt(0) == letter) {
                count++;
            }
        }
        return count;
    }

    public static String[] formatNames(String[] names) {
        String[] formatted = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            formatted[i] = names[i].trim().toLowerCase();
        }
        return formatted;
    }

    public static void main(String[] args) {
    String[] students = {"John Smith", "Alice Johnson", "Bob Brown",
    "Carol Davis", "David Wilson"};

    System.out.println(findLongestString(students));
    String [] formatted_names = formatNames(students);
    for (String name : formatted_names) {
        System.out.println(name);
    }
    
    }
}
