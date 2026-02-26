package com.ceg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class ParseAndInsert {
    public static void main(String[] args) {
        // Create a Gson instance
        Gson gson = new Gson();

        try (Reader reader = new FileReader("superheroes_100_2.json")) {
            // Define the type of the target object (a List of Student objects)
            // TypeToken is necessary to handle generic types like List<Student>
            Type superheroListType = new TypeToken<List<Superhero>>() {
            }.getType();

            // Parse the JSON array from the file into a List<Student>
            List<Superhero> heroList = gson.fromJson(reader, superheroListType);

            // Iterate over the list and use the objects
            for (Superhero hero : heroList) {
                System.out.println("Hero Name: " + hero.getHeroName() + ", firstName: " + hero.getCivilianFirstName()
                        + " address: " + hero.getAddresses().getFirst().getCity());
                // Or simply: System.out.println(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
