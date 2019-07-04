import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InformationController {
    public static void Iselect() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Information");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int InfoID = results.getInt(1);
                double Hours = results.getDouble(2);
                double TPay = results.getDouble(3);
                double RPay = results.getDouble(4);
                String SGrade = results.getString(5);
                String TuGrade = results.getString(6);
                int StudentID = results.getInt(7);
                int TutorID = results.getInt(8);
                System.out.println(InfoID + " " + Hours + " "+ TPay + " " + RPay + " " + SGrade + " " + TuGrade + " " + StudentID + " " + TutorID);

            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void Iinsert(double Hours, double TPay){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Information () VALUES(?,?,?)");
            ps.setString(1, PFName);
            ps.setString(2, PSName);
            ps.setInt(3, StudentID);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void Pupdate(String PFName, String PSName, int StudentID){
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
}
