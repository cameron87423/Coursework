import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TutorController {//n
        public static void Tselect() {
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT TFName,TSName,Gender,Experience,Rating FROM Tutors");
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int TutorID = results.getInt(1);
                    String TFName = results.getString(2);
                    String TSName = results.getString(3);
                    String Gender = results.getString(4);
                    int Experience = results.getInt(5);
                    Double Rating = results.getDouble(6);
                    System.out.println(TutorID + " " + TFName + " "+ TSName + " " + Gender + " " + Experience + " " + Rating);

                }
            } catch (Exception e) {
                System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
            }
        }

        public static void Tinsert(String TFName, String TSName, String Gender, int Experience, String Subject, Double Rating){
            try{
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Tutors (TFName, TSName, Gender, Experience, Rating) VALUES(?,?,?,?,?)");
                ps.setString(1, TFName);
                ps.setString(2, TSName);
                ps.setString(3, Gender);
                ps.setInt(4, Experience);
                ps.setDouble(5, Rating);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
            }
        }

        public static void Tupdate(String TFName, String TSName, String Gender, int Experience, Double Rating, int TutorID ){
            try {
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Tutors SET TFName = ?, TSName = ?, Gender = ?, Experience = ?, Rating = ? WHERE TutorID = ?");
                ps.setString(1, TFName);
                ps.setString(2, TSName);
                ps.setString(3, Gender);
                ps.setInt(4, Experience);
                ps.setDouble(5, Rating);
                ps.setInt(6, TutorID);
                System.out.println("Record has been updated");
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
            }
        }

    public static void Tdelete(int TutorID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tutors WHERE TutorID = ?");
            ps.setInt(1,TutorID);
            ps.executeUpdate();
            System.out.println("Record has been deleted");
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
