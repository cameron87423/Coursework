package Controllers;

import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Parents/")
public class ParentController {
    @GET
    @Path("child/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String PASInfo(@PathParam("id") Integer id) {//shows the info of both the parent and the student
        if (id == null) {
            throw new IllegalArgumentException("Student's id is missing in the HTTP request's URL");
        }
        System.out.println("Parents/child/" + id);
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.FName, Students.Age, Students.Address1,Students.Address2, Parents.PFName, Parents.PSName," +
                                                  "FROM Students JOIN Parents ON Students.StudentID = Parents.StuedntID WHERE StudentID = ?");//SQL to join the two tables and select
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentID", id);
                item.put("StudentName",results.getString(1));
                item.put("Age",results.getInt(2));
                item.put("Address1",results.getString(3));
                item.put("Address2",results.getString(4));
                item.put("Name",results.getString(5));
                item.put("Surname",results.getString(6));
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
        if (id == null) {
            throw new IllegalArgumentException("Parent's id is missing in the HTTP request's URL");
        }
        System.out.println("Parents/pick/" + id);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.FName, Students.Age, Students.Address1,Students.Address2, Parents.PFName, Parents.PSName " +
                    "FROM Students JOIN Parents ON Students.StudentID = Parents.StuedntID WHERE StudentID = ?");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("StudentID", id);
                item.put("StudentName", results.getString(1));
                item.put("Age", results.getInt(2));
                item.put("Address1", results.getString(3));
                item.put("Address2", results.getString(4));
                item.put("Name", results.getString(5));
                item.put("Surname", results.getString(6));
            }
            return item.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Pinsert(@FormDataParam("name") String name, @FormDataParam("surname") String surname,
                         @FormDataParam("studentID") Integer studentID){
        try{
            if (name == null || surname == null || studentID == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parents/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Parents (PFName, PSName, StudentID) VALUES(?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setInt(3,studentID);
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
    public String Pupdate(@FormDataParam("parentID") Integer parentID,@FormDataParam("studentID") Integer studentID,@FormDataParam("name") String name,
                         @FormDataParam("surname") String surname,@FormDataParam("studentname") String studentname, @FormDataParam("studentsurname") String studentsurname,
                         @FormDataParam("address1") String address1,@FormDataParam("address2") String address2){
        try{
            if (name == null || surname == null || studentname == null|| studentsurname == null || address1 == null || address2 == null){
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parent/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Parents SET PFName = ?, PSName = ?, StudentID = ? WHERE ParentID = ? " +
                    "UPDATE Students SET FName = ?, SName = ?, Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, studentID);
            ps.setInt(4,parentID);
            ps.setString(5,studentname);
            ps.setString(6,studentsurname);
            ps.setString(7,address1);
            ps.setString(8,address2);
            ps.setInt(9,studentID);
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
    public String Pdelete(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parents/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Parents WHERE ParentID = ?");
            ps.setInt(1,id);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
