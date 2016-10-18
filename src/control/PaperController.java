/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Paper;
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
public class PaperController {

    private Connection c;

    public void setConnection(Connection c) {
        this.c = c;
    }

    public ResultSet searchPaper(String id) {

        ResultSet rset2 = null;
        String ini = "   select * from S26575809.PAPER@FIT5148B where track_id IN ( \n"
                + "    select track_id from S26575809.TRACK@FIT5148B where conference_id IN (\n"
                + "        select conference_id from S26575809.CONFERENCE@FIT5148A where \"NAME\" = '" + id + "'))";
        try {
            Statement stmt = c.createStatement();
            // execute update SQL stetement
            rset2 = stmt.executeQuery(ini);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Wrong attributes", "Exception", JOptionPane.OK_OPTION);
        }

        return rset2;
    }

    public void insertRecord(Paper t) {
        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.PAPER (PAPER_ID, ABSTRACT"
                        + " ,PAPERTYPE, SUBMISSION_DATE,TRACK_ID,TITLE) VALUES ( SEQ_PAPER_ID.nextval, ?, ?, ?, ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);
                    java.sql.Date sDate = java.sql.Date.valueOf(t.getSubmissionDate().split(" ")[0]);
                    preparedStatement.setString(1, t.getAbs());
                    preparedStatement.setString(2, t.getPaperType());
                    preparedStatement.setDate(3, sDate);
                    preparedStatement.setInt(4, Integer.parseInt(t.getTrackId()));
                    preparedStatement.setString(5, t.getTitle());

                    // execute update SQL stetement
                    preparedStatement.executeUpdate();

                    System.out.println("Record is inserted!");
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "SQL Exception", "Wrong arguments", JOptionPane.OK_OPTION);
                    Logger.getLogger(PaperController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.IllegalArgumentException ex2) {
            JOptionPane.showConfirmDialog(null, "Wrong attributes format", "Exception", JOptionPane.OK_OPTION);
        }
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM PAPER "
                    + "WHERE PAPER_ID = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<Paper> newCs) {

        String prep = "UPDATE S26575809.PAPER SET "
                + " ABSTRACT = ?"
                + ", PAPERTYPE = ?"
                + ",SUBMISSION_DATE = ?"
                + ",TRACK_ID = ?"
                + ",TITLE = ?"
                + " WHERE PAPER_ID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);
                java.sql.Date sDate = java.sql.Date.valueOf(newCs.get(i).getSubmissionDate().split(" ")[0]);//valueOf only works for yyyy-mm-dd

                preparedStatement.setString(1, newCs.get(i).getAbs());
                preparedStatement.setString(2, newCs.get(i).getPaperType());
                preparedStatement.setDate(3, sDate);
                preparedStatement.setInt(4, Integer.parseInt(newCs.get(i).getTrackId()));
                preparedStatement.setString(5, newCs.get(i).getTitle());
                preparedStatement.setInt(6, Integer.parseInt(newCs.get(i).getPaper_id()));

                // execute update SQL stetement
                preparedStatement.executeUpdate();
                JOptionPane.showConfirmDialog(null, "Update Successd", "Info", JOptionPane.OK_OPTION);

                System.out.println("Record is updated!");
            } catch (SQLException ex) {
                Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showConfirmDialog(null, "Wrong attributes", "Exception", JOptionPane.OK_OPTION);
            } catch (java.lang.IllegalArgumentException ex2) {
                JOptionPane.showConfirmDialog(null, "Wrong attributes format", "Exception", JOptionPane.OK_OPTION);
            }
        }
    }

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            Statement stmt = c.createStatement();
            rset = stmt.executeQuery("select * from PAPER");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }
}
