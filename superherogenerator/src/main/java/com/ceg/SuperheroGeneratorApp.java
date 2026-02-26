package com.ceg;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperheroGeneratorApp {

    private static final int NUM_SUPERHEROES = 100; // 1_000_000
    private static final int MIN_ADDRESSES_PER_HERO = 1;
    private static final int MAX_ADDRESSES_PER_HERO = 3;
    private static final int MIN_POWERS_PER_HERO = 2;
    private static final int MAX_POWERS_PER_HERO = 5;

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        String outputFile = "superheroes.json";
        if (args.length > 0) {
            outputFile = args[0];
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();

        try (OutputStream os = new FileOutputStream(outputFile);
                JsonGenerator generator = factory.createGenerator(os, com.fasterxml.jackson.core.JsonEncoding.UTF8)) {

            generator.writeRaw('{');
            generator.writeString("heroes");
            generator.writeRaw(':');
            generator.writeStartArray();

            for (int i = 0; i < NUM_SUPERHEROES; i++) {
                Superhero hero = generateRandomSuperhero();
                mapper.writeValue(generator, hero);

                if (i % 100_000 == 0) {
                    System.out.println("Generated " + i + " superheroes...");
                }
            }

            generator.writeEndArray();
            generator.writeRaw('}');
        }

        System.out.println("Done. Output written to " + outputFile);
    }

    private static Superhero generateRandomSuperhero() {
        String adjective = randomFrom(ADJECTIVES);
        String noun = randomFrom(NOUNS);
        String heroName = adjective + " " + noun;

        String firstName = randomFrom(FIRST_NAMES);
        String lastName = randomFrom(LAST_NAMES);

        int numPowers = randomBetween(MIN_POWERS_PER_HERO, MAX_POWERS_PER_HERO);
        List<String> powers = new ArrayList<>(numPowers);
        for (int i = 0; i < numPowers; i++) {
            powers.add(randomFrom(POWERS));
        }

        int numAddresses = randomBetween(MIN_ADDRESSES_PER_HERO, MAX_ADDRESSES_PER_HERO);
        List<Address> addresses = new ArrayList<>(numAddresses);
        for (int i = 0; i < numAddresses; i++) {
            String street = randomFrom(STREET_NAMES) + " " + (RANDOM.nextInt(9999) + 1);
            String city = randomFrom(CITIES);
            String country = "USA";
            String postalCode = String.format("%05d", RANDOM.nextInt(100000));
            addresses.add(new Address(street, city, country, postalCode));
        }

        return new Superhero(heroName, firstName, lastName, powers, addresses);
    }

    private static int randomBetween(int minInclusive, int maxInclusive) {
        return RANDOM.nextInt(maxInclusive - minInclusive + 1) + minInclusive;
    }

    private static <T> T randomFrom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    // --- DATA SETS (≥100 each where requested) ---

    private static final List<String> ADJECTIVES = List.of(
            "Mighty", "Incredible", "Amazing", "Invincible", "Spectacular", "Savage", "Swift", "Silent", "Crimson",
            "Golden",
            "Shadow", "Radiant", "Thunderous", "Blazing", "Frozen", "Electric", "Cosmic", "Galactic", "Iron", "Steel",
            "Emerald", "Scarlet", "Violet", "Azure", "Obsidian", "Luminous", "Phantom", "Vigilant", "Valiant",
            "Fearless",
            "Brave", "Noble", "Cunning", "Arcane", "Mystic", "Eternal", "Infinite", "Solar", "Lunar", "Stellar",
            "Quantum", "Atomic", "Titanic", "Colossal", "Miniature", "Rapid", "Furious", "Stormy", "Tempest",
            "Glorious",
            "Heroic", "Daring", "Bold", "Secret", "Hidden", "Shining", "Burning", "Icy", "Magnetic", "Sonic",
            "Nebulous", "Celestial", "Primal", "Ancient", "Modern", "Cyber", "Neon", "Crystalline", "Jagged", "Rugged",
            "Wild", "Untamed", "Enchanted", "Blessed", "Cursed", "Shadowy", "Radiant", "Gleaming", "Blinding", "Silent",
            "Roaring", "Howling", "Whispering", "Vengeful", "Righteous", "Merciful", "Relentless", "Unyielding",
            "Steadfast", "Unseen",
            "Invisible", "Spectral", "Ethereal", "Temporal", "Vibrant", "Harmonic", "Chaotic", "Orderly", "Regal",
            "Majestic");

    private static final List<String> NOUNS = List.of(
            "Guardian", "Avenger", "Defender", "Sentinel", "Ranger", "Knight", "Samurai", "Ninja", "Warrior",
            "Champion",
            "Crusader", "Protector", "Warden", "Hunter", "Seeker", "Wizard", "Sorcerer", "Mage", "Witch", "Alchemist",
            "Phoenix", "Dragon", "Tiger", "Panther", "Falcon", "Eagle", "Wolf", "Lion", "Leopard", "Cobra",
            "Viper", "Hawk", "Raven", "Crow", "Spider", "Scorpion", "Shark", "Dolphin", "Kraken", "Golem",
            "Titan", "Giant", "Colossus", "Specter", "Phantom", "Ghost", "Shade", "Shadow", "Blade", "Arrow",
            "Hammer", "Axe", "Spear", "Shield", "Fist", "Claw", "Storm", "Tempest", "Blizzard", "Inferno",
            "Quake", "Tremor", "Bolt", "Flash", "Spark", "Surge", "Wave", "Tsunami", "Cyclone", "Hurricane",
            "Comet", "Meteor", "Star", "Nova", "Nebula", "Galaxy", "Atom", "Quantum", "Pulse", "Beacon",
            "Oracle", "Prophet", "Sage", "Monk", "Paladin", "Vanguard", "Outrider", "Striker", "Gladiator", "Berserker",
            "Wraith", "Revenant", "Reaper", "Harbinger", "Herald", "Emissary", "Nomad", "Pilgrim", "Voyager",
            "Navigator");

    private static final List<String> FIRST_NAMES = List.of(
            "Liam", "Noah", "Oliver", "Elijah", "James", "William", "Benjamin", "Lucas", "Henry", "Theodore",
            "Jack", "Levi", "Alexander", "Jackson", "Mateo", "Daniel", "Michael", "Mason", "Sebastian", "Ethan",
            "Logan", "Owen", "Samuel", "Jacob", "Asher", "Aiden", "John", "Joseph", "Wyatt", "David",
            "Leo", "Luke", "Julian", "Hudson", "Grayson", "Matthew", "Ezra", "Gabriel", "Carter", "Isaac",
            "Jayden", "Luca", "Anthony", "Dylan", "Lincoln", "Thomas", "Maverick", "Elias", "Josiah", "Charles",
            "Emma", "Olivia", "Ava", "Sophia", "Isabella", "Mia", "Amelia", "Harper", "Evelyn", "Abigail",
            "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Sofia", "Camila", "Aria", "Scarlett", "Victoria",
            "Madison", "Luna", "Grace", "Chloe", "Penelope", "Layla", "Riley", "Zoey", "Nora", "Lily",
            "Eleanor", "Hannah", "Lillian", "Addison", "Aubrey", "Ellie", "Stella", "Natalie", "Zoe", "Leah",
            "Hazel", "Violet", "Aurora", "Savannah", "Audrey", "Brooklyn", "Bella", "Claire", "Skylar", "Lucy");

    private static final List<String> LAST_NAMES = List.of(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
            "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
            "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts",
            "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
            "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper",
            "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
            "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes");

    private static final List<String> STREET_NAMES = List.of(
            "Oak", "Maple", "Pine", "Cedar", "Elm", "Birch", "Walnut", "Chestnut", "Willow", "Ash",
            "Spruce", "Hickory", "Sycamore", "Poplar", "Magnolia", "Dogwood", "Locust", "Alder", "Beech", "Cypress",
            "Laurel", "Holly", "Juniper", "Redwood", "Sequoia", "Aspen", "Cottonwood", "Fir", "Hemlock", "Linden",
            "River", "Lake", "Hill", "Valley", "Glen", "Ridge", "Creek", "Brook", "Spring", "Meadow",
            "Forest", "Grove", "Park", "Garden", "Sunset", "Sunrise", "Highland", "Lowland", "Prairie", "Canyon",
            "Summit", "Vista", "Harbor", "Bay", "Shore", "Coast", "Ocean", "Island", "Harborview", "Cliff",
            "Stone", "Rock", "Boulder", "Pebble", "Sand", "Dune", "Prairieview", "Field", "Orchard", "Vineyard",
            "Mill", "Factory", "Depot", "Station", "Market", "Plaza", "Court", "Circle", "Terrace", "Lane",
            "Drive", "Avenue", "Boulevard", "Street", "Way", "Trail", "Path", "Place", "Alley", "Row");

    private static final List<String> CITIES = List.of(
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego",
            "Dallas", "San Jose",
            "Austin", "Jacksonville", "Fort Worth", "Columbus", "Charlotte", "San Francisco", "Indianapolis", "Seattle",
            "Denver", "Washington",
            "Boston", "El Paso", "Nashville", "Detroit", "Oklahoma City", "Portland", "Las Vegas", "Memphis",
            "Louisville", "Baltimore",
            "Milwaukee", "Albuquerque", "Tucson", "Fresno", "Sacramento", "Kansas City", "Long Beach", "Mesa",
            "Atlanta", "Colorado Springs",
            "Virginia Beach", "Raleigh", "Omaha", "Miami", "Oakland", "Minneapolis", "Tulsa", "Wichita", "New Orleans",
            "Arlington",
            "Cleveland", "Bakersfield", "Tampa", "Aurora", "Honolulu", "Anaheim", "Santa Ana", "Corpus Christi",
            "Riverside", "Lexington",
            "St. Louis", "Stockton", "Pittsburgh", "Saint Paul", "Cincinnati", "Anchorage", "Henderson", "Greensboro",
            "Plano", "Newark",
            "Lincoln", "Toledo", "Orlando", "Chula Vista", "Irvine", "Fort Wayne", "Jersey City", "Durham",
            "St. Petersburg", "Laredo",
            "Buffalo", "Madison", "Lubbock", "Chandler", "Scottsdale", "Glendale", "Reno", "Norfolk", "Winston-Salem",
            "North Las Vegas");

    private static final List<String> POWERS = List.of(
            "Flight", "Super Strength", "Invisibility", "Telepathy", "Telekinesis", "Super Speed", "Time Manipulation",
            "Shape Shifting", "Energy Blasts", "Force Fields",
            "Regeneration", "Healing Touch", "Elemental Control", "Fire Manipulation", "Water Manipulation",
            "Earth Manipulation", "Air Manipulation", "Lightning Control", "Ice Manipulation", "Mind Control",
            "Technopathy", "Animal Communication", "Size Changing", "Intangibility", "X-Ray Vision", "Night Vision",
            "Super Hearing", "Super Intelligence", "Precognition", "Probability Manipulation",
            "Gravity Control", "Magnetism", "Sonic Scream", "Duplication", "Elasticity", "Portal Creation",
            "Illusion Casting", "Shadow Control", "Light Manipulation", "Plant Control",
            "Poison Generation", "Radiation Control", "Density Control", "Emotion Manipulation", "Luck Manipulation",
            "Dream Walking", "Astral Projection", "Spirit Communication", "Energy Absorption", "Power Mimicry");
}
