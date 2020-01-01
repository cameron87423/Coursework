package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Subjects/")
public class SubjectController {//
    @GET
    @Path("StudentSubjects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String StudentSubjects(@PathParam("id") Integer id) {
        System.out.println("Subjects/StudentSubjects/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectN,TutorID FROM Subject WHERE StudentID = ?");
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",id);
                item.put("SubjectName",results.getString(1));
                item.put("TutorID",results.getInt(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("TutorSubjects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String TutorSubjects(@PathParam("id") Integer id) {
        System.out.println("Subjects/TutorSubjects/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectN,StudentID FROM Subject WHERE TutorID = ?");
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TutorID",id);
                item.put("SubjectName",results.getString(1));
                item.put("StudentID",results.getInt(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("ListSubjects/{StudentID}{TutorID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListSubjects(@PathParam("StudentID") Integer StudentID, @PathParam("TutorID") Integer TutorID) {
        System.out.println("Subjects/ListSubjects/" + StudentID + " " + TutorID);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectN, FROM Subject WHERE StudentID = ? AND TutorID = ?");
            ps.setInt(1,StudentID);
            ps.setInt(2,TutorID);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",StudentID);
                item.put("TutorID",TutorID);
                item.put("SubjectName",results.getString(1));
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
    public String Sinsert(@FormDataParam("subjectName") String subjectName, @FormDataParam("studentID") Integer studentID,
                         @FormDataParam("tutorID") Integer tutorID){
        try{
            if (subjectName == null || tutorID == null || studentID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("subjects/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Subject(SubjectN,StudentID,TutorID) VALUES(?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1,subjectName);
            ps.setInt(2,studentID);
            ps.setInt(3,tutorID);
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
    public String Supdate(@FormDataParam("subjectID") Integer subjectID,@FormDataParam("studentID") Integer studentID,@FormDataParam("tutorID") Integer tutorID,
                         @FormDataParam("subjectName") String subjectName){
        System.out.println(subjectID);
        System.out.println(studentID);
        System.out.println(tutorID);
        System.out.println(subjectName);

        try{
            if (subjectName == null || studentID == null || tutorID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("subject/change");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Subject SET SubjectN = ?,StudentID = ?,TutorID = ? WHERE SubjectID = ?");
            ps.setString(1, subjectName);
            ps.setInt(2, studentID);
            ps.setInt(3, tutorID);
            ps.setInt(4, subjectID);
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
    public String Sdelete(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Subject/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Subject WHERE SubjectID = ?");
            ps.setInt(1,id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
