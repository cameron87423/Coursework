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

@Path("Parents/")
public class ParentController {//

    @POST
    @Path("check")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String check(@FormDataParam("id") int id, @FormDataParam("name") String name) {
        try {
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT FName FROM Students WHERE StudentID = ?");
            ps1.setInt(1, id);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {
                String correctPassword = loginResults.getString(1);
                if (name.equals(correctPassword)) {
                    JSONObject userDetails = new JSONObject();
                    userDetails.put("stuid",id);
                    return userDetails.toString();
                } else {
                    return "{\"error\": \"Incorrect name!\"}";
                }
            } else {
                return "{\"error\": \"Unknown user!\"}";
            }
        } catch (Exception exception) {
            System.out.println("Database error during /parent/check: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @GET
    @Path("stu/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String PS(@PathParam("id") Integer id) {
        System.out.println("Parents/stu/" + id);
        JSONObject item = new JSONObject();
        try {
            if (id == null) {
                throw new Exception("Parent's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.StudentID FROM Students JOIN Parents ON Students.StudentID = Parents.StudentID WHERE ParentID = ?");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("StudentID",1);
            }
            return item.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list item, please see server console for more info.\"}";
        }

    }

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
                    "FROM Students JOIN Parents ON Students.StudentID = Parents.StudentID WHERE StudentID = ?");//SQL to join the two tables and select
            ps.setInt(1,id);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StudentName", results.getString(1));
                item.put("Age", results.getInt(2));
                item.put("Address1", results.getString(3));
                item.put("Address2", results.getString(4));
                item.put("Name", results.getString(5));
                item.put("Surname", results.getString(6));
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
    public String Ppick(@PathParam("id") Integer id) {
        System.out.println("Parents/pick/" + id);
        JSONObject item = new JSONObject();
        try {
            if (id == null) {
                throw new Exception("Parent's id is missing in the HTTP request's URL");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.FName, Students.Address1,Students.Address2, Parents.PFName, Parents.PSName " +
                    "FROM Students JOIN Parents ON Students.StudentID = Parents.StudentID WHERE StudentID = ?");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("StudentID",id);
                item.put("StudentName", results.getString(1));
                item.put("Address1", results.getString(2));
                item.put("Address2", results.getString(3));
                item.put("Name", results.getString(4));
                item.put("Surname", results.getString(5));
            }
            return item.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("Plogin")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("id") int id, @FormDataParam("password") String password) {
        try {
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Parents WHERE ParentID = ?");
            ps1.setInt(1, id);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {
                String correctPassword = loginResults.getString(1);
                if (password.equals(correctPassword)) {
                    String token = UUID.randomUUID().toString();
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Parents SET Token = ? WHERE ParentID = ?");
                    ps2.setString(1, token);
                    ps2.setInt(2, id);
                    ps2.executeUpdate();
                    JSONObject userDetails = new JSONObject();
                    userDetails.put("id",id);
                    userDetails.put("token",token);
                    return userDetails.toString();
                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }
            } else {
                return "{\"error\": \"Unknown user!\"}";
            }
        } catch (Exception exception) {
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("Plogout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) {
        try {
            System.out.println("Parents/logout");
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT ParentID FROM Parents WHERE Token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {
                int id = logoutResults.getInt(1);
                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Parents SET Token = NULL WHERE ParentID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();
                return "{\"status\": \"OK\"}";
            } else {
                return "{\"error\": \"Invalid token!\"}";
            }
        } catch (Exception exception) {
            System.out.println("Database error during /Parents/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Pinsert(@FormDataParam("name") String name, @FormDataParam("surname") String surname,
                          @FormDataParam("password") String password, @FormDataParam("studentID") Integer studentID) {
        try {
            if (name == null || surname == null || password == null || studentID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parents/new");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Parents (PFName, PSName, Password, StudentID) VALUES(?,?,?,?)");
            //SQL for inserting a new record into a table
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, password);
            ps.setInt(4, studentID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to insert items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("change")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Pupdate(@FormDataParam("parentID") Integer parentID, @FormDataParam("studentID") Integer studentID, @FormDataParam("name") String name,
                          @FormDataParam("surname") String surname, @FormDataParam("address1") String address1, @FormDataParam("address2") String address2) {
        try {
            if (name == null || surname == null || address1 == null || address2 == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parent/update");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Parents SET PFName = ?, PSName = ?, StudentID = ? WHERE ParentID = ? " +
                    "UPDATE Students SET Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setInt(3, studentID);
            ps.setInt(4, parentID);
            ps.setString(5, address1);
            ps.setString(6, address2);
            ps.setInt(7, studentID);
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
    public String PupdateP(@FormDataParam("parentID") Integer parentID, @FormDataParam("name") String name, @FormDataParam("password") String password) {
        try {
            if (name == null || password == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parent/updatePassword");
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Parents SET Password = ? WHERE PFName = ?, ParentID = ?");
            ps.setString(1, password);
            ps.setString(2, name);
            ps.setInt(3, parentID);
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
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("parents/delete");
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Parents WHERE ParentID = ?");
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
            PreparedStatement ps = Main.db.prepareStatement("SELECT ParentID FROM Parents WHERE Token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /Parents/logout: " + exception.getMessage());
            return false;
        }
    }
}
