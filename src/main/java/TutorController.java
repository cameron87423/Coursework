import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TutorController {
        public static void Tselect() {
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Tutors");
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int TutorID = results.getInt(1);
                    String TFName = results.getString(2);
                    String TSName = results.getString(3);
                    String Gender = results.getString(4);
                    int Experience = results.getInt(5);
                    String Subject = results.getString(6);
                    Double Rating = results.getDouble(7);
                    System.out.println(TutorID + " " + TFName + " "+ TSName + " " + Gender + " " + Experience + " " + Subject + " " + Rating);

                }
            } catch (Exception e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void Tinsert(String TFName, String TSName, String Gender, int Experience, String Subject, Double Rating){
            try{
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Tutors (TFName, TSName, Gender, Experience, Subject, Rating) VALUES(?,?,?,?,?,?)");
                ps.setString(1, TFName);
                ps.setString(2, TSName);
                ps.setString(3, Gender);
                ps.setInt(4, Experience);
                ps.setString(5, Subject);
                ps.setDouble(6, Rating);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        public static void Tupdate(String TFName, String TSName, String Gender, int Experience, String Subject, Double Rating, int TutorID ){
            try {
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Tutors SET TFName = ?, TSName = ?, Gender = ?, Experience = ?, Subject = ?, Rating = ? WHERE TutorID = ?");
                ps.setString(1, TFName);
                ps.setString(2, TSName);
                ps.setString(3, Gender);
                ps.setInt(4, Experience);
                ps.setString(5, Subject);
                ps.setDouble(6, Rating);
                ps.setInt(7, TutorID);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    public static void Tdelete(int TutorID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tutors WHERE TutorID = ?");
            ps.setInt(1,TutorID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
