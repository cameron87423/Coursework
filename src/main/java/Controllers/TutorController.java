package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Tutors/")
public class TutorController{//
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String Tselect() {
        System.out.println("Tutors/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT TFName,TSName,Experience,Rating FROM Tutors");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Name",results.getString(1));
                item.put("Surname",results.getString(2));
                item.put("Experience",results.getInt(3));
                item.put("Rating",results.getDouble(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("pick/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String Tpick(@PathParam("id") Integer id) {
        System.out.println("Tutors/pick/" + id);
        JSONObject item = new JSONObject();
        try {
            if (id == null) {
                throw new Exception("Tutor's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT TFName,TSName,Gender,Experience,Rating FROM Tutors WHERE TutorID = ?");
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("TutorID", id);
                item.put("Name", results.getString(1));
                item.put("Surname", results.getString(2));
                item.put("Gender", results.getString(3));
                item.put("Experience", results.getInt(4));
                item.put("Rating", results.getDouble(5));
            }
            return item.toString();
        }catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    @GET
    @Path("StudentList/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String TutorList(@PathParam("id") Integer id) {//shows the info of both the parent and the student
        System.out.println("Students/StudentList/" + id);
        JSONArray list = new JSONArray();
        try {
            if (id == null) {
                throw new Exception("Tutor's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT Tutors.TFName, Subjects.SubjectN, Students.StudentID, Students.FName, Students.SName" +
                    "FROM Tutors JOIN Subjects ON Tutors.TutorID = Subjects.TutorID JOIN Students ON Subjects.StudentID = Students.StudentID WHERE TutorID = ?");//SQL to join the two tables and select
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID", id);
                item.put("TutorName",results.getString(1));
                item.put("SubjectName",results.getString(2));
                item.put("StudentID",results.getInt(3));
                item.put("StudentName",results.getString(4));
                item.put("StudentSurname",results.getString(5));;
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Tinsert(@FormDataParam("name") String name, @FormDataParam("surname") String surname,
                         @FormDataParam("gender") String gender, @FormDataParam("experience") Integer experience, @FormDataParam("rating") Double rating){
        try{
            if (name == null || surname == null || gender == null || experience == null || rating == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("tutors/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Tutors (TFName, TSName, Gender, Experience, Rating) VALUES(?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,gender);
            ps.setInt(4,experience);
            ps.setDouble(5,rating);
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
    public String  Tupdate(@FormDataParam("tutorID") Integer tutorID,@FormDataParam("name") String name,@FormDataParam("surname") String surname,
                         @FormDataParam("gender") String gender,@FormDataParam("experience") Integer experience,@FormDataParam("rating") Integer rating){
        try{
            if (name == null || surname == null || gender == null|| experience == null || rating == null  ){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("tutor/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Tutors SET TFName = ?, TSName = ?, Gender = ?, Experience = ?, Rating = ? WHERE TutorID = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, gender);
            ps.setInt(4, experience);
            ps.setDouble(5, rating);
            ps.setInt(6, tutorID);
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
    public String Tdelete(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("tutors/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tutors WHERE TutorID = ?");
            ps.setInt(1,id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
