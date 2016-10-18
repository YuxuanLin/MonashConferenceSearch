/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Track;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class TrackController {

    private Connection c;

    public void setConnection(Connection c) {
        this.c = c;
    }

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            if (c != null && !c.isClosed()) {
                try {
                    Statement stmt = c.createStatement();
                    rset = stmt.executeQuery("select * from TRACK");
                } catch (SQLException ex) {
                    Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }

    public void insertRecord(Track t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.TRACK (TRACK_ID, TRACK_NAME"
                        + " ,DESCRIPTION, CONFERENCE_ID) VALUES ( SEQ_TRACK_ID.nextval, ?, ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);
                    preparedStatement.setString(1, t.getTRACK_NAME());
                    preparedStatement.setString(2, t.getDESCRIPTION());
                    preparedStatement.setInt(3, Integer.parseInt(t.getCONFERENCE_ID()));

                    // execute update SQL stetement
                    preparedStatement.executeUpdate();

                    System.out.println("Record is inserted!");
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "SQL Exception or violate a trigger", "Wrong arguments", JOptionPane.OK_OPTION);
                    Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRecord(int id) {
        try {
            if (c != null && !c.isClosed()) {
                try {
                    Statement stmt = c.createStatement();
                    String sql = "DELETE FROM TRACK "
                            + "WHERE TRACK_ID = " + id;
                    System.out.println(sql);
                    stmt.executeUpdate(sql);

                } catch (SQLException ex) {
                    Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<Track> newCs) {

        try {
            if (c != null && !c.isClosed()) {

                String prep = "UPDATE S26575809.TRACK SET "
                        + ", TRACK_NAME = ?"
                        + ", DESCRIPTION = ?"
                        + ",CONFERENCE_ID = ?"
                        + " WHERE TRACK_ID = ?";
                PreparedStatement preparedStatement = null;

                for (int i = 0; i < newCs.size(); i++) {
                    try {
                        preparedStatement = c.prepareStatement(prep);

                        preparedStatement.setString(1, newCs.get(i).getTRACK_NAME());
                        preparedStatement.setString(2, newCs.get(i).getDESCRIPTION());
                        preparedStatement.setInt(3, Integer.parseInt(newCs.get(i).getCONFERENCE_ID()));
                        preparedStatement.setInt(4, Integer.parseInt(newCs.get(i).getTRACK_ID()));

                        // execute update SQL stetement
                        preparedStatement.executeUpdate();
                        JOptionPane.showConfirmDialog(null, "Update Successd", "Info", JOptionPane.OK_OPTION);

                        System.out.println("Record is updated!");
                    } catch (SQLException ex) {
                        Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showConfirmDialog(null, "Wrong attributes", "Exception", JOptionPane.OK_OPTION);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchTrack(String city) {

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

}
