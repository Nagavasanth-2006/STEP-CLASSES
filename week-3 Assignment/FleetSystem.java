import java.util.*;

class Driver {
    String driverId;
    String driverName;
    String licenseType;
    Vehicle assignedVehicle;
    int totalTrips;

    Driver(String driverId, String driverName, String licenseType) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.licenseType = licenseType;
    }

    void assignVehicle(Vehicle vehicle) {
        this.assignedVehicle = vehicle;
        vehicle.assignDriver(this);
    }

    void completeTrip(double distance, double fuelUsed) {
        if (assignedVehicle != null) {
            assignedVehicle.updateMileage(distance);
            assignedVehicle.fuelConsumption += fuelUsed;
            totalTrips++;
            Vehicle.totalFuelConsumption += fuelUsed;
        }
    }
}

class Vehicle {
    String vehicleId, brand, model, fuelType, currentStatus;
    int year;
    double mileage;
    Driver driver;
    double fuelConsumption;

    static int totalVehicles = 0;
    static double fleetValue = 0;
    static double totalFuelConsumption = 0;
    static String companyName = "TransFleet";

    Vehicle(String vehicleId, String brand, String model, int year, double mileage, String fuelType) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.currentStatus = "Available";
        totalVehicles++;
    }

    void assignDriver(Driver driver) {
        this.driver = driver;
        this.currentStatus = "In Use";
    }

    void scheduleMaintenance() {
        this.currentStatus = "Under Maintenance";
    }

    void updateMileage(double distance) {
        this.mileage += distance;
    }

    double calculateRunningCost() {
        return fuelConsumption * 1.5; // cost per liter
    }

    boolean checkServiceDue() {
        return mileage >= 10000;
    }

    String getType() {
        return "Generic Vehicle";
    }
}

class Car extends Vehicle {
    int numberOfSeats;

    Car(String vehicleId, String brand, String model, int year, double mileage, String fuelType, int numberOfSeats) {
        super(vehicleId, brand, model, year, mileage, fuelType);
        this.numberOfSeats = numberOfSeats;
    }

    String getType() {
        return "Car";
    }
}

class Bus extends Vehicle {
    int seatingCapacity;

    Bus(String vehicleId, String brand, String model, int year, double mileage, String fuelType, int seatingCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType);
        this.seatingCapacity = seatingCapacity;
    }

    String getType() {
        return "Bus";
    }
}

class Truck extends Vehicle {
    double loadCapacity;

    Truck(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double loadCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType);
        this.loadCapacity = loadCapacity;
    }

    String getType() {
        return "Truck";
    }
}

class FleetManager {
    List<Vehicle> vehicles = new ArrayList<>();
    List<Driver> drivers = new ArrayList<>();

    void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    void addDriver(Driver d) {
        drivers.add(d);
    }

    static void getFleetUtilization(List<Vehicle> vehicles) {
        int inUse = 0;
        for (Vehicle v : vehicles) {
            if (v.currentStatus.equals("In Use")) {
                inUse++;
            }
        }
        System.out.println("Fleet Utilization: " + inUse + "/" + vehicles.size());
    }

    static void calculateTotalMaintenanceCost(List<Vehicle> vehicles) {
        double cost = 0;
        for (Vehicle v : vehicles) {
            if (v.checkServiceDue()) {
                cost += 500; // flat maintenance cost per service
            }
        }
        System.out.println("Estimated Maintenance Cost: $" + cost);
    }

    static void getVehiclesByType(List<Vehicle> vehicles, String type) {
        System.out.println("Vehicles of type: " + type);
        for (Vehicle v : vehicles) {
            if (v.getType().equalsIgnoreCase(type)) {
                System.out.println(v.vehicleId + " - " + v.brand + " " + v.model);
            }
        }
    }

    void simulateTrip(String driverId, double distance, double fuelUsed) {
        for (Driver d : drivers) {
            if (d.driverId.equals(driverId)) {
                d.completeTrip(distance, fuelUsed);
                return;
            }
        }
    }
}

public class FleetSystem {
    public static void main(String[] args) {
        FleetManager manager = new FleetManager();

        Car car1 = new Car("V001", "Toyota", "Camry", 2021, 8000, "Petrol", 5);
        Bus bus1 = new Bus("V002", "Volvo", "9400", 2020, 12000, "Diesel", 45);
        Truck truck1 = new Truck("V003", "Tata", "LPT", 2019, 15000, "Diesel", 10.5);

        manager.addVehicle(car1);
        manager.addVehicle(bus1);
        manager.addVehicle(truck1);

        Driver driver1 = new Driver("D001", "Alice", "Car");
        Driver driver2 = new Driver("D002", "Bob", "Heavy");

        manager.addDriver(driver1);
        manager.addDriver(driver2);

        driver1.assignVehicle(car1);
        driver2.assignVehicle(truck1);

        manager.simulateTrip("D001", 120.5, 10.0);
        manager.simulateTrip("D002", 250.0, 40.0);

        System.out.println("\n--- Fleet Report ---");
        FleetManager.getFleetUtilization(manager.vehicles);
        FleetManager.calculateTotalMaintenanceCost(manager.vehicles);
        System.out.println("Total Fuel Consumed: " + Vehicle.totalFuelConsumption + " liters\n");

        FleetManager.getVehiclesByType(manager.vehicles, "Car");
        FleetManager.getVehiclesByType(manager.vehicles, "Truck");
    }
}
