package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExercisesThursday {
    public static class Student implements Serializable {
        private String name;
        private String address;
        private double GPA;
        private transient List<String> courses = new ArrayList<>();
        private transient List<String> hobbies = new ArrayList<>();

        public Student(String name, String address, double GPA) {
            this.name = name;
            this.address = address;
            this.GPA = GPA;
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

        public static void main(String[] args) {
            List<Student> students = new ArrayList<>();
            Student student1 = new Student("Hannah", "Berlin 123", 3.5);
            Student student2 = new Student("Rosa", "Berlin 321", 4.0);
            Student student3 = new Student("Miro", "Berlin 456", 2.1);
            students.add(student1);
            students.add(student2);
            students.add(student3);

            String baseFileName = "data_student";

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseFileName))) {
                out.writeObject(students);
                System.out.println("Students serialized successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(baseFileName))) {
                List<Student> deserializedStudents = (List<Student>) in.readObject();
                System.out.println("Deserialized students: ");
                for (Student student : deserializedStudents) {
                    System.out.println(student.toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}