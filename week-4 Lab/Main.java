import java.util.*;

enum PersonalityType {
    BRAVE, CUNNING, KIND, MYSTERIOUS, HUMOROUS
}

abstract class StoryCharacter {
    final String characterId;
    final String backstory;
    final PersonalityType corePersonality;

    String currentMood;
    Map<String, Integer> relationshipMap = new HashMap<>();
    Set<String> skillSet = new HashSet<>();
    String currentLocation;

    static final String[] STORY_GENRES = {"Fantasy", "Sci-Fi", "Mystery", "Romance", "Adventure"};

    StoryCharacter(String characterId, String backstory, PersonalityType corePersonality) {
        this.characterId = characterId;
        this.backstory = backstory;
        this.corePersonality = corePersonality;
        this.currentMood = "Neutral";
        this.currentLocation = "Unknown";
    }

    abstract String speak();

    void changeMood(String mood) {
        currentMood = mood;
    }

    void addRelationship(String charId, int level) {
        relationshipMap.put(charId, level);
    }

    void addSkill(String skill) {
        skillSet.add(skill);
    }
}

class Hero extends StoryCharacter {
    final String originStory;
    final String finalAbility;

    Hero(String characterId, String backstory, PersonalityType corePersonality, String originStory) {
        super(characterId, backstory, corePersonality);
        this.originStory = originStory;
        if(originStory.contains("fire")) finalAbility = "FireControl";
        else if(originStory.contains("water")) finalAbility = "WaterBending";
        else finalAbility = "BasicStrength";
    }

    @Override
    String speak() {
        return "For honor and glory!";
    }
}

class Villain extends StoryCharacter {
    final String evilMotivation;

    Villain(String characterId, String backstory, PersonalityType corePersonality, String evilMotivation) {
        super(characterId, backstory, corePersonality);
        this.evilMotivation = evilMotivation;
    }

    @Override
    String speak() {
        return "You cannot stop me!";
    }

    void evolveMotivation(String newMotivation) {
        // Can't change final evilMotivation, so just humorously complain
        System.out.println("Villain tries to hack motivation but fails!");
    }
}

class MysteriousStranger extends StoryCharacter {
    private String secret;

    MysteriousStranger(String characterId) {
        super(characterId, "Unknown", PersonalityType.MYSTERIOUS);
        secret = "Hidden";
    }

    void revealSecret() {
        secret = "Revealed: lost royalty";
    }

    @Override
    String speak() {
        return "Maybe I can help... or maybe not.";
    }
}

class ComicRelief extends StoryCharacter {
    final String humorStyle;
    String timing;

    ComicRelief(String characterId, String backstory, PersonalityType corePersonality, String humorStyle) {
        super(characterId, backstory, corePersonality);
        this.humorStyle = humorStyle;
        this.timing = "On Cue";
    }

    @Override
    String speak() {
        return "Why did the chicken cross the road? To get away from this story!";
    }
}

class StoryEngine {
    static StoryCharacter createCharacterFromPrompt(String prompt) {
        if(prompt.contains("hero")) 
            return new Hero(UUID.randomUUID().toString(), "Born in fire lands", PersonalityType.BRAVE, "fire origin");
        if(prompt.contains("villain")) 
            return new Villain(UUID.randomUUID().toString(), "Dark past", PersonalityType.CUNNING, "World domination");
        if(prompt.contains("stranger")) 
            return new MysteriousStranger(UUID.randomUUID().toString());
        if(prompt.contains("comic")) 
            return new ComicRelief(UUID.randomUUID().toString(), "Funny backstory", PersonalityType.HUMOROUS, "Slapstick");
        return null;
    }

    static String generateStoryArc(StoryCharacter c1, StoryCharacter c2) {
        if(c1 instanceof Hero && c2 instanceof Villain) return "Hero vs Villain epic battle";
        if(c1 instanceof Hero && c2 instanceof MysteriousStranger) return "Hero meets mysterious ally";
        if(c1 instanceof ComicRelief && c2 instanceof Villain) return "Comic tries to sabotage villain with jokes";
        return "A tale of unexpected friendships";
    }

    static void resolveConflict(StoryCharacter c1, StoryCharacter c2) {
        if(c1 instanceof Hero && c2 instanceof Villain) 
            System.out.println("Epic fight ensues!");
        else if(c1 instanceof ComicRelief) 
            System.out.println(c1.speak() + " Breaks tension.");
        else 
            System.out.println("Characters talk it out.");
    }

    static void createDialogue(StoryCharacter c) {
        System.out.println(c.characterId + ": " + c.speak());
    }

    static void metaStoryTwist(StoryCharacter c) {
        System.out.println(c.characterId + " says: 'I wish I could change my final fields, but alas!'");
    }
}

public class Main {
    public static void main(String[] args) {
        StoryCharacter hero = StoryEngine.createCharacterFromPrompt("hero with fire");
        StoryCharacter villain = StoryEngine.createCharacterFromPrompt("villain with evil plan");
        StoryCharacter stranger = StoryEngine.createCharacterFromPrompt("mysterious stranger");
        StoryCharacter comic = StoryEngine.createCharacterFromPrompt("comic relief");

        System.out.println(StoryEngine.generateStoryArc(hero, villain));
        StoryEngine.resolveConflict(hero, villain);
        StoryEngine.createDialogue(comic);

        StoryEngine.metaStoryTwist(villain);
    }
}
