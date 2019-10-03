package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParentController {
    public static void PASInfo() {//shows the info of both the parent and the student
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Students.FName, Students.Age, Students.Address1,Students.Address2, Parents.PFName, Parents.PSName," +
                                                  " Parents.StudentID FROM Students JOIN Parents ON Students.StudentID = Parents.ParentID");//SQL to join the two tables and select
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String FName = results.getString(1);
                int Age = results.getInt(2);
                String Address1 = results.getString(3);
                String Address2 = results.getString(4);
                String PFName = results.getString(5);
                String PSName = results.getString(6);
                System.out.println(FName + " " + Age + " " + Address1 + " " + Address2 + " "+ PFName + " "+ PSName);//prints out each of the responses

            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Pinsert(String PFName, String PSName, int StudentID){//creates a new account for a parent
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Parents (PFName, PSName, StudentID) VALUES(?,?,?)");
            //SQL code to insert a new record
            ps.setString(1, PFName);
            ps.setString(2, PSName);
            ps.setInt(3, StudentID);

            ps.executeUpdate();
        }catch (Exception e){//throws an error message if the program has an error throughout
            System.out.println("Database error: " + e.getMessage() + "Please contact help@StuTu.com for more information");
        }
    }

    public static void PASupdate(String PFName, String PSName, int StudentID,int ParentID, String FName, String SName, String Address1, String Address2){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Parents SET PFName = ?, PSName = ?, StudentID = ? WHERE ParentID = ? " +
                                                        "UPDATE Students SET FName = ?, SName = ?, Address1 = ?, Address2 = ? WHERE StudentID = ?");
            ps.setString(1, PFName);
            ps.setString(2, PSName);
            ps.setInt(3, StudentID);
            ps.setInt(4,ParentID);
            ps.setString(5,FName);
            ps.setString(6,SName);
            ps.setString(7,Address1);
            ps.setString(8,Address2);
            ps.setInt(9,StudentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + "Please contact help@StuTu.com for more information");
        }
    }

    public static void Pdelete(int ParentID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Parents WHERE ParentID = ?");
            ps.setInt(1,ParentID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
