import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParentController {
    public static void Tselect() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Parents");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int ParentID = results.getInt(1);
                String PFName = results.getString(2);
                String PSName = results.getString(3);
                int StudentID = results.getInt(4);
                System.out.println(ParentID + " " + PFName + " "+ PSName + " " + StudentID);

            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void Tinsert(String PFName, String PSName, int StudentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Parents (PFName, PSName, StudentID) VALUES(?,?,?)");
            ps.setString(1, PFName);
            ps.setString(2, PSName);
            ps.setInt(3, StudentID);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void Tupdate(String PFName, String PSName, int StudentID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Parents SET PFName = ?, PSName = ?, StudentID = ? WHERE ParentID = ?");
            ps.setString(1, PFName);
            ps.setString(2, PSName);
            ps.setInt(3, StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Tdelete(int ParentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Parents WHERE ParentID = ?");
            ps.setInt(1,ParentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
