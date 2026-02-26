package com.ceg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;

public class ParseAndInsert {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "asdf1234";
    public static ArrayList<String> heroJSON = new ArrayList<String>();

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
                /*
                 * System.out.println("Hero Name: " + hero.getHeroName() + ", firstName: " +
                 * hero.getCivilianFirstName()
                 * + " address: " + hero.getAddresses().getFirst().getCity());
                 */
                String jsonString = gson.toJson(hero);
                heroJSON.add(jsonString);
                System.out.println(jsonString);
                insertHero(jsonString);
                // Or simply: System.out.println(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertHero(String jsonStr) {
        // SQL INSERT statement with placeholders (?)
        String SQL = "INSERT INTO superheroes (hero_data) VALUES (?::jsonb)";

        // Use try-with-resources to ensure Connection and PreparedStatement are closed
        // automatically
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            // Bind values to the placeholders
            pstmt.setString(1, jsonStr); // Binds the first '?' to the name value

            // Execute the update
            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows + " row(s) inserted.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
