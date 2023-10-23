package org.example;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ExercisesMonday {
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name?");
        String name = sc.nextLine();
        int age = 0;
        while (true) {
            try {
                System.out.println("How old are you?");
                age = sc.nextInt();
                sc.nextLine();
                if (age < 0) {
                    System.out.println("Please enter a positive age.");
                    continue;
                }
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter a valid integer for your age.");
                sc.nextLine();
            }
        }
        int birthYear = 2023 - age;
        String email;
        System.out.println("What's your email address?");
        while (true) {
            email = sc.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println(email + " is not a valid email address. Please try again.");
            }
        }
        System.out.println("What is your phone number?");
        String phone = sc.nextLine();
        System.out.println("What's your address?");
        String address = sc.nextLine();
        System.out.println("What is your favourite book?");
        String bookTitle = sc.nextLine();
        System.out.println("Who is the author?");
        String bookAuthor = sc.nextLine();
        System.out.println("When was it first published?");
        int publishedYear = sc.nextInt();
        System.out.println("What is your favourite colour?");
        System.out.println("Type 'b' for blue");
        System.out.println("Type 'r' for red");
        System.out.println("Type 'y' for yellow");
        System.out.println("Type 'g' for green");
        System.out.println("Type 'o' for orange");
        String colourKey = sc.next();
        String favColour;
        switch(colourKey){
            case "b" : favColour = "blue";
                break;
            case "r" : favColour = "red";
                break;
            case "y" : favColour = "yellow";
                break;
            case "g" : favColour = "green";
                break;
            case "o" : favColour = "orange";
                break;
            default : favColour = "none";
        }
        try {
            FileWriter fileWriter = new FileWriter("user_info.txt");
            fileWriter.write("Name: " + name + "\n");
            fileWriter.write("Age: " + age + "\n");
            fileWriter.write("Year of birth: " + birthYear + "\n");
            fileWriter.write("Email: " + email + "\n");
            fileWriter.write("Phone: " + phone + "\n");
            fileWriter.write("Address: " + address + "\n");
            fileWriter.write("Favourite book: " + bookTitle + " by " + bookAuthor + " published in " + publishedYear+ "\n");
            fileWriter.write("Favourite colour: " + favColour);
            fileWriter.close();
            System.out.println("User information has been written to user_info.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        try {
            FileReader fileReader = new FileReader("user_info.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        sc.close();
    }
}
