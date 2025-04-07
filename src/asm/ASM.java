/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asm;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author admin
 */

class Student {
    int ID;
    String name;
    double marks;
    String ranking;
    Student left, right;
    
    public Student(int ID, String name, double marks) {
        this.ID = ID;
        this.name = name;
        this.marks = marks;
        this.ranking = classifyRanking(marks);
        this.left = this.right = null;
    }
    
    private String classifyRanking(double marks) {
        if (marks < 5.0) {
            return "Fail";
        }
        else if (marks < 6.5) {
            return "Medium";
        }
        else if (marks < 7.5) {
            return "Good";
        }
        else if (marks < 9.0) {
            return "Very good";
        }
        else {
            return "Excellent";
        }
    }
    
    public int getID() {
        return ID;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMark() {
        return marks;
    }

    public String getRanking() {
        return ranking;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMarks (double marks) {
        this.marks = marks;
        this.ranking = classifyRanking(marks);
    }
    
    @Override
    public String toString() {
        return "ID: " + ID + ", Name: " + name + ", Mark: " + marks + ", Rank: " + ranking;
    }
}

class BSTNode {
    Student student;
    BSTNode left, right;

    public BSTNode(Student student) {
        this.student = student;
        this.left = this.right = null;
    }
    
}

class BST {
    private BSTNode root;
    
    public void insert(Student student) {
        root = insertRec(root, student);
    }
    
    private BSTNode insertRec(BSTNode root, Student student) {
        if(root == null) {
            return new BSTNode(student);
        }
        
        if(student.ID < root.student.ID) {
            root.left = insertRec(root.left, student);
        }
        else {
            root.right = insertRec(root.right, student);
        }
        return root;
    }
    
    public Student searchByID(int ID) {
        BSTNode result = searchRec(root, ID);
        return (result != null) ? result.student : null;
    }
    
    public Student searchByName(String name) {
        BSTNode result = searchRecc(root, name);
        return (result != null) ? result.student : null;
    }
    
    private BSTNode searchRec(BSTNode root, int ID) {
        if(root == null || root.student.ID == ID) {
           return root;
        }
        
        return (ID < root.student.ID) ? searchRec(root.left, ID) : searchRec(root.right, ID);
    }
    
    private BSTNode searchRecc(BSTNode root, String name) {
        if(root == null) {
           return null;
        }
        
        if(root.student.name.equalsIgnoreCase(name)) {
            return root;
        }
        
        BSTNode leftResult = searchRecc(root.left, name);
        return (leftResult != null) ? leftResult : searchRecc(root.right, name);
    }
        
    public void deleteStudent (int ID) {
        root = delete(root, ID);
    }
    
    public BSTNode delete(BSTNode root, int ID) {
        if(root == null) {
            return root;
        }
        
        if(ID < root.student.ID) {
            root.left = delete(root.left, ID);
        }
        else if(ID > root.student.ID) {
            root.right = delete(root.right, ID);
        }
        else {
            if(root.left == null) {
                return root.right;
            }
            else if(root.right == null) {
                return  root.left;
            }
            
            root.student = findMin(root.right).student;
            root.right = delete(root.right, root.student.ID);
        }
        return root;
    }
    
    private BSTNode findMin(BSTNode root) {
        while (root.left != null) {            
            root = root.left;
        }
        return root;
    }
    
    //Duyệt cây theo thứ tự
    public void inorder(){
        inorderRec(root);
    }
    
    private void inorderRec(BSTNode root){
        if(root != null){
            inorderRec(root.left);
            System.out.println(root.student);
            inorderRec(root.right);
        }
    }
    
    public Student findHighMarkStudent() {
        if (root == null) {
            return null;
        }
        return findMaxMark(root);
    }
    
    private Student findMaxMark(BSTNode root) {
        if(root == null) {
            return null;
        }
        
        Student leftMax = findMaxMark(root.left);
        Student rightMax = findMaxMark(root.right);
        
        Student maxStudent = root.student;
        if(leftMax != null && leftMax.marks > maxStudent.marks) {
            maxStudent = leftMax;
        }
        if(rightMax != null && rightMax.marks > maxStudent.marks) {
            maxStudent = rightMax;
        }
        
        return maxStudent;
    }
    
    public Student findLowestMarkStudent() {
        if (root == null) {
            return null;
        }
        return findMinMark(root, root.student);
    }
    
    private Student findMinMark(BSTNode root, Student minStudent) {
        if(root == null) {
            return minStudent;
        }
        
        if(root.student.marks < minStudent.marks) {
            minStudent = root.student;
        }
        
        Student leftMin = findMinMark(root.left, minStudent);
        Student rightMin = findMinMark(root.right, minStudent);
        
        if(leftMin == null) {
            return rightMin;
        }
        
        if (rightMin == null) {
            return leftMin;
        }
        
        return (leftMin.marks < rightMin.marks) ? leftMin : rightMin;
    }
    

   
    public void sortStudentByMarks() {
        List<Student> students = new ArrayList<>();
        storeInList(root, students);
        
        heapSort(students);
        
        for (Student student : students) {
            System.out.println(student);
        }
    }
    
    public void sortdStudentByMarks() {
        List<Student> students = new ArrayList<>();
        storeInList(root, students);

        heapSortDescending(students);
        for (Student student : students) {
            System.out.println(student);
        }
    }
        
    private void  storeInList(BSTNode root, List<Student> students) {
        if (root != null) {
            storeInList(root.left, students);
            students.add(root.student);
            storeInList(root.right, students);
        }
    }
    
    private void heapSort(List<Student> students) {
        PriorityQueue<Student> heap = new PriorityQueue<>(Comparator.comparingDouble(Student::getMark));
        heap.addAll(students);
        students.clear();
        while (!heap.isEmpty()) {            
            students.add(heap.poll());
        }
    }
    
    private void heapSortDescending(List<Student> students) {
        PriorityQueue<Student> heap = new PriorityQueue<>(Comparator.comparingDouble(Student::getMark).reversed());
        heap.addAll(students);
        students.clear();
        while (!heap.isEmpty()) {            
            students.add(heap.poll());
        }
    }
}
public class ASM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Student> listStudent = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        BST studentTree = new BST();
        
        int choice;
        do {
            System.out.println("\n--- STUDENT MANAGER ---");
            System.out.println("1. Add students");
            System.out.println("2. Edit students");
            System.out.println("3. Delete students");
            System.out.println("4. Search for students by ID or Name");
            System.out.println("5. Display all students");
            System.out.println("6. Enter students score");
            System.out.println("7. Calculate the average score");
            System.out.println("8. Student classification");
            System.out.println("9. Sort students by mark");
            System.out.println("10. Search for students with the highest or lowest score");
            System.out.println("11. List of scholarships");
            System.out.println("12. Exit");
            System.out.print("Choose function: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice) {
                case 1: 
                    addStudents(listStudent, studentTree, scanner);
                    break;
                case 2:
                    editStudents(listStudent, studentTree, scanner);
                    break;
                case 3:
                    deleteStudents(listStudent, studentTree, scanner);
                    break;
                case 4:
                    searchforStudentsbyIDorName(listStudent, studentTree, scanner);
                    break;
                case 5:
                    displayallStudents(listStudent, studentTree, scanner);
                    break;
                case 6:
                    enterStudentscore(listStudent, studentTree, scanner);
                    break;
                case 7:
                    calculatetheaverageScore(listStudent, studentTree, scanner);
                    break;
                case 8:
                    studentClassification(listStudent, studentTree, scanner);
                    break;
                case 9:
                    sortstudentsbyMark(listStudent, studentTree, scanner);
                    break;
                case 10:
                    searchforstudentswiththehighestorlowestScore(listStudent, studentTree, scanner);
                    break;
                case 11:
                    listofScholarships(listStudent, studentTree, scanner);
                    break;
                case 12:
                    System.out.println("Exiting.....");
                    break;
                default:
                    System.out.println("The choice is not valid!");
            }
        }
        while(choice != 12);
    }
    
