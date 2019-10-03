import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SubjectController {
    public static void Subselect() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectN,StudentID,TutorID FROM Subject");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String SubjectN = results.getString(1);
                int StudentID = results.getInt(2);
                int TutorID = results.getInt(3);
                System.out.println(SubjectN + " " + StudentID + " " + TutorID);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
    public static void Subinsert(String SubjectN, int StudentID, int TutorID){//inserts a new record into the students
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Subject(SubjectN,StudentID,TutorID) VALUES(?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1, SubjectN);
            ps.setInt(2, StudentID);
            ps.setInt(3,TutorID);
            ps.executeUpdate();
        }catch (Exception e){//throws an error message if the program has an error throughout
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
    public static void Subupdate(String SubjectN, int StudentID, int TutorID,int SubjectID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Subject SET SubjectN = ?,StudentID = ?,TutorID = ? WHERE SubjectID = ?");
            ps.setString(1, SubjectN);
            ps.setInt(2, StudentID);
            ps.setInt(3, TutorID);
            ps.setInt(4, SubjectID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
    public static void Subdelete(int SubjectID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Subject WHERE SubjectID = ?");
            ps.setInt(1,SubjectID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
