package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionController {
    public static void Sselect() {//no
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID,TutorID,Review,Hours,Pay,Grade FROM Sessions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int StudentID = results.getInt(1);
                int TutorID = results.getInt(2);
                double Review = results.getDouble(3);
                int Hours = results.getInt(4);
                double Pay = results.getDouble(5);
                String Grade = results.getString(6);
                System.out.println(StudentID + " "+ TutorID + " " + Review + " " + Hours + " " + Pay + " " + Grade);

            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Sinsert(int StudentID, int TutorID, double Review, int Hours, double Pay, String Grade){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Sessions (StudentID, TutorID, Review, Hours, Pay, Grade) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, StudentID);
            ps.setInt(2, TutorID);
            ps.setDouble(3, Review);
            ps.setInt(4, Hours);
            ps.setDouble(5, Pay);
            ps.setString(6, Grade);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Supdate(int StudentID, int TutorID, double Review, int Hours, double Pay, String Grade, int SessionID ){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Sessions SET StudentID = ?, TutorID = ?, Review = ?, Hours = ?, Pay = ?, Grade = ? WHERE SessionID = ?");
            ps.setInt(1, StudentID);
            ps.setInt(2, TutorID);
            ps.setDouble(3, Review);
            ps.setInt(4,Hours);
            ps.setDouble(5, Pay);
            ps.setString(6, Grade);
            ps.setInt(7, SessionID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Sdelete(int SessionID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Sessions WHERE SessionID = ?");
            ps.setInt(1,SessionID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
