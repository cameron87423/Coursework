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

@Path("Information/")
public class InformationController {
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String Iselect() {
        System.out.println("Information/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT THours,TPay,RPay,TGrade,StudentID,TutorID FROM Information");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Hours",results.getInt(1));
                item.put("Pay",results.getDouble(2));
                item.put("Remaining Pay",results.getDouble(3));
                item.put("Grade",results.getString(4));
                item.put("StudentID",results.getInt(5));
                item.put("TutorID",results.getInt(6));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    public static void Iinsert(int THours, double TPay, double RPay, String TGrade, int StudentID, int TutorID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Information(THours, TPay, RPay, TGrade, StudentID, TutorID) VALUES(?,?,?,?,?,?)");
            ps.setInt(1,THours);
            ps.setDouble(2,TPay);
            ps.setDouble(3,RPay);
            ps.setString(4,TGrade);
            ps.setInt(5,StudentID);
            ps.setInt(6,TutorID);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Iupdate(int THours, double TPay, double RPay, String TGrade, int StudentID, int TutorID, int InfoID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Information SET THours = ?, TPay = ?, RPay = ?, TGrade = ?, StudentID = ?, TutorID = ? WHERE InfoID = ?");
            ps.setInt(1,THours);
            ps.setDouble(2,TPay);
            ps.setDouble(3,RPay);
            ps.setString(4,TGrade);
            ps.setInt(5,StudentID);
            ps.setInt(6,TutorID);
            ps.setInt(7,InfoID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
    public static void Idelete(int InfoID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Information WHERE InfoID = ?");
            ps.setInt(1,InfoID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
