import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class StudentController {//delete this comment
    public static void select() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT FName,SName,Age,Address1,Address2 FROM Students");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String FName = results.getString(1);
                String SName = results.getString(2);
                int Age = results.getInt(3);
                String Address1 = results.getString(4);
                String Address2 = results.getString(5);
                System.out.println(FName + " "+ SName + " " + Age + " " + Address1 + " " + Address2);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void pick(int StudentID) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID,FName,SName,Age,Address1,Address2 FROM Students WHERE StudentID = ?");
            ps.setInt(1,StudentID);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String FName = results.getString(2);
                String SName = results.getString(3);
                int Age = results.getInt(4);
                String Address1 = results.getString(5);
                String Address2 = results.getString(6);
                System.out.println(FName + " " + SName + " " + Age + " " + Address1 + " " + Address2);
            }
            }
        catch (Exception e){
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void insert(String FName, String SName, int Age, String Gender, String Address1, String Address2){//inserts a new record into the students
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Students (FName, SName, Age, Gender, Address1, Address2) VALUES(?,?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setInt(3, Age);
            ps.setString(4, Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);

            ps.executeUpdate();
        }catch (Exception e){//throws an error message if the program has an error throughout
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void update(String FName, String SName,int Age, String Gender, String Address1, String Address2, int StudentID ){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Students SET FName = ?, SName = ?, Age = ?, Gender = ?, Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setInt(3, Age);
            ps.setString(4,Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);
            ps.setInt(7, StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void delete(int StudentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Students WHERE StudentID = ?");
            ps.setInt(1,StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
