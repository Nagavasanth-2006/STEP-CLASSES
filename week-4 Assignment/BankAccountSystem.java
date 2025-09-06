import java.util.Random;

class BankAccount {
    String accountHolder;
    int accountNumber;
    double balance;

    BankAccount() {
        this.accountHolder = "Unknown";
        this.accountNumber = 0;
        this.balance = 0;
    }

    BankAccount(String accountHolder) {
        this.accountHolder = accountHolder;
        this.accountNumber = generateAccountNumber();
        this.balance = 0;
    }

    BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = generateAccountNumber();
        this.balance = initialBalance;
    }

    private int generateAccountNumber() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }

    void withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    void displayAccount() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("---------------------");
    }
}

public class BankAccountSystem {
    public static void main(String[] args) {
        BankAccount a1 = new BankAccount();
        BankAccount a2 = new BankAccount("Alice");
        BankAccount a3 = new BankAccount("Bob", 1000);

        a1.deposit(500);
        a1.withdraw(100);

        a2.deposit(700);
        a2.withdraw(300);

        a3.deposit(500);
        a3.withdraw(2000); // should print error

        a1.displayAccount();
        a2.displayAccount();
        a3.displayAccount();
    }
}
