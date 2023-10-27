package org.example;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExercisesThursday {
    static List<Student> students = new ArrayList<>();
    static String fileName = "data_student";
    public static class Student implements Serializable {
        private String name;
        private String address;
        private double GPA;
        private String grade;
        private transient List<String> courses = new ArrayList<>();
        private transient List<String> hobbies = new ArrayList<>();

        public Student(String name, String address, double GPA) {
            this.name = name;
            this.address = address;
            this.GPA = GPA;
            if (GPA > 3.3 && GPA <= 4.0) {
                grade = "A";
            } else if (GPA > 2.4 && GPA <= 3.3) {
                grade = "B";
            } else if (GPA > 1.3 && GPA <= 2.4) {
                grade = "C";
            } else if (GPA >= 1.0 && GPA <= 1.3) {
                grade = "D";
            } else if (GPA < 1.0) {
                grade = "F";
            } else {
                grade = "NG";
            }
        }

        public String getName() {
            return name;
        }

        public String getGrade() {
            return grade;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getGPA() {
            return GPA;
        }

        public void setGPA(double GPA) {
            this.GPA = GPA;
        }

        public List<String> getCourses() {
            return courses;
        }

        public void setCourses(List<String> courses) {
            this.courses = courses;
        }

        public List<String> getHobbies() {
            return hobbies;
        }

        public void setHobbies(List<String> hobbies) {
            this.hobbies = hobbies;
        }

        public void addHobby(String hobby){
            hobbies.add(hobby);
        }

        public void addCourse(String course){
            courses.add(course);
        }

        private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
            String encryptedName = new StringBuilder(name).reverse().toString();
            stream.defaultWriteObject();
            stream.writeObject(encryptedName);
        }

        private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            String encryptedName = (String) stream.readObject();
            name = new StringBuilder(encryptedName).reverse().toString();
        }

        @Override
        public String toString() {
            return "Name: "+name+", address: "+address+", GPA: "+GPA+". Courses taken: "+courses+", hobbies: "+hobbies;
        }

        public static void search(List<Student> students, String searchTerm) {
            boolean found = false;
            for (Student student : students) {
                if (student.getName().contains(searchTerm)) {
                    System.out.println("Found student: " + student);
                    found = true;
                    continue;
                }
                for (String hobby : student.getHobbies()) {
                    if (hobby.contains(searchTerm)) {
                        System.out.println("Found student: " + student);
                        found = true;
                        break;
                    }
                }
                for (String course : student.getCourses()) {
                    if (course.contains(searchTerm)) {
                        System.out.println("Found student: " + student);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                System.out.println("No match found.");
            }
        }


        public static void deleteStudent(Student student){
            System.out.println("Delete "+student.getName()+". Are you sure? (y/n)");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            if (choice.contains("y")) {
                students.remove(student);
                System.out.println("Student deleted.");
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    out.writeObject(students);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(student.getName()+" not deleted.");
            }
        }

        public static double avgGpa(){
            double total = 0;
            for (Student student : students){
                total += student.getGPA();
            }
            int size = students.size();
            double average = (total / size);
            DecimalFormat df = new DecimalFormat("#.#");
            return Double.parseDouble(df.format(average));
        }

        public static void courseList(String course){
            List<Student> courseList = new ArrayList<>();
            for (Student student : students){
                if (student.getCourses().contains(course)) {
                    courseList.add(student);
                }
            }
            System.out.println("Students taking "+course);
            for (Student student : courseList) {
                System.out.println(student.getName()+", "+student.getGPA());
            }
        }


        public static void main(String[] args) {
            Student student1 = new Student("Hannah", "Berlin 123", 3.5);
            Student student2 = new Student("Rosa", "Berlin 321", 4.0);
            Student student3 = new Student("Miro", "Berlin 456", 2.1);
            students.add(student1);
            students.add(student2);
            students.add(student3);

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
                out.writeObject(students);
                System.out.println("Students serialized successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                List<Student> deserializedStudents = (List<Student>) in.readObject();
                System.out.println("Deserialized students: ");
                for (Student student : deserializedStudents) {
                    System.out.println(student.toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Would you like to update student data? (y/n)");
            String updateChoice = scanner.nextLine();
            if (updateChoice.contains("y")) {
                System.out.println("Enter the name of the student you would like to update.");
                String studentName = scanner.next();
                Student studentToUpdate = null;
                for (Student student : students) {
                    if (student.getName().equals(studentName)) {
                        studentToUpdate = student;
                    }
                }
                if (studentToUpdate == null) {
                    System.out.println("Not found");
                } else {
                    System.out.println("Which info would you like to update? Enter the number.");
                    System.out.println("1. Edit GPA");
                    System.out.println("2. Add course");
                    System.out.println("3. Add hobby");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Current GPA: "+studentToUpdate.getGPA()+". Please enter new GPA.");
                            double newGPA = scanner.nextDouble();
                            studentToUpdate.setGPA(newGPA);
                            System.out.println("Successfully updated "+studentToUpdate.getName()+"'s GPA to "+newGPA);
                            break;
                        case 2:
                            System.out.println("Existing courses: "+studentToUpdate.getCourses()+". Enter new course.");
                            scanner.nextLine();
                            String newCourse = scanner.nextLine();
                            studentToUpdate.addCourse(newCourse);
                            System.out.println("Successfully added "+newCourse+" to "+studentToUpdate.getName()+"'s courses.");
                            break;
                        case 3:
                            System.out.println("Existing hobbies: "+studentToUpdate.getHobbies()+". Enter new hobby.");
                            scanner.nextLine();
                            String newHobby = scanner.nextLine();
                            studentToUpdate.addHobby(newHobby);
                            System.out.println("Successfully added "+newHobby+" to "+studentToUpdate.getName()+"'s hobbies.");
                            break;
                        default:
                            System.out.println("Not a valid option.");
                    }
                }
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    out.writeObject(students);
                    System.out.println("Info updated and serialized successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                String backupFile = "student_data_backup.ser";
                Files.copy(Paths.get(fileName), Paths.get(backupFile), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Created a copy of " + fileName + " at " + backupFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            search(students, "Hannah");
            deleteStudent(student1);
            System.out.println(students);

            System.out.println("Average GPA:"+avgGpa());

            student1.addCourse("Java");
            student1.addCourse("DevOps");
            student1.addCourse("Soft Skills");
            student2.addCourse("Soft Skills");
            student2.addCourse("Marketing");
            student2.addCourse("SEO");

            search(students, "Java");

            courseList("Java");
        }
    }
}