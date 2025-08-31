import java.util.*;

class Employee {
    String empId, empName, department, designation;
    double baseSalary;
    String joinDate;
    boolean[] attendanceRecord = new boolean[30];

    static int totalEmployees = 0;
    static String companyName = "TechCorp";
    static double totalSalaryExpense = 0;
    static int workingDaysPerMonth = 22;

    Employee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.designation = designation;
        this.baseSalary = baseSalary;
        this.joinDate = joinDate;
        totalEmployees++;
    }

    void markAttendance(int day, boolean present) {
        if (day >= 1 && day <= 30) {
            attendanceRecord[day - 1] = present;
        }
    }

    int getDaysPresent() {
        int count = 0;
        for (boolean present : attendanceRecord) {
            if (present) count++;
        }
        return count;
    }

    double calculateSalary() {
        return baseSalary;
    }

    double calculateBonus() {
        int daysPresent = getDaysPresent();
        if (daysPresent >= workingDaysPerMonth) {
            return baseSalary * 0.10;
        } else if (daysPresent >= workingDaysPerMonth - 2) {
            return baseSalary * 0.05;
        } else {
            return 0;
        }
    }

    void generatePaySlip() {
        double salary = calculateSalary();
        double bonus = calculateBonus();
        double total = salary + bonus;
        totalSalaryExpense += total;

        System.out.println("Pay Slip for " + empName);
        System.out.println("Base Salary: $" + salary);
        System.out.println("Bonus: $" + bonus);
        System.out.println("Total Pay: $" + total);
        System.out.println();
    }

    void requestLeave(int day) {
        if (day >= 1 && day <= 30 && attendanceRecord[day - 1]) {
            attendanceRecord[day - 1] = false;
            System.out.println(empName + " has requested leave on day " + day);
        }
    }
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    double calculateSalary() {
        int daysPresent = getDaysPresent();
        return (baseSalary / workingDaysPerMonth) * daysPresent;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    double calculateSalary() {
        int daysPresent = getDaysPresent();
        return (baseSalary / workingDaysPerMonth) * daysPresent * 0.5;
    }
}

class ContractEmployee extends Employee {
    ContractEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    double calculateSalary() {
        return baseSalary;
    }

    double calculateBonus() {
        return 0;
    }
}

class Department {
    String deptId, deptName;
    Employee manager;
    List<Employee> employees = new ArrayList<>();
    double budget;

    Department(String deptId, String deptName, Employee manager, double budget) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.manager = manager;
        this.budget = budget;
        this.employees.add(manager);
    }

    void addEmployee(Employee emp) {
        employees.add(emp);
    }

    double calculateExpenses() {
        double expense = 0;
        for (Employee emp : employees) {
            expense += emp.calculateSalary() + emp.calculateBonus();
        }
        return expense;
    }
}

class CompanyReports {
    static void calculateCompanyPayroll(List<Employee> employees) {
        double total = 0;
        for (Employee emp : employees) {
            total += emp.calculateSalary() + emp.calculateBonus();
        }
        Employee.totalSalaryExpense = total;
        System.out.println("Total Company Payroll: $" + total);
    }

    static void getDepartmentWiseExpenses(List<Department> departments) {
        for (Department d : departments) {
            System.out.println("Department: " + d.deptName + " | Expense: $" + d.calculateExpenses());
        }
    }

    static void getAttendanceReport(List<Employee> employees) {
        for (Employee emp : employees) {
            System.out.println(emp.empName + ": Present " + emp.getDaysPresent() + " days");
        }
    }
}

public class EmployeeSystem {
    public static void main(String[] args) {
        List<Employee> allEmployees = new ArrayList<>();

        FullTimeEmployee e1 = new FullTimeEmployee("E001", "Alice", "IT", "Developer", 3000, "2021-05-01");
        PartTimeEmployee e2 = new PartTimeEmployee("E002", "Bob", "HR", "Assistant", 2000, "2022-01-10");
        ContractEmployee e3 = new ContractEmployee("E003", "Charlie", "IT", "Consultant", 2500, "2023-03-15");

        allEmployees.add(e1);
        allEmployees.add(e2);
        allEmployees.add(e3);

        for (int i = 1; i <= 22; i++) {
            e1.markAttendance(i, true);
            if (i % 2 == 0) e2.markAttendance(i, true);
            if (i % 3 == 0) e3.markAttendance(i, true);
        }

        Department itDept = new Department("D01", "IT", e1, 20000);
        itDept.addEmployee(e3);

        Department hrDept = new Department("D02", "HR", e2, 10000);

        List<Department> allDepartments = new ArrayList<>();
        allDepartments.add(itDept);
        allDepartments.add(hrDept);

        for (Employee emp : allEmployees) {
            emp.generatePaySlip();
        }

        CompanyReports.calculateCompanyPayroll(allEmployees);
        System.out.println();
        CompanyReports.getDepartmentWiseExpenses(allDepartments);
        System.out.println();
        CompanyReports.getAttendanceReport(allEmployees);
    }
}
