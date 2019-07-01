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
        StudentController.select();
        String FName = "James";
        String SName = "Bishop";
        String DoB = "22/03/2002";
        String Gender = "male";
        String Address1 = "1 Grange Lane";
        String Address2 = "Hook";
        int StudentID = 1;
        StudentController.insert(FName,SName,DoB,Gender,Address1,Address2);
        StudentController.update(FName,SName,DoB,Gender,Address1,Address2,StudentID);
        StudentController.delete(StudentID);
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

