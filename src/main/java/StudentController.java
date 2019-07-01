import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class StudentController {

    public static void select() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Students");
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
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void insert(String FName, String SName, String DoB, String Gender, String Address1, String Address2){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Students (FName, SName, DoB, Gender, Address1, Address2) VALUES(?,?,?,?,?,?)");
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setString(3, DoB);
            ps.setString(4, Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);

            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void update(String FName, String SName,String DoB, String Gender, String Address1, String Address2, int StudentID ){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Students SET FName = ?, SName = ?, DoB = ?, Gender = ?, Address1 = ?, Address2 = ?, WHERE StudentID = ?");
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setString(3, DoB);
            ps.setString(5, Address1);
            ps.setString(6, Address2);
            ps.setInt(7, StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete(int StudentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Students WHERE StudentID = ?");
            ps.setInt(1,StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
