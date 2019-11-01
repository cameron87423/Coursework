package Controllers;

import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Students/")
public class StudentController {

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
        if (id == null) {
            throw new IllegalArgumentException("Student's id is missing in the HTTP request's URL");
        }
        System.out.println("Students/pick/" + id);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT FName,SName,Age,Address1,Address2 FROM Students WHERE StudentID = ?");
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("StudentID", id);
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

    public static void insert(String FName, String SName, int Age, String Gender, String Address1, String Address2){//inserts a new record into the students
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Students (FName, SName, Age, Gender, Address1, Address2) VALUES(?,?,?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setInt(3, Age);
            ps.setString(4, Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);

            ps.executeUpdate();
        }catch (Exception e){//throws an error message if the program has an error throughout
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void update(String FName, String SName,int Age, String Gender, String Address1, String Address2, int StudentID ){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Students SET FName = ?, SName = ?, Age = ?, Gender = ?, Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, FName);
            ps.setString(2, SName);
            ps.setInt(3, Age);
            ps.setString(4,Gender);
            ps.setString(5, Address1);
            ps.setString(6, Address2);
            ps.setInt(7, StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void delete(int StudentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Students WHERE StudentID = ?");
            ps.setInt(1,StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
