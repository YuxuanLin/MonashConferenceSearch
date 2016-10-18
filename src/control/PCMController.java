/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.PCM;
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
public class PCMController {

    private Connection c;

    public ResultSet searchPCM(String affiliation) {

        ResultSet rset2 = null;
        String ini = "Select PC_ID,FNAME,SNAME,TITLE,POSITION,AFFILIATION,EMAIL,TRACK_ID FROM PC_MEMBER  WHERE AFFILIATION = '";
        String prep = ini + affiliation + "'";
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

    public void setConnection(Connection c) {
        this.c = c;
    }

    public void insertRecord(PCM t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.PC_MEMBER (PC_ID, FNAME"
                        + " ,SNAME,TITLE,POSITION,AFFILIATION,EMAIL,TRACK_ID) VALUES ( SEQ_PCMEMBER_ID.nextval, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);

                    preparedStatement.setString(1, t.getFname());
                    preparedStatement.setString(2, t.getSname());
                    preparedStatement.setString(3, t.getTitle());
                    preparedStatement.setString(4, t.getPosition());
                    preparedStatement.setString(5, t.getAffliation());
                    preparedStatement.setString(6, t.getEmail());
                    preparedStatement.setInt(7, Integer.parseInt(t.getTrack_id()));

                    // execute update SQL stetement
                    preparedStatement.executeUpdate();

                    System.out.println("Record is inserted!");
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "SQL Exception", "Wrong arguments", JOptionPane.OK_OPTION);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM PC_MEMBER "
                    + "WHERE PC_ID = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<PCM> newCs) {

        String prep = "UPDATE S26575809.PC_MEMBER SET "
                + " FNAME = ?"
                + ", SNAME = ?"
                + ",TITLE = ?"
                + ",POSITION = ?"
                + ",AFFILIATION = ?"
                + ",EMAIL = ?"
                + ",TRACK_ID = ?"
                + " WHERE PC_ID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);

                preparedStatement.setString(1, newCs.get(i).getFname());
                preparedStatement.setString(2, newCs.get(i).getSname());
                preparedStatement.setString(3, newCs.get(i).getTitle());
                preparedStatement.setString(4, newCs.get(i).getPosition());
                preparedStatement.setString(5, newCs.get(i).getAffliation());
                preparedStatement.setString(6, newCs.get(i).getEmail());
                preparedStatement.setInt(7, Integer.parseInt(newCs.get(i).getTrack_id()));
                preparedStatement.setInt(8, Integer.parseInt(newCs.get(i).getPc_id()));

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

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            Statement stmt = c.createStatement();
            rset = stmt.executeQuery("select * from PC_MEMBER");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }
}
