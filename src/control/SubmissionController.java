/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Submission;
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
public class SubmissionController {

    private Connection c;

    public ResultSet searchSubmission(String conference) {

        ResultSet rset2 = null;
        String ini = "select * from S26575809.SUBMISSION@FIT5148B where submissionid IN\n"
                + "  ( select paper_id from S26575809.PAPER@FIT5148B where track_id IN ( \n"
                + "    select track_id from S26575809.TRACK@FIT5148B where conference_id IN (\n"
                + "        select conference_id from S26575809.CONFERENCE@FIT5148A where \"NAME\" = '" + conference + "')))";
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

    public void setConnection(Connection c) {
        this.c = c;
    }

    public void insertRecord(Submission t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.SUBMISSION (SUBMISSIONID, PAPER_ID"
                        + " ,AUTHOR_ID, AUTHORORDER,CORRESPONDINGORNOT) VALUES ( SEQ_PMMAP_ID.nextval, ?, ?, ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);

                    preparedStatement.setInt(1, Integer.parseInt(t.getPaper_id()));
                    preparedStatement.setInt(2, Integer.parseInt(t.getAuthor_id()));
                    preparedStatement.setInt(3, Integer.parseInt(t.getAuthorOrder()));
                    preparedStatement.setString(4, t.getCorrespondingOrNot());

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

    public void update(ArrayList<Submission> newCs) {

        String prep = "UPDATE S26575809.SUBMISSION SET "
                + "PAPER_ID = ?"
                + ", AUTHOR_ID = ?"
                + ",AUTHORORDER = ?"
                + ", CORRESPONDINGORNOT = ?"
                + " WHERE SUBMISSIONID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);

                preparedStatement.setInt(1, Integer.parseInt(newCs.get(i).getPaper_id()));
                preparedStatement.setInt(2, Integer.parseInt(newCs.get(i).getAuthor_id()));
                preparedStatement.setInt(3, Integer.parseInt(newCs.get(i).getAuthorOrder()));
                preparedStatement.setString(4, newCs.get(i).getSubmissionid());

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
            rset = stmt.executeQuery("select * from SUBMISSION");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM SUBMISSION "
                    + "WHERE submissionid = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
