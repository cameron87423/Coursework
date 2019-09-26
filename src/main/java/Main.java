import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.sqlite.SQLiteConfig;
public class Main {//program in the rest of the selection menu and function

    public static Connection db = null;// behaves as a global variable
    //this is the main method
    public static void main(String[] args) {//no
        Scanner sc = new Scanner(System.in);
        try {
            openDatabase("courseworkDB.db"); // code to get data from, write to the database etc goes here...
            System.out.println("Which controller do you want to access?");
            System.out.println("1 student, 2 tutor, 3 parent, 4 Subject, 5 session, 6 information: ");
            int opt1 = sc.nextInt();
            System.out.println("What function do you want to do?");
            System.out.println("1 read, 2 create, 3 update, 4 delete, 5 pick: ");
            int opt2 = sc.nextInt();

            if (opt1 == 1) {
                if (opt2 == 1) {
                    StudentController.select();
                } else if (opt2 == 2) {
                    //create function for these terms each
                    sc.nextLine();
                    System.out.println("enter FName: ");
                    String FName = sc.nextLine();
                    System.out.println("enter SName: ");
                    String SName = sc.nextLine();
                    System.out.println("enter Age: ");
                    int Age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("enter Gender: ");
                    String Gender = sc.nextLine();
                    System.out.println("enter Address1: ");
                    String Address1 = sc.nextLine();
                    System.out.println("enter Address2: ");
                    String Address2 = sc.nextLine();
                    System.out.println(FName + " " + SName + " " + Age + " " + Gender + " " + Address1 + " " + Address2);
                    StudentController.insert(FName, SName, Age, Gender, Address1, Address2);

                } else if (opt2 == 3) {
                    System.out.println();
                    sc.nextLine();
                    System.out.println("enter FName: ");
                    String FName = sc.nextLine();
                    System.out.println("enter SName: ");
                    String SName = sc.nextLine();
                    System.out.println("enter Age: ");
                    int Age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("enter Gender: ");
                    String Gender = sc.nextLine();
                    System.out.println("enter Address1: ");
                    String Address1 = sc.nextLine();
                    System.out.println("enter Address2: ");
                    String Address2 = sc.nextLine();
                    System.out.println("enter search ID: ");
                    int StudentID = sc.nextInt();
                    sc.nextLine();
                    StudentController.update(FName, SName, Age, Gender, Address1, Address2, StudentID);
                } else if (opt2 == 4) {
                    System.out.println();
                    System.out.println("enter search ID: ");
                    int StudentID = sc.nextInt();
                    sc.nextLine();
                    StudentController.delete(StudentID);
                }else if (opt2 == 5) {
                    System.out.println("enter search ID: ");
                    int StudentID = sc.nextInt();
                    StudentController.pick(StudentID);
                }
            } else if (opt1 == 2) {
                if (opt2 == 3) {
                    sc.nextLine();
                    System.out.println("enter FName: ");
                    String TFName = sc.nextLine();
                    System.out.println("enter SName: ");
                    String TSName = sc.nextLine();
                    System.out.println("enter Gender: ");
                    String Gender = sc.nextLine();
                    System.out.println("enter Experience in years: ");
                    int Experience = sc.nextInt();
                    sc.nextLine();
                    System.out.println("enter Rating: ");
                    double Rating = sc.nextDouble();
                    System.out.println("enter TutorID: ");
                    int TutorID = sc.nextInt();
                    TutorController.Tupdate(TFName, TSName, Gender, Experience, Rating, TutorID);
                }
                if (opt2 == 4) {
                    System.out.println("enter search ID: ");
                    int TutorID = sc.nextInt();
                    sc.nextLine();
                    TutorController.Tdelete(TutorID);
                }
            }


            closeDatabase();
        }catch(Exception e){
            System.out.println("Selection error: " + e.getMessage() + " Please enter an appropriate value next time");
        }
    }
    //this opens the database
    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC"); // this loads the database driver
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true); // needed in order to change the original database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) { // throws an exception if the database can't connect to the code
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }
    // this closes the database
    private static void closeDatabase(){
        try {
            db.close(); // closes the database
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}

