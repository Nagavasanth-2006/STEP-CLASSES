class Employee {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;
    private static int totalEmployees = 0;

    private double salary;
    private double tax;

    public Employee(String empId, String empName, String department, double baseSalary, double bonus) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.baseSalary = baseSalary + bonus;
        this.empType = "Full-Time";
        totalEmployees++;
    }

    public Employee(String empId, String empName, String department, double hourlyRate, int hours) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.baseSalary = hourlyRate * hours;
        this.empType = "Part-Time";
        totalEmployees++;
    }

    public Employee(String empId, String empName, String department, double fixedAmount) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.baseSalary = fixedAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    public void calculateSalary(double bonus) {
        if (empType.equals("Full-Time")) {
            salary = baseSalary + bonus;
        }
    }

    public void calculateSalary(double hourlyRate, int hours) {
        if (empType.equals("Part-Time")) {
            salary = hourlyRate * hours;
        }
    }

    public void calculateSalary() {
        if (empType.equals("Contract")) {
            salary = baseSalary;
        }
    }

    public void calculateTax(double rate) {
        tax = salary * rate;
    }

    public void calculateTax() {
        if (empType.equals("Full-Time")) {
            tax = salary * 0.2;
        } else if (empType.equals("Part-Time")) {
            tax = salary * 0.1;
        } else if (empType.equals("Contract")) {
            tax = salary * 0.15;
        }
    }

    public void generatePaySlip() {
        System.out.println("----- Pay Slip -----");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + department);
        System.out.println("Employee Type: " + empType);
        System.out.println("Salary: " + salary);
        System.out.println("Tax: " + tax);
        System.out.println("--------------------");
    }

    public void displayEmployeeInfo() {
        System.out.println(empId + " | " + empName + " | " + department + " | " + empType);
    }

    public static void displayTotalEmployees() {
        System.out.println("Total Employees: " + totalEmployees);
    }

    public static void generateCompanyPayroll(Employee[] employees) {
        System.out.println("=== Company Payroll Report ===");
        for (Employee e : employees) {
            e.generatePaySlip();
        }
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Employee e1 = new Employee("E001", "Alice", "HR", 50000, 5000);
        e1.calculateSalary(5000);
        e1.calculateTax();
        e1.generatePaySlip();

        Employee e2 = new Employee("E002", "Bob", "Sales", 200, 80);
        e2.calculateSalary(200, 80);
        e2.calculateTax();
        e2.generatePaySlip();

        Employee e3 = new Employee("E003", "Charlie", "IT", 40000);
        e3.calculateSalary();
        e3.calculateTax();
        e3.generatePaySlip();

        e1.displayEmployeeInfo();
        e2.displayEmployeeInfo();
        e3.displayEmployeeInfo();

        Employee.displayTotalEmployees();
        Employee[] allEmployees = {e1, e2, e3};
        Employee.generateCompanyPayroll(allEmployees);
    }
}
