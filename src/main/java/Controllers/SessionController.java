package Controllers;

import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Sessions/")
public class SessionController {
    @GET
    @Path("StudentSessions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String StudentSessions(@PathParam("id") Integer id) {
        System.out.println("Sessions/StudentSessions/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT TutorID, Hours, Pay, Grade FROM Sessions WHERE StudentID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",id);
                item.put("TutorID",results.getInt(1));
                item.put("Hours",results.getInt(2));
                item.put("Pay",results.getDouble(3));
                item.put("Grade",results.getString(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("TutorSessions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String TutorSessions(@PathParam("id") Integer id) {
        System.out.println("Sessions/TutorSessions/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID, Hours, Pay, Grade FROM Sessions WHERE TutorID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID",id);
                item.put("StudentID",results.getInt(1));
                item.put("Hours",results.getInt(2));
                item.put("Pay",results.getDouble(3));
                item.put("Grade",results.getString(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("ListSessions/{StudentID}{TutorID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListSessions(@PathParam("StudentID") Integer StudentID, @PathParam("TutorID") Integer TutorID) {
        System.out.println("Sessions/TutorSessions/" + StudentID + " " + TutorID);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Hours, Pay, Grade FROM Sessions WHERE TutorID = ?, StudentID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID",TutorID);
                item.put("StudentID",StudentID);
                item.put("Hours",results.getInt(1));
                item.put("Pay",results.getDouble(2));
                item.put("Grade",results.getString(3));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Seinsert(@FormDataParam("studentID") Integer studentID, @FormDataParam("tutorID") Integer tutorID, @FormDataParam("review") Double review,
                         @FormDataParam("hours") Integer hours, @FormDataParam("pay") Double pay, @FormDataParam("grade") String grade){
        try{
            if (studentID == null || tutorID == null || review == null || hours == null || pay == null || grade == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("sessions/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Sessions (StudentID, TutorID, Review, Hours, Pay, Grade) VALUES(?,?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setInt(1,studentID);
            ps.setInt(2,tutorID);
            ps.setDouble(3,review);
            ps.setInt(4,hours);
            ps.setDouble(5,pay);
            ps.setString(6,grade);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to insert items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("change")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Seupdate(@FormDataParam("sessionID") Integer sessionID,@FormDataParam("studentID") Integer studentID,@FormDataParam("tutorID") Integer tutorID,
                           @FormDataParam("review") Double review, @FormDataParam("hours") Integer hours, @FormDataParam("pay") Double pay, @FormDataParam("grade") String grade){
        try{
            if (studentID == null || tutorID == null || review == null || hours == null || pay == null || grade == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("session/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Sessions SET StudentID = ?, TutorID = ?, Review = ?, Hours = ?, Pay = ?, Grade = ? WHERE SessionID = ?");
            ps.setInt(1, studentID);
            ps.setInt(2, tutorID);
            ps.setDouble(3, review);
            ps.setInt(4,hours);
            ps.setDouble(5, pay);
            ps.setString(6, grade);
            ps.setInt(7, sessionID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Sedelete(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Session/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Sessions WHERE SessionID = ?");
            ps.setInt(1,id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
