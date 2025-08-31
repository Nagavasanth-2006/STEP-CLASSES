public class BankAccount {
    // Static variables
    private static String bankName;
    private static int totalAccounts = 0;
    private static double interestRate;

    // Instance variables
    private String accountNumber;
    private String accountHolder;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        totalAccounts++;
    }

    // Static methods
    public static void setBankName(String name) {
        bankName = name;
    }

    public static void setInterestRate(double rate) {
        interestRate = rate;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void displayBankInfo() {
        System.out.println("Bank Name: " + bankName);
        System.out.println("Total Accounts: " + totalAccounts);
        System.out.println("Interest Rate: " + interestRate + "%");
    }

    // Instance methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(accountHolder + " deposited $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println(accountHolder + " withdrew $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public double calculateInterest() {
        return balance * (interestRate / 100);
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
        System.out.println("Interest Earned: $" + calculateInterest());
    }

    public static void main(String[] args) {
        // Set static members using static methods
        BankAccount.setBankName("Global Bank");
        BankAccount.setInterestRate(3.5);

        // Create BankAccount objects (instance members are unique)
        BankAccount acc1 = new BankAccount("001", "Alice", 1000);
        BankAccount acc2 = new BankAccount("002", "Bob", 2000);

        // Demonstrate static members (shared across all objects)
        System.out.println("Bank Information:");
        BankAccount.displayBankInfo();

        System.out.println("\nAccount 1 Info:");
        acc1.displayAccountInfo();

        System.out.println("\nAccount 2 Info:");
        acc2.displayAccountInfo();

        // Operations on individual accounts
        acc1.deposit(500);
        acc2.withdraw(300);

        System.out.println("\nAfter Transactions:");

        System.out.println("Account 1 Info:");
        acc1.displayAccountInfo();

        System.out.println("\nAccount 2 Info:");
        acc2.displayAccountInfo();

        // Calling static method with object (not recommended, but allowed)
        System.out.println("\nTotal Accounts (called via object): " + acc1.getTotalAccounts());

        // Recommended: call static method using class name
        System.out.println("Total Accounts (called via class): " + BankAccount.getTotalAccounts());
    }
}
