package org.example;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

public class ExercisesWednesday {
        private static Logger logger = Logger.getLogger("UserInteractions");
        private static int interactionCount = 0;
        private static List<String[]> users = new ArrayList<>();
    private static void saveUserDataToCSV(String fileName) {  //exercise 1
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Name, Age, Year of Birth, Age Category, Email, Phone, Address, Favorite Book Title, Book Author, Published Year, Favorite Color");
            for (String[] userData : users) {
                String line = String.join(", ", userData);
                writer.println(line);
            }
            System.out.println("User data has been saved to " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while saving user data to the CSV file: " + e.getMessage());
        }
    }

static {
            try {
                FileHandler fileHandler = new FileHandler("user_interactions.log", true);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
                logger.addHandler(fileHandler);
                BufferedReader countReader = new BufferedReader(new FileReader("interaction_count.txt"));
                String countStr = countReader.readLine();
                if (countStr != null) {
                    interactionCount = Integer.parseInt(countStr);;
                }
                countReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            interactionCount++;
            try {
                FileWriter countWriter = new FileWriter("interaction_count.txt");
                countWriter.write(String.valueOf(interactionCount));
                countWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static boolean isValidEmail(String email) {
            String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the name of the file to store your data (e.g., user_info.txt):");
            String fileName = sc.nextLine();
            logger.info("User entered file name: " + fileName);
            System.out.println("What's your name?");
            String name = sc.nextLine();
            logger.info("User entered name: " + name);
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
            String ageCategory = " ";
            if (age <= 12){
                ageCategory = "Child";
            } else if (age >12 && age <= 19) {
                ageCategory = "Teenager";
            } else if (age >19 && age <=64) {
                ageCategory = "Adult";
            } else if (age >= 65) {
                ageCategory = "Senior";
            }
            logger.info("User entered age: " + age);
            String email;
            System.out.println("What's your email address?");
            while (true) {
                email = sc.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println(email + " is not a valid email address. Please try again.");
                    logger.info("User entered invalid email: " + email);
                }
            }
            logger.info("User entered email: " + email);
            System.out.println("What is your phone number?");
            String phone = sc.nextLine();
            logger.info("User entered ph number: " + phone);
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
            logger.info("User selected "+favColour+" as their favourite colour");
            String[] userData = {
                    name, Integer.toString(age), Integer.toString(birthYear), ageCategory, email,
                    phone, address, bookTitle, bookAuthor, Integer.toString(publishedYear), favColour
            };
            users.add(userData);
            saveUserDataToCSV("users.csv");
            System.out.println("User information has been recorded.");
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write("Name: " + name + "\n");
                fileWriter.write("Age: " + age + "\n");
                fileWriter.write("Year of birth: " + birthYear + "\n");
                fileWriter.write("Age category: " + ageCategory + "\n");
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
                FileInputStream fileInput = new FileInputStream(fileName);
                InputStreamReader reader = new InputStreamReader(fileInput);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String phoneNumber = null;
                String userAddress = null;

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("Phone: ")) {
                        phoneNumber = line.substring(7);
                    } else if (line.startsWith("Address: ")) {
                        userAddress = line.substring(9);
                    } else if (line.startsWith("Favourite colour: ")) {
                        favColour = line.substring(18);
                    }
                }

                if (phoneNumber != null && userAddress != null && favColour != null) {
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Address: " + userAddress);
                    System.out.println("Favourite colour: "+favColour);
                } else {
                    System.out.println("Phone number and/or address and/or favourite colour not found in the file.");
                }

                bufferedReader.close();
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
            sc.nextLine();
            System.out.println("Do you want to update your email? (y/n): ");
            String updateEmail = sc.nextLine();
            if (updateEmail.equals("y")) {
                System.out.println("Enter your new email address: ");
                email = sc.nextLine();
                logger.info("User updated email to: "+email);
            } else {
                logger.info("User chose not to update email");
            }
            System.out.println("Do you want to update your phone number? (y/n): ");
            String updatePhone = sc.nextLine();
            if (updatePhone.equals("y")) {
                System.out.println("Enter your new phone number: ");
                phone = sc.nextLine();
                logger.info("User updated phone number to: "+phone);
            }else {
                logger.info("User chose not to update phone number");
            }
            System.out.println("Do you want to update your address? (y/n): ");
            String updateAddress = sc.nextLine();
            if (updateAddress.equals("y")) {
                System.out.println("Enter your new address: ");
                address = sc.nextLine();
                logger.info("User updated address to: "+address);
            } else {
                logger.info("User chose not to update address");
            }
            System.out.println("Updated User Information:");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Year of birth: " + birthYear);
            System.out.println("Age category: " + ageCategory);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Address: " + address);
            System.out.println("Favourite book: " + bookTitle + " by " + bookAuthor + " published in " + publishedYear);
            System.out.println("Favourite colour: " + favColour);

            System.out.println("Do you want to delete your information? (y/n): ");
            String deleteInfo = sc.nextLine();
            if (deleteInfo.equalsIgnoreCase("y")) {
                System.out.println("Are you sure? (y/n)");
                deleteInfo = sc.nextLine();
                if (deleteInfo.equalsIgnoreCase("y")) {
                    try {
                        File userInfoFile = new File(fileName);
                        if (userInfoFile.delete()) {
                            System.out.println("Your information has been deleted.");
                            logger.info("User deleted all data");
                        } else {
                            System.out.println("Failed to delete your information.");
                        }
                    } catch (Exception e) {
                        System.err.println("An error occurred while deleting the information: " + e.getMessage());
                    }
                } else {
                    logger.info("User chose not to delete data.");
                }
            }
            sc.close();
            System.out.println("Interaction count: "+interactionCount);
        }
    }