class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    private static int accountCounter = 0;
    private static int totalAccounts = 0;

    public BankAccount(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = (initialDeposit >= 0) ? initialDeposit : 0;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    private static String generateAccountNumber() {
        accountCounter++;
        return "ACC" + String.format("%03d", accountCounter);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " into " + accountNumber);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds in " + accountNumber);
        } else {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from " + accountNumber);
        }
    }

    public void checkBalance() {
        System.out.println("Balance for " + accountNumber + ": $" + balance);
    }

    public void displayAccountInfo() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + accountHolderName);
        System.out.println("Balance        : $" + balance);
        System.out.println("---------------------------");
    }
}

public class BankSystem {
    public static void main(String[] args) {
        BankAccount[] accounts = new BankAccount[5];

        accounts[0] = new BankAccount("Alice", 500);
        accounts[1] = new BankAccount("Bob", 1000);
        accounts[2] = new BankAccount("Charlie", 750);

        accounts[0].deposit(200);
        accounts[1].withdraw(300);
        accounts[2].withdraw(800); // Should show insufficient funds

        for (int i = 0; i < 3; i++) {
            accounts[i].displayAccountInfo();
        }

        System.out.println("Total Bank Accounts Created: " + BankAccount.getTotalAccounts());
    }
}
