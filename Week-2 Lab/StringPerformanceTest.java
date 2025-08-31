import java.util.Scanner;

public class StringPerformanceTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of iterations:");
        int iterations = scanner.nextInt();

        Result stringResult = useStringConcat(iterations);
        Result builderResult = useStringBuilder(iterations);
        Result bufferResult = useStringBuffer(iterations);

        displayResults(stringResult, builderResult, bufferResult);
    }

    static class Result {
        String method;
        long timeTaken;
        int finalLength;

        Result(String method, long timeTaken, int finalLength) {
            this.method = method;
            this.timeTaken = timeTaken;
            this.finalLength = finalLength;
        }
    }

    public static Result useStringConcat(int iterations) {
        String text = "";
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            text += "abc";
        }
        long end = System.currentTimeMillis();
        return new Result("String", end - start, text.length());
    }

    public static Result useStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            sb.append("abc");
        }
        long end = System.currentTimeMillis();
        return new Result("StringBuilder", end - start, sb.length());
    }

    public static Result useStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            sb.append("abc");
        }
        long end = System.currentTimeMillis();
        return new Result("StringBuffer", end - start, sb.length());
    }

    public static void displayResults(Result stringRes, Result builderRes, Result bufferRes) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-15s\n", "Method", "Time (ms)", "Final Length");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s | %-15d | %-15d\n", stringRes.method, stringRes.timeTaken, stringRes.finalLength);
        System.out.printf("%-15s | %-15d | %-15d\n", builderRes.method, builderRes.timeTaken, builderRes.finalLength);
        System.out.printf("%-15s | %-15d | %-15d\n", bufferRes.method, bufferRes.timeTaken, bufferRes.finalLength);
        System.out.println("-------------------------------------------------------------");

        System.out.println("\nMemory Efficiency (Fastest to Slowest):");
        if (builderRes.timeTaken <= bufferRes.timeTaken && builderRes.timeTaken <= stringRes.timeTaken) {
            System.out.println("1. StringBuilder");
            if (bufferRes.timeTaken <= stringRes.timeTaken) {
                System.out.println("2. StringBuffer");
                System.out.println("3. String");
            } else {
                System.out.println("2. String");
                System.out.println("3. StringBuffer");
            }
        } else if (bufferRes.timeTaken <= builderRes.timeTaken && bufferRes.timeTaken <= stringRes.timeTaken) {
            System.out.println("1. StringBuffer");
            if (builderRes.timeTaken <= stringRes.timeTaken) {
                System.out.println("2. StringBuilder");
                System.out.println("3. String");
            } else {
                System.out.println("2. String");
                System.out.println("3. StringBuilder");
            }
        } else {
            System.out.println("1. String");
            if (builderRes.timeTaken <= bufferRes.timeTaken) {
                System.out.println("2. StringBuilder");
                System.out.println("3. StringBuffer");
            } else {
                System.out.println("2. StringBuffer");
                System.out.println("3. StringBuilder");
            }
        }
    }
}
