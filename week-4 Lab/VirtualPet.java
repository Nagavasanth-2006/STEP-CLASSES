import java.util.UUID;
import java.util.Random;

public class VirtualPet {
    private final String petId;
    private String petName;
    private String species;
    private int age;
    private int happiness;
    private int health;
    private int stageIndex;
    private boolean isDead;

    static final String[] EVOLUTION_STAGES = {"Egg", "Baby", "Child", "Teen", "Adult", "Elder"};
    static int totalPetsCreated = 0;

    public VirtualPet() {
        this("Unknown", getRandomSpecies(), 0, 50, 50, 0);
    }

    public VirtualPet(String petName) {
        this(petName, getRandomSpecies(), 0, 60, 60, 1);
    }

    public VirtualPet(String petName, String species) {
        this(petName, species, 1, 70, 70, 2);
    }

    public VirtualPet(String petName, String species, int age, int happiness, int health, int stageIndex) {
        this.petId = generatePetId();
        this.petName = petName;
        this.species = species;
        this.age = age;
        this.happiness = happiness;
        this.health = health;
        this.stageIndex = stageIndex;
        this.isDead = false;
        totalPetsCreated++;
        System.out.println("New pet created: " + petName + " [" + petId + "]");
    }

    public static String generatePetId() {
        return UUID.randomUUID().toString();
    }

    private static String getRandomSpecies() {
        String[] speciesOptions = {"Dragon", "Catmon", "Turtloid", "Birdsy", "Lambox"};
        return speciesOptions[new Random().nextInt(speciesOptions.length)];
    }

    public void feedPet() {
        if (!isDead) {
            health += 10;
            if (health > 100) health = 100;
            System.out.println(petName + " was fed. Health is now " + health);
        }
    }

    public void playWithPet() {
        if (!isDead) {
            happiness += 10;
            if (happiness > 100) happiness = 100;
            System.out.println(petName + " played. Happiness is now " + happiness);
        }
    }

    public void healPet() {
        if (!isDead) {
            health += 20;
            if (health > 100) health = 100;
            System.out.println(petName + " was healed. Health is now " + health);
        }
    }

    public void evolvePet() {
        if (isDead || species.equals("Ghost")) return;
        if (age >= 2 && stageIndex < EVOLUTION_STAGES.length - 1) {
            stageIndex++;
            System.out.println(petName + " has evolved to: " + EVOLUTION_STAGES[stageIndex]);
        }
    }

    public void simulateDay() {
        if (isDead) return;

        age++;
        happiness -= new Random().nextInt(10);
        health -= new Random().nextInt(10);

        if (happiness < 0) happiness = 0;
        if (health < 0) health = 0;

        if (health == 0) {
            dieAndHaunt();
        } else {
            evolvePet();
        }
    }

    private void dieAndHaunt() {
        isDead = true;
        species = "Ghost";
        stageIndex = 0;
        System.out.println(petName + " has died and become a haunting Ghost...");
    }

    public void haunt(VirtualPet otherPet) {
        if (this.isDead && this.species.equals("Ghost")) {
            otherPet.happiness -= 10;
            if (otherPet.happiness < 0) otherPet.happiness = 0;
            System.out.println(this.petName + " haunted " + otherPet.petName + "! " + otherPet.petName + "'s happiness is now " + otherPet.happiness);
        }
    }

    public void getPetStatus() {
        System.out.println("\n--- Status of " + petName + " ---");
        System.out.println("ID: " + petId);
        System.out.println("Species: " + species);
        System.out.println("Age: " + age);
        System.out.println("Stage: " + EVOLUTION_STAGES[stageIndex]);
        System.out.println("Health: " + health);
        System.out.println("Happiness: " + happiness);
        System.out.println("Alive: " + (!isDead));
    }

    public static void main(String[] args) {
        System.out.println("=== VIRTUAL PET DAYCARE ===\n");

        VirtualPet pet1 = new VirtualPet(); // Egg stage
        VirtualPet pet2 = new VirtualPet("Fluffy"); // Baby stage
        VirtualPet pet3 = new VirtualPet("Zappy", "Turtloid"); // Child stage
        VirtualPet pet4 = new VirtualPet("Max", "Dragon", 3, 90, 90, 3); // Custom

        VirtualPet[] pets = {pet1, pet2, pet3, pet4};

        for (int day = 1; day <= 5; day++) {
            System.out.println("\n--- Day " + day + " ---");
            for (VirtualPet pet : pets) {
                pet.simulateDay();
                pet.feedPet();
                pet.playWithPet();
                pet.healPet();
            }
        }

        // Ghost pet haunts others
        for (VirtualPet pet : pets) {
            if (pet.species.equals("Ghost")) {
                for (VirtualPet other : pets) {
                    if (!other.equals(pet)) {
                        pet.haunt(other);
                    }
                }
            }
        }

        // Show final status
        System.out.println("\n=== FINAL STATUS ===");
        for (VirtualPet pet : pets) {
            pet.getPetStatus();
        }

        System.out.println("\nTotal pets created: " + VirtualPet.totalPetsCreated);
    }
}
