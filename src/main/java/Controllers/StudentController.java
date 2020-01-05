package Controllers;

import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("Students/")
public class StudentController {//

    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public String select() {
        System.out.println("Students/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT FName,SName,Age,Address1,Address2 FROM Students");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Name", results.getString(1));
                item.put("Surname", results.getString(2));
                item.put("Age", results.getInt(3));
                item.put("Address1", results.getString(4));
                item.put("Address2", results.getString(5));
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
    public String pick(@PathParam("id") Integer id) {
        System.out.println("Students/pick/" + id);
        JSONObject item = new JSONObject();
        try {
            if (id == null) {
                throw new Exception("Student's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT FName,SName,Age,Address1,Address2 FROM Students WHERE StudentID = ?");
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("Name", results.getString(1));
                item.put("Surname", results.getString(2));
                item.put("Age", results.getInt(3));
                item.put("Address1", results.getString(4));
                item.put("Address2", results.getString(5));
            }
            return item.toString();
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("TutorList/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String TutorList(@PathParam("id") Integer id) {//shows the info of both the parent and the student
        System.out.println("Students/TutorList/" + id);
        JSONArray list = new JSONArray();
        try {
            if (id == null) {
                throw new Exception("Student's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.FName,Subject.SubjectID, Subject.SubjectN, Tutors.TutorID, Tutors.TFName, Tutors.TSName, Tutors.Rating FROM Students " +
                    "JOIN Subject ON Students.StudentID = Subject.StudentID JOIN Tutors ON Subject.TutorID = Tutors.TutorID WHERE Students.StudentID = ?");//SQL to join the two tables and select
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID",id);
                item.put("StudentName",results.getString(1));
                item.put("SubjectID",results.getString(2));
                item.put("SubjectName",results.getString(3));
                item.put("TutorID",results.getInt(4));
                item.put("TutorName",results.getString(5));
                item.put("TutorSurname",results.getString(6));
                item.put("Rating",results.getDouble(7));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("id") int id, @FormDataParam("password") String password) {
        try {
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Students WHERE StudentID = ?");
            ps1.setInt(1, id);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {
                String correctPassword = loginResults.getString(1);
                if (password.equals(correctPassword)) {
                    String token = UUID.randomUUID().toString();
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Students SET Token = ? WHERE StudentID = ?");
                    ps2.setString(1, token);
                    ps2.setInt(2, id);
                    ps2.executeUpdate();
                    JSONObject userDetails = new JSONObject();
                    userDetails.put("id",id);
                    userDetails.put("token",token);
                    return userDetails.toString();
                } else {
                    return "{\"error\": \"Incorrect ID and password!\"}";
                }
            } else {
                return "{\"error\": \"Unknown user!\"}";
            }
        }catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) {
        try {
            System.out.println("student/logout");
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT StudentID FROM Students WHERE Token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {
                int id = logoutResults.getInt(1);
                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Students SET Token = NULL WHERE StudentID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();
                return "{\"status\": \"OK\"}";
            } else {
                return "{\"error\": \"Invalid token!\"}";
            }
        } catch (Exception exception){
            System.out.println("Database error during /Students/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insert(@FormDataParam("name") String name, @FormDataParam("surname") String surname, @FormDataParam("password") String password,@FormDataParam("age") Integer age,
                         @FormDataParam("gender") String gender, @FormDataParam("address1") String address1, @FormDataParam("address2") String address2){
        try{
            if (name == null || surname == null || password == null || age == null || gender == null || address1 == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("students/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Students (FName, SName, Password, Age, Gender, Address1, Address2) VALUES(?,?,?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,password);
            ps.setInt(4,age);
            ps.setString(5,gender);
            ps.setString(6,address1);
            ps.setString(7,address2);
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
    public String update(@FormDataParam("studentID") Integer studentID,@FormDataParam("name") String name,@FormDataParam("surname") String surname,@FormDataParam("age") Integer age,
                         @FormDataParam("gender") String gender,@FormDataParam("address1") String address1,@FormDataParam("address2") String address2){
        try{
            if (name == null || surname == null || age == null || gender == null || address1 == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("student/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Students SET FName = ?, SName = ?, Age = ?, Gender = ?, Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, age);
            ps.setString(4,gender);
            ps.setString(5, address1);
            ps.setString(6, address2);
            ps.setInt(7,studentID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
        System.out.println("Database error: " + e.getMessage());
        return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("Password")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateP(@FormDataParam("studentID") Integer studentID,@FormDataParam("name") String name,@FormDataParam("password") String password){
        try{
            if (name == null || password == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("student/updatePassword");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Students SET Password = ? WHERE FName = ?,StudentID = ?");
            ps.setString(1, password);
            ps.setString(2, name);
            ps.setInt(3,studentID);
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
    public String delete(@FormDataParam("id") Integer id){
        try {
            if (id == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("student/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Students WHERE StudentID = ?");
            ps.setInt(1, id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StudentID FROM Students WHERE Token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /Students/logout: " + exception.getMessage());
            return false;
        }
    }
}
