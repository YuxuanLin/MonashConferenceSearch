/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Conference;
import model.beans.Track;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.MainFrame;

/**
 *
 * @author linyuxuan
 */
public class ConferenceController {

    private Connection c;

    public void setConnection(Connection c) {
        this.c = c;
    }

    public ResultSet searchConference(String city) {

        ResultSet rset2 = null;
        String ini = "Select CONFERENCE_ID,NAME,STARTING_DATE,ENDING_DATE,COUNTRY,CITY,VENUE,CONTACT_EMAIL,YEAR from CONFERENCE WHERE CITY = '";
        String prep = ini + city + "'";
        try {
            Statement stmt = c.createStatement();
            // execute update SQL stetement
            rset2 = stmt.executeQuery(prep);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showConfirmDialog(null, "Wrong attributes", "Exception", JOptionPane.OK_OPTION);
        }

        return rset2;
    }

    public void insertRecord(Conference t) {
        PreparedStatement preparedStatement = null;
        String insert = "INSERT INTO S26575809.CONFERENCE (CONFERENCE_ID, \"NAME\","
                + " STARTING_DATE, ENDING_DATE, COUNTRY, CITY, "
                + "VENUE, CONTACT_EMAIL, \"YEAR\") VALUES ( SEQ_CONFERENCE_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ? )";
        try {
            preparedStatement = c.prepareStatement(insert);
            java.sql.Date sDate = java.sql.Date.valueOf(t.getStartingDate());
            java.sql.Date eDate = java.sql.Date.valueOf(t.getEndingDate());//valueOf only works for yyyy-mm-dd HH:MM:ss

            preparedStatement.setString(1, t.getName());
            preparedStatement.setDate(2, sDate);
            preparedStatement.setDate(3, eDate);
            preparedStatement.setString(4, t.getCountry());
            preparedStatement.setString(5, t.getCity());
            preparedStatement.setString(6, t.getVenue());
            preparedStatement.setString(7, t.getContactMail());
            preparedStatement.setString(8, t.getYear());

            // execute update SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is updated!");
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "SQL Exception", "Wrong arguments", JOptionPane.OK_OPTION);
        } catch (java.lang.IllegalArgumentException ex2) {
            JOptionPane.showConfirmDialog(null, "Wrong attributes format", "Exception", JOptionPane.OK_OPTION);
        }
    }

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            Statement stmt = c.createStatement();
            rset = stmt.executeQuery("select * from Conference");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM conference "
                    + "WHERE conference_id = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<Conference> newCs) {
        for (int i = 0; i < newCs.size(); i++) {
            Validator.validateBoolean("");
        }

        String prep = "UPDATE S26575809.CONFERENCE SET "
                + "CONFERENCE_ID = ? "
                + ", \"NAME\" = ?"
                + ",STARTING_DATE = ?"
                + ",ENDING_DATE = ?"
                + ",COUNTRY = ?"
                + ",CITY = ?"
                + ",VENUE = ?"
                + ",CONTACT_EMAIL = ?"
                + ",YEAR = ?"
                + " WHERE CONFERENCE_ID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);
                java.sql.Date sDate = java.sql.Date.valueOf(newCs.get(i).getStartingDate().split(" ")[0]);
                java.sql.Date eDate = java.sql.Date.valueOf(newCs.get(i).getStartingDate().split(" ")[0]);//valueOf only works for yyyy-mm-dd

                preparedStatement.setInt(1, Integer.parseInt(newCs.get(i).getConference_ID()));
                preparedStatement.setString(2, newCs.get(i).getName());
                preparedStatement.setDate(3, sDate);
                preparedStatement.setDate(4, eDate);
                preparedStatement.setString(5, newCs.get(i).getCountry());
                preparedStatement.setString(6, newCs.get(i).getCity());
                preparedStatement.setString(7, newCs.get(i).getVenue());
                preparedStatement.setString(8, newCs.get(i).getContactMail());
                preparedStatement.setString(9, newCs.get(i).getYear().trim());
                preparedStatement.setInt(10, Integer.parseInt(newCs.get(i).getConference_ID()));

                // execute update SQL stetement
                preparedStatement.executeUpdate();
                JOptionPane.showConfirmDialog(null, "Update Successd", "Info", JOptionPane.OK_OPTION);

                System.out.println("Record is updated!");
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, "Wrong attributes", "Exception", JOptionPane.OK_OPTION);
            } catch (java.lang.IllegalArgumentException ex2) {
                JOptionPane.showConfirmDialog(null, "Wrong attributes format", "Exception", JOptionPane.OK_OPTION);
            }
        }

    }
}