    public static boolean idExists(int ID, List<Student> list) {
        for (Student student : list) {
            if (student.getID() == ID) {
                return true;
            }
        }
        return false;
    }
    
    static void addStudents(List<Student> list, BST studentTree, Scanner scanner){
        System.out.println("Enter the number of students to add:");
        int numStudent = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numStudent; i++) {
            System.out.println("Enter the information of student " + (i + 1));
            
            int ID ;
            do {
                System.out.println("Enter student ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid student ID: ");
                    scanner.next();
                }
                ID = scanner.nextInt();
                scanner.nextLine();
                
                if (ID <= 0) {
                    System.out.println("Invalid ID! ID must be a positive number.");
                    continue;
                }
                
                if (idExists(ID, list)) {
                    System.out.println("This ID already exists! Please enter a unique ID.");
                }
            }
            while (ID <= 0 || idExists(ID, list));
            
            String name;
            while (true) {                
                System.out.println("Enter student's Name: ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z\\s]+") ){
                    break;
                } 
                else {
                    System.out.println("Invalid Name! Please enter a valid name: ");
                }
            }
            
            double marks = 0.0;
            
            Student student = new Student(ID, name, marks);
            list.add(student);
            studentTree.insert(student);
            
            System.out.println("Add successful students!");
        }
        
        System.out.println("\nThe full student list: ");
        studentTree.inorder();
    }
    
    static void editStudents(List<Student> list, BST studentTree, Scanner scanner){
        System.out.println("Enter the student ID to edit:");
        int ID = scanner.nextInt();
        scanner.nextLine();
        
        Student student = studentTree.searchByID(ID);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        String newName;
        while (true) {                
            System.out.println("Enter the new name: ");
            newName = scanner.nextLine();
            if (newName.matches("[a-zA-Z\\s]+")) {
                break;
            } 
            else {
                System.out.println("Invalid Name! Please enter the new name: ");
            }
        }

        double newMark;
        while(true) {
            System.out.print("Enter the new mark: ");
            if (scanner.hasNextDouble()) {
                newMark = scanner.nextDouble();
                if (newMark > 0.0 && newMark < 10.0){
                    break;
                }
            }
            else{
                System.out.println("Invalid input! Please enter the new Mark: ");
                scanner.nextLine();
            }
        }
        
        studentTree.deleteStudent(ID);
        list.removeIf(s -> s.getID() == ID);
        
        Student updatedStudent = new Student(ID, newName, newMark);
        list.add(updatedStudent);
        studentTree.insert(updatedStudent);
        System.out.println("Student information updated successfully!");
        
        System.err.println("\n Display edited student information: ");
        studentTree.inorder();
    }
    
    static void deleteStudents(List<Student> list, BST studentTree, Scanner scanner){
        System.out.print("Enter the student ID to delete: ");
        int ID;
        while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid student ID: ");
                scanner.next();
        }
        ID = scanner.nextInt();
        scanner.nextLine();
    
        Student student = studentTree.searchByID(ID);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        list.removeIf(s -> s.getID() == ID);
        studentTree.deleteStudent(ID);
        
        System.out.println("Student deleted successfully!");
    }

    static void searchforStudentsbyIDorName(List<Student> list, BST studentTree, Scanner scanner){
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("Choose function: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch(choice) {
            case 1:
                System.out.print("Enter the student ID to find: ");
                while(!scanner.hasNextInt()) {
                    System.out.println("Please enter the student ID to find: ");
                    scanner.nextLine();
                }
                int IDtofind = scanner.nextInt();
                Student found = studentTree.searchByID(IDtofind);
                System.out.println((found != null) ? found : "Can not find students with the ID!");
                break;
                
            case 2:
                System.out.print("Enter the student Name to find: ");
                String Nametofind = scanner.nextLine();
                
                Student foundStudent = studentTree.searchByName(Nametofind);
                System.out.println((foundStudent != null) ? foundStudent : "Can not find students with the Name!");
                break;
                
            default:
                System.out.println("The choice is not valid!");
        }     
               
    }
    
    static void displayallStudents(List<Student> list, BST studentTree, Scanner scanner){
        if(list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }
        
        System.out.println("\n List of Students");
        for (Student student : list) {
            System.out.println(student);
        }
    }
    
    static void enterStudentscore(List<Student> list, BST studentTree, Scanner scanner){
        int ID = -1;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("Enter student ID: ");
            if(scanner.hasNextInt()) {
                ID = scanner.nextInt();
                validInput = true;
            }
            else {
                System.out.println("Invalid input! Please enter a valid student ID: ");
                scanner.next();
            }
        }
        scanner.nextLine(); // Consume newline
    
        Student student = studentTree.searchByID(ID);
        // Search for the student
        if (student != null) {
            double mark = -1.0;
            validInput = false;
            
            // Validate mark input
            while(!validInput) {
                System.out.print("Enter the Mark : ");
                if (scanner.hasNextDouble()) {
                    mark = scanner.nextDouble();
                    if (mark >= 0.0 && mark <= 10.0){
                        validInput = true;
                    }
                    else {
                        System.out.println("Invalid input! Enter the Mark: ");
                    }
                }
                else {
                    scanner.next();
                }  
            }
            scanner.nextLine();
            
            // Update student mark
            student.setMarks(mark);
            System.out.println("Score updated successfully!");
        } 
        else {
            System.out.println("Student not found!");
        }

        // Display updated student list
        System.out.println("\nUpdated student list:");
        studentTree.inorder();
    }
    
    static void calculatetheaverageScore(List<Student> list, BST studentTree, Scanner scanner){
        if(list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }
        
        double totalMarks = 0;
        int count = 0;
        
        for (Student student : list) {
            if (student.getMark() >= 0) {
                totalMarks += student.getMark();
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("No students have mark!");
        } 
        else {
            double average = totalMarks / count;
            System.out.printf("The average mark of all student: %.2f \n" , average);
        }
    }
    
    static void studentClassification(List<Student> list, BST studentTree, Scanner scanner){
        if (list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        int fail = 0, medium = 0, good = 0, veryGood = 0, excellent = 0;

        for (Student student : list) {
            if(student.getRanking() != null) {
                switch (student.getRanking()) {
                    case "Fail":
                        fail++;
                        break;
                    case "Medium":
                        medium++;
                        break;
                    case "Good":
                        good++;
                        break;
                    case "Very good":
                        veryGood++;
                        break;
                    case "Excellent":
                        excellent++;
                        break;
                    default:
                        System.out.println("Unknown ranking for student ID! ");
                }
            }
        }

        System.out.println("\nStudent ranking statistics:");
        System.out.println("Fail: " + fail);
        System.out.println("Medium: " + medium);
        System.out.println("Good: " + good);
        System.out.println("Very good: " + veryGood);
        System.out.println("Excellent: " + excellent);
        
        List<String> rankOrder = Arrays.asList("Fail", "Medium", "Good", "Very good", "Excellent");

    list.stream()
        .filter(s -> s.getRanking() != null)
        .sorted(Comparator.comparingInt(s -> rankOrder.indexOf(s.getRanking())))
        .forEach(System.out::println);
    }
    
    static void sortstudentsbyMark(List<Student> list, BST studentTree, Scanner scanner){
        if (list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }
        
        System.out.println("1. Sort by increasing Mark");
        System.out.println("2. Sort by decreasing Mark");
        System.out.println("Choose sorting option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.println("\nList of students sorted by increasing marks: ");
                studentTree.sortStudentByMarks();
                break;
            case 2:
                System.out.println("\nList of students sorted by decreasing marks: ");
                studentTree.sortdStudentByMarks();
                break;
            default:
                System.out.println("The choice is not valid!");
                break;
        }
        
        for (Student student : list) {
            System.out.println(student);
        }
    }

    static void searchforstudentswiththehighestorlowestScore(List<Student> list, BST studentTree, Scanner scanner){
        if (list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        System.out.println("\nStudent with the highest mark:");
        System.out.println(studentTree.findHighMarkStudent());

        System.out.println("\nStudent with the lowest mark:");
        System.out.println(studentTree.findLowestMarkStudent());
    }
    
    static void listofScholarships(List<Student> list, BST studentTree, Scanner scanner){
        if (list.isEmpty()) {
            System.out.println("No students available!");
            return;
        }
        
        System.out.println("\nStudents who achieved scholarships Mark > 8.0: ");
        
        boolean hasScholarship = false;
        for (Student student : list) {
            if (student.getMark() > 8.0) {
                System.out.println("ID: " + student.getID() + 
                                   " - Name: " + student.getName() + 
                                   " - Mark: " + student.getMark() +
                                   " - Ranking: " + student.getRanking());
                hasScholarship = true;
            }
        }

        if (!hasScholarship) {
            System.out.println("No students meet the scholarship criteria!");
        }
    }
}
