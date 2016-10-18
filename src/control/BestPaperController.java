/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.BestPaper;
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
public class BestPaperController {

    private Connection c;

    public ResultSet searchBp(String conferenceId) {

        ResultSet rset2 = null;
        String ini = "   select * from S26575809.BEST_PAPER_AWARD@FIT5148B where track_id IN ( \n"
                + "    select track_id from S26575809.TRACK@FIT5148B where conference_id IN (\n"
                + "        select conference_id from S26575809.CONFERENCE@FIT5148A where \"NAME\" = '" + conferenceId + "'))";
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

    public void insertRecord(BestPaper t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.BEST_PAPER_AWARD (AWARDID, TRACK_ID, PAPER_ID"
                        + " ,AWARD_PRICE, PAPER_TITLE) VALUES ( SEQ_AWARD_ID.nextval, ?, ?, ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);
                    preparedStatement.setInt(1, Integer.parseInt(t.getTrackId()));
                    preparedStatement.setInt(2, Integer.parseInt(t.getPaperID()));
                    preparedStatement.setInt(3, Integer.parseInt(t.getAwardPrice()));
                    preparedStatement.setString(4, t.getPaperTitle());

                    // execute update SQL stetement
                    preparedStatement.executeUpdate();

                    System.out.println("Record is inserted!");
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "SQL Exception", "Wrong arguments", JOptionPane.OK_OPTION);
                    Logger.getLogger(BestPaperController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            Statement stmt = c.createStatement();
            rset = stmt.executeQuery("select * from BEST_PAPER_AWARD");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM BEST_PAPER_AWARD "
                    + "WHERE awardid = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<BestPaper> newCs) {

        String prep = "UPDATE S26575809.BEST_PAPER_AWARD SET "
                + " TRACK_ID = ?"
                + ", PAPER_ID = ?"
                + ",AWARD_PRICE = ?"
                + ",PAPER_TITLE = ?"
                + " WHERE AWARDID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);

                preparedStatement.setInt(1, Integer.parseInt(newCs.get(i).getTrackId()));
                preparedStatement.setInt(2, Integer.parseInt(newCs.get(i).getPaperID()));
                preparedStatement.setInt(3, Integer.parseInt(newCs.get(i).getAwardPrice()));
                preparedStatement.setString(4, newCs.get(i).getPaperTitle());
                preparedStatement.setInt(5, Integer.parseInt(newCs.get(i).getAwardID()));

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
}
