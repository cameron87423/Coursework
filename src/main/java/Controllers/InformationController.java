package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InformationController {
    public static void Iselect() {   //test
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT THours,TPay,RPay,TGrade,StudentID,TutorID FROM Information");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                double THours = results.getDouble(1);
                double TPay = results.getDouble(2);
                double RPay = results.getDouble(3);
                String TGrade = results.getString(4);
                int StudentID = results.getInt(5);
                int TutorID = results.getInt(6);
                System.out.println(THours + " "+ TPay + " " + RPay + " " + TGrade + " " + StudentID + " " + TutorID);

            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Iinsert(int THours, double TPay, double RPay, String TGrade, int StudentID, int TutorID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Information(THours, TPay, RPay, TGrade, StudentID, TutorID) VALUES(?,?,?,?,?,?)");
            ps.setInt(1,THours);
            ps.setDouble(2,TPay);
            ps.setDouble(3,RPay);
            ps.setString(4,TGrade);
            ps.setInt(5,StudentID);
            ps.setInt(6,TutorID);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }

    public static void Iupdate(int THours, double TPay, double RPay, String TGrade, int StudentID, int TutorID, int InfoID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Information SET THours = ?, TPay = ?, RPay = ?, TGrade = ?, StudentID = ?, TutorID = ? WHERE InfoID = ?");
            ps.setInt(1,THours);
            ps.setDouble(2,TPay);
            ps.setDouble(3,RPay);
            ps.setString(4,TGrade);
            ps.setInt(5,StudentID);
            ps.setInt(6,TutorID);
            ps.setInt(7,InfoID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
    public static void Idelete(int InfoID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Information WHERE InfoID = ?");
            ps.setInt(1,InfoID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage() + " Please contact help@StuTu.com for more information");
        }
    }
}
