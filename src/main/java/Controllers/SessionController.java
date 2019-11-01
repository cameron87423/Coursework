package Controllers;

import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Sessions/")
public class SessionController {
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String Sselect() {
        System.out.println("Sessions/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID,TutorID,Review,Hours,Pay,Grade FROM Sessions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",results.getInt(1));
                item.put("TutorID",results.getInt(2));
                item.put("Review",results.getDouble(3));
                item.put("Hours",results.getInt(4));
                item.put("Pay",results.getDouble(5));
                item.put("Grade",results.getString(6));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
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
