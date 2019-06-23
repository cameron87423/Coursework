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
        String FName = "James";
        String SName = "Bishop";
        String DoB = "22/03/2002";
        String Gender = "male";
        String Address1 = "1 Grange Lane";
        String Address2 = "Hook";
        insert(FName,SName,DoB,Gender,Address1,Address2);
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
            PreparedStatement ps = db.prepareStatement("SELECT StudentID, FName, SName, DoB, Gender, Address1, Address2 FROM Students");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int StudentID = results.getInt(1);
                String FName = results.getString(2);
                String SName = results.getString(3);
                String DoB = results.getString(4);
                String Gender = results.getString(5);
                String Address1 = results.getString(6);
                String Address2 = results.getString(7);
                System.out.println(StudentID + " " + FName + " "+ SName + " " + DoB + " " + Gender + " " + Address1 + " " + Address2);

            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insert(String FName, String SName, String DoB, String Gender, String Address1, String Address2){
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO Students (FName, SName, DoB, Gender, Address1, Address2) VALUES(?,?,?,?,?,?)");
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setString(3, DoB);
            ps.setString(4, Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);

            ps.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
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

