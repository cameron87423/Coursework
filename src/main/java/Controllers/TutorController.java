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

@Path("Tutors/")
public class TutorController{
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String Tselect() {
        System.out.println("Tutors/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT TFName,TSName,Gender,Experience,Rating FROM Tutors");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Name",results.getString(1));
                item.put("Surname",results.getString(2));
                item.put("Gender",results.getString(3));
                item.put("Experience",results.getInt(4));
                item.put("Rating",results.getDouble(5));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
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
