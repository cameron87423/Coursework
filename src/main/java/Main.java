import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.sqlite.SQLiteConfig;
public class Main {

    public static Connection db = null;// behaves as a global variable
//this is the main method
    public static void main(String[] args) {
        openDatabase("courseworkDB.db"); // code to get data from, write to the database etc goes here...
        select();
        insert()
        closeDatabase();
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

    public static void select() {
        try {
            PreparedStatement ps = db.prepareStatement("SELECT StudentID, StudentName, DoB, Gender FROM Students");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int StudentID = results.getInt(1);
                String StudentName = results.getString(2);
                String DoB = results.getString(3);
                String Gender = results.getString(4);
                System.out.println(StudentID + " " + StudentName + " " + DoB + " " + Gender);

            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insert(String StudentName, String Dob, String Gender){
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO Students (Student)")
        }
    }

    public static void update(){

    }

    public static void delete(){

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

