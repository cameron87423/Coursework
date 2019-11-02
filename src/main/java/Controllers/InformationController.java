package Controllers;

import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Information/")
public class InformationController {
    @GET
    @Path("Students/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String StudentSessions(@PathParam("id") Integer id) {
        System.out.println("Information/Students/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT TutorID, THours, TPay, RPay, TGrade FROM Information WHERE StudentID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",id);
                item.put("TutorID",results.getInt(1));
                item.put("THours",results.getInt(2));
                item.put("TotalPay",results.getDouble(3));
                item.put("RemainingPay",results.getDouble(4));
                item.put("Grade",results.getString(5));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("Tutors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String TutorSessions(@PathParam("id") Integer id) {
        System.out.println("Information/Tutors/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID, THours, TPay, RPay, TGrade FROM Information WHERE TutorID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID",id);
                item.put("StudentID",results.getInt(1));
                item.put("THours",results.getInt(2));
                item.put("TotalPay",results.getDouble(3));
                item.put("RemainingPay",results.getDouble(4));
                item.put("Grade",results.getString(5));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("One/{StudentID}{TutorID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String One(@PathParam("StudentID") Integer StudentID, @PathParam("TutorID") Integer TutorID) {
        System.out.println("Information/Tutors/" + StudentID + " " + TutorID);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT THours, TPay, RPay, TGrade FROM Information WHERE TutorID = ?, StudentID = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID", TutorID);
                item.put("Student", StudentID);
                item.put("THours",results.getInt(1));
                item.put("TotalPay",results.getDouble(2));
                item.put("RemainingPay",results.getDouble(3));
                item.put("Grade",results.getString(4));
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
    public String Iinsert(@FormDataParam("thours") Integer thours, @FormDataParam("tpay") Double tpay, @FormDataParam("rpay") Double rpay,
                         @FormDataParam("tgrade") String tgrade, @FormDataParam("studentID") Integer studentID, @FormDataParam("tutorID") Integer tutorID){
        try{
            if (studentID == null || tutorID == null || thours == null || tpay == null || rpay == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("sessions/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Information(THours, TPay, RPay, TGrade, StudentID, TutorID) VALUES(?,?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setInt(1,thours);
            ps.setDouble(2,tpay);
            ps.setDouble(3,rpay);
            ps.setString(4,tgrade);
            ps.setInt(5,studentID);
            ps.setInt(6,tutorID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("change")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Iupdate(@FormDataParam("infoID") Integer infoID,@FormDataParam("thours") Integer thours,@FormDataParam("tpay") Double tpay,
                           @FormDataParam("rpay") Double rpay, @FormDataParam("tgrade") String tgrade, @FormDataParam("studentID") Integer studentID, @FormDataParam("tutorID") Integer tutorID){
        try{
            if (studentID == null || tutorID == null || thours == null || tpay == null || rpay == null || tgrade == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("information/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Information SET THours = ?, TPay = ?, RPay = ?, TGrade = ?, StudentID = ?, TutorID = ? WHERE InfoID = ?");
            ps.setInt(1,thours);
            ps.setDouble(2,tpay);
            ps.setDouble(3,rpay);
            ps.setString(4,tgrade);
            ps.setInt(5,studentID);
            ps.setInt(6,tutorID);
            ps.setInt(7,infoID);
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
    public String Idelete(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Information/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Information WHERE InfoID = ?");
            ps.setInt(1,id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
