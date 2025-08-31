//program 1
public class StringManipulation
{
    public static void main(String[] args)
    {
        String str1 = "Java Programming";
        String str2 = new String("Java Programming");
        char[] charArray = {'J', 'a', 'v', 'a', ' ', 'P', 'r', 'o', 'g', 'r', 'a', 'm', 'm', 'i', 'n', 'g'};
        String str3= new String(charArray);

        System.out.println("Comparing with ==");
        System.out.println("Str1==Str2:" +str1==str2);
        System.out.println("Str1==Str3:" +str1==str3);

        System.out.println("\nComparing witg .equals():");
        System.out.println("str1.equals(str2):" +str1.equals(str2));
        System.out.println("str1.equals(str2):" +str1.equals(str3));

         System.out.println("\nExplanation:");
        System.out.println("== compares object references (memory addresses),");
        System.out.println(".equals() compares the actual string contents.");

        String quote = "Programming Quote:\n\"Code is poetry\" - Unknown\nPath: C:\\Java\\Projects";
        System.out.println("\n" + quote);

    }
    
}
