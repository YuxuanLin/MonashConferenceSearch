/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Review;
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
public class ReviewController {

    private Connection c;

    public ResultSet searchReview(String recommendation) {

        ResultSet rset2 = null;
        String ini = "Select REVIEW_ID, PAPER_ID,DUE_DATE,REVIEW_DATE,RECOMMENDATION,COMMENTS,PC_ID_A,PC_ID_B,PC_ID_C FROM REVIEW WHERE recommendation = '";
        String prep = ini + recommendation + "'";
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

    public void insertRecord(Review t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.REVIEW (REVIEW_ID, PAPER_ID"
                        + " ,DUE_DATE, REVIEW_DATE,RECOMMENDATION,COMMENTS,PC_ID_A,PC_ID_B,PC_ID_C ) VALUES ( SEQ_REVIEW_ID.nextval, ?, ?, ? ,? ,? ,? ,? ,?)";
                try {
                    preparedStatement = c.prepareStatement(insert);
                    java.sql.Date dDate = java.sql.Date.valueOf(t.getDue_date().split(" ")[0]);
                    java.sql.Date rDate = java.sql.Date.valueOf(t.getReview_date().split(" ")[0]);//valueOf only works for yyyy-mm-dd

                    preparedStatement.setString(1, t.getPaper_id());
                    preparedStatement.setDate(2, dDate);
                    preparedStatement.setDate(3, rDate);
                    preparedStatement.setString(4, t.getRecommemdation());
                    preparedStatement.setString(5, t.getComments());
                    preparedStatement.setString(6, t.getPc_id_a());
                    preparedStatement.setString(7, t.getPc_id_b());
                    preparedStatement.setString(8, t.getPc_id_c());

                    // execute update SQL stetement
                    preparedStatement.executeUpdate();

                    System.out.println("Record is inserted!");
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "SQL Exception", "Wrong arguments", JOptionPane.OK_OPTION);
                } catch (java.lang.IllegalArgumentException ex2) {
                    JOptionPane.showConfirmDialog(null, "Wrong attributes format", "Exception", JOptionPane.OK_OPTION);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRecord(int chosenID) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM REVIEW "
                    + "WHERE REVIEW_ID = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<Review> newCs) {

        String prep = "UPDATE S26575809.REVIEW SET "
                + "PAPER_ID = ?"
                + ", DUE_DATE = ?"
                + ",REVIEW_DATE = ?"
                + ",RECOMMONDATION = ?"
                + ",COMMENTS = ?"
                + ",PC_ID_A = ?"
                + ",PC_ID_B = ?"
                + ",PC_ID_C = ?"
                + " WHERE REVIEW_ID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);
                java.sql.Date dDate = java.sql.Date.valueOf(newCs.get(i).getDue_date().split(" ")[0]);
                java.sql.Date rDate = java.sql.Date.valueOf(newCs.get(i).getReview_date().split(" ")[0]);

                preparedStatement.setInt(1, Integer.parseInt(newCs.get(i).getPaper_id()));
                preparedStatement.setDate(2, dDate);
                preparedStatement.setDate(3, rDate);
                preparedStatement.setString(4, newCs.get(i).getRecommemdation());
                preparedStatement.setString(5, newCs.get(i).getComments());
                preparedStatement.setInt(6, Integer.parseInt(newCs.get(i).getPc_id_a()));
                preparedStatement.setInt(7, Integer.parseInt(newCs.get(i).getPc_id_b()));
                preparedStatement.setInt(8, Integer.parseInt(newCs.get(i).getPc_id_c()));
                preparedStatement.setInt(9, Integer.parseInt(newCs.get(i).getReview_id()));

                // execute update SQL stetement
                preparedStatement.executeUpdate();
                JOptionPane.showConfirmDialog(null, "Update Successd", "Info", JOptionPane.OK_OPTION);

                System.out.println("Record is updated!");

            } catch (SQLException ex) {
                Logger.getLogger(TrackController.class
                        .getName()).log(Level.SEVERE, null, ex);
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
            rset = stmt.executeQuery("select * from REVIEW");

        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }
}
