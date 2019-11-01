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

@Path("Subjects/")
public class SubjectController {
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String Subselect() {
        System.out.println("Subjects/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectN,StudentID,TutorID FROM Subject");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("SubjectName",results.getString(1));
                item.put("StudentID",results.getInt(2));
                item.put("TutorID",results.getInt(3));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
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
