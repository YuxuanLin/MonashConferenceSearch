/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.beans.Author;
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
public class AuthorController {

    private Connection c;

    public ResultSet searchAuthor(String country) {

        ResultSet rset2 = null;
        String ini = "Select AUTHOR_ID,FNAME,SNAME,AFFLIATION,COUNTRY,EMAIL,CONTACT_NUMBER FROM AUTHOR WHERE COUNTRY = '";
        String prep = ini + country + "'";
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

    public ResultSet viewAll() {
        ResultSet rset = null;
        try {
            Statement stmt = c.createStatement();
            rset = stmt.executeQuery("select * from AUTHOR");
        } catch (SQLException ex) {
            Logger.getLogger(ConferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rset;
    }

    public void insertRecord(Author t) {

        try {
            if (c != null && !c.isClosed()) {
                PreparedStatement preparedStatement = null;
                String insert = "INSERT INTO S26575809.AUTHOR (AUTHOR_ID, FNAME,SNAME,AFFLIATION,COUNTRY,EMAIL,CONTACT_NUMBER) VALUES ( SEQ_AUTHOR_ID.nextval, ?, ?, ?, ?,  ?, ?)";
                try {
                    preparedStatement = c.prepareStatement(insert);

                    preparedStatement.setString(1, t.getFname());
                    preparedStatement.setString(2, t.getSname());
                    preparedStatement.setString(3, t.getAffliation());
                    preparedStatement.setString(4, t.getCountry());
                    preparedStatement.setString(5, t.getEmail());
                    preparedStatement.setString(6, t.getCountry());

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
            String sql = "DELETE FROM author "
                    + "WHERE author_id = " + chosenID;
            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TrackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(ArrayList<Author> newCs) {

        String prep = "UPDATE S26575809.AUTHOR SET "
                + " FNAME = ?"
                + ", SNAME = ?"
                + ",AFFLIATION = ?"
                + ",COUNTRY = ?"
                + ",EMAIL = ?"
                + ",CONTACT_NUMBER = ?"
                + " WHERE AUTHOR_ID = ?";
        PreparedStatement preparedStatement = null;

        for (int i = 0; i < newCs.size(); i++) {
            try {
                preparedStatement = c.prepareStatement(prep);

                preparedStatement.setString(1, newCs.get(i).getFname());
                preparedStatement.setString(2, newCs.get(i).getSname());
                preparedStatement.setString(3, newCs.get(i).getAffliation());
                preparedStatement.setString(4, newCs.get(i).getCountry());
                preparedStatement.setString(5, newCs.get(i).getEmail());
                preparedStatement.setString(6, newCs.get(i).getContact_number());
                preparedStatement.setInt(7, Integer.parseInt(newCs.get(i).getAuthor_id()));

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
