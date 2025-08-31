public class Car {
    String brand;
    String model;
    int year;
    String color;
    boolean isRunning;

    public Car(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isRunning = false;
    }

    public void startEngine() {
        isRunning = true;
        System.out.println(brand + " " + model + " engine started.");
    }

    public void stopEngine() {
        isRunning = false;
        System.out.println(brand + " " + model + " engine stopped.");
    }

    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Color: " + color);
        System.out.println("Is Running: " + isRunning);
    }

    public int getAge() {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - year;
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "Corolla", 2015, "White");
        Car car2 = new Car("Honda", "Civic", 2020, "Black");
        Car car3 = new Car("Ford", "Mustang", 2018, "Red");

        car1.startEngine();
        car1.displayInfo();
        System.out.println("Age: " + car1.getAge());
        car1.stopEngine();

        System.out.println();

        car2.displayInfo();
        car2.startEngine();
        System.out.println("Age: " + car2.getAge());
        car2.stopEngine();

        System.out.println();

        car3.startEngine();
        car3.displayInfo();
        System.out.println("Age: " + car3.getAge());
        car3.stopEngine();
    }
}
