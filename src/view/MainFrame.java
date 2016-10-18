/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.beans.BestPaper;
import model.beans.Submission;
import model.beans.Paper;
import model.beans.Review;
import model.beans.Track;
import model.beans.PCM;
import model.beans.Conference;
import model.beans.Author;
import control.AuthorController;
import control.BestPaperController;
import control.ConferenceController;
import control.PCMController;
import control.PaperController;
import control.ReviewController;
import control.SubmissionController;
import control.TrackController;
import oracle.jdbc.OracleDriver;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.CellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linyuxuan
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public MainFrame() {

        this.conferenceC = new ConferenceController();
        this.bestPaperC = new BestPaperController();
        this.authorC = new AuthorController();
        this.pcmC = new PCMController();
        this.paperC = new PaperController();
        this.reviewC = new ReviewController();
        this.submissionC = new SubmissionController();
        this.trackC = new TrackController();

        initComponents();
//        disableTableControl();
        connect();
    }

    ArrayList<Integer> conferenceUpdatedData = new ArrayList<>();

    /**
     * Controller prep
     */
    private final ConferenceController conferenceC;
    private final BestPaperController bestPaperC;
    private final AuthorController authorC;
    private final PCMController pcmC;
    private final PaperController paperC;
    private final ReviewController reviewC;
    private final SubmissionController submissionC;
    private final TrackController trackC;

    /**
     * SQL Connection prep
     */
    private Connection connA;
    private Connection connB;
    private Statement stmt = null;
    private ResultSet rset = null;

    /**
     * Conference table Prep
     */
    private Object columnHeaders[] = {"CONFERENCE_ID", "CONFERENCE_NAME",
        "STARTING_DATE", "ENDING_DATE", "COUNTRY", "CITY", "VENUE", "CONTACT_EMAIL", "YEAR"};
    private Object columnHeadersForInsert[] = {"CONFERENCE_NAME",
        "STARTING_DATE", "ENDING_DATE", "COUNTRY", "CITY", "VENUE", "CONTACT_EMAIL", "YEAR"};
    private Object[] dataBackup;
    private Object[][] resultData = {};
    private Object[][] searchData = {{}};
    private DefaultTableModel dtmCONFERENCE = new DefaultTableModel(resultData,
            columnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8;
        }
    };
    private DefaultTableModel dtm_insertCONFERENCE = new DefaultTableModel(searchData,
            columnHeadersForInsert);

    /**
     * table track prep
     */
    private Object trackColumnHeaders[] = {"TRACK_ID", "TRACK_NAME",
        "DESCRIPTION", "CONFERENCE"};
    private Object trackColumnHeadersForInsert[] = {"TRACK_NAME",
        "DESCRIPTION", "CONFERENCE"};
    private Object[][] resultDataTRACK = {};
    private Object[][] searchDataTRACK = {{}};
    private DefaultTableModel dtmTRACK = new DefaultTableModel(resultDataTRACK,
            trackColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3;
        }
    };
    private DefaultTableModel dtm_insertTRACK = new DefaultTableModel(searchDataTRACK,
            trackColumnHeadersForInsert);

    /**
     * TABLE AUTHOR PREP
     */
    private Object authorColumnHeaders[] = {"AUTHOR_ID", "FNAME",
        "SNAME", "AFFLIATION", "COUNTRY", "EMAIL", "CONTACT_NUMBER"};
    private Object authorColumnHeadersForInsert[] = {"FNAME",
        "SNAME", "AFFLIATION", "COUNTRY", "EMAIL", "CONTACT_NUMBER"};
    private Object[][] resultDataAUTHOR = {};
    private Object[][] searchDataAUTHOR = {{}};
    private DefaultTableModel dtmAUTHOR = new DefaultTableModel(resultDataAUTHOR,
            authorColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6;
        }
    };
    private DefaultTableModel dtm_insertAUTHOR = new DefaultTableModel(searchDataAUTHOR,
            authorColumnHeadersForInsert);
    /**
     * PM MEMBER PREP
     */
    private Object PC_MEMBERColumnHeaders[] = {"PC_ID", "FNAME",
        "SNAME", "TITLE", "POSITION", "AFFLIATION", "EMAIL", "TRACK_ID"};
    private Object PC_MEMEBRColumnHeadersForInsert[] = {"FNAME",
        "SNAME", "TITLE", "POSITION", "AFFLIATION", "EMAIL", "TRACK_ID"};
    private Object[][] resultDataPC_MEMEBR = {};
    private Object[][] searchDataPC_MEMEBR = {{}};
    private DefaultTableModel dtmPC_MEMEBR = new DefaultTableModel(resultDataPC_MEMEBR,
            PC_MEMBERColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7;
        }
    };
    private DefaultTableModel dtm_searchFieldPC_MEMEBR = new DefaultTableModel(searchDataPC_MEMEBR, PC_MEMEBRColumnHeadersForInsert);
    /**
     * REVIEW PREP
     */
    private Object REVIEWColumnHeaders[] = {"REVIEW_ID", "PAPER_ID", "DUE_DATE", "REVIEW_DATE", "RECOMMENDATION", "COMMEMTS", "PC_ID_A", "PC_ID_B", "PC_ID_C"};
    private Object REVIEWColumnHeadersForInsert[] = {"PAPER_ID", "DUE_DATE", "REVIEW_DATE", "RECOMMENDATION", "COMMEMTS", "PC_ID_A", "PC_ID_B", "PC_ID_C"};
    private Object[][] resultDataREVIEW = {};
    private Object[][] searchDataREVIEW = {{}};
    private DefaultTableModel dtmREVIEW = new DefaultTableModel(resultDataREVIEW,
            REVIEWColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8;
        }
    };
    private DefaultTableModel dtm_searchFieldREVIEW = new DefaultTableModel(searchDataREVIEW,
            REVIEWColumnHeadersForInsert);
    /**
     * SUBMISSION PREP
     */
    private Object SUBMISSIONColumnHeaders[] = {"SUBMISSIONID", "PAPER_ID", "AUTHOR_ID", "AUTHORORDER", "CORRESPONDINGORNOT"};
    private Object SUBMISSIONColumnHeadersForInsert[] = {"PAPER_ID", "AUTHOR_ID", "AUTHORORDER", "CORRESPONDINGORNOT"};
    private Object[][] resultDataSUBMISSION = {};
    private Object[][] searchDataSUBMISSION = {{}};
    private DefaultTableModel dtmSUBMISSION = new DefaultTableModel(resultDataSUBMISSION,
            SUBMISSIONColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4;
        }
    };
    private DefaultTableModel dtm_searchFieldSUBMISSION = new DefaultTableModel(searchDataSUBMISSION,
            SUBMISSIONColumnHeadersForInsert);
    /**
     * PAPER PREP
     */
    private Object PAPERColumnHeaders[] = {"PAPER_ID", "ABSTRACT", "PAPERTYPE", "SUBMISSION_DATE", "TRACK_ID", "TITLE"};
    private Object PAPERColumnHeadersForInsert[] = {"ABSTRACT", "PAPERTYPE", "SUBMISSION_DATE", "TRACK_ID", "TITLE"};
    private Object[][] resultDataPAPER = {};
    private Object[][] searchDataPAPER = {{}};
    private DefaultTableModel dtmPAPER = new DefaultTableModel(resultDataPAPER,
            PAPERColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4 || column == 5;
        }
    };
    private DefaultTableModel dtm_searchFieldPAPER = new DefaultTableModel(searchDataPAPER,
            PAPERColumnHeadersForInsert);

    /**
     * BEST PAPER PREP
     */
    private Object BESTPAPERColumnHeaders[] = {"AWARD_ID", "TRACK_ID", "PAPER_ID", "AWARD_PRICE", "TITLE"};
    private Object BESTPAPERColumnHeadersForInsert[] = {"TRACK_ID", "PAPER_ID", "AWARD_PRICE", "TITLE"};
    private Object[][] resultDataBESTPAPER = {};
    private Object[][] searchDataBESTPAPER = {{}};
    private DefaultTableModel dtmBESTPAPER = new DefaultTableModel(resultDataBESTPAPER,
            BESTPAPERColumnHeaders) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 2 || column == 3 || column == 4;
        }
    };
    private DefaultTableModel dtm_searchFieldBESTPAPER = new DefaultTableModel(searchDataBESTPAPER, BESTPAPERColumnHeadersForInsert);

    private void disableTableControl() {

        jTabbedPane_allTables.setEnabled(false);
        jButton_disconnect.setEnabled(false);
    }

    private void ebleTableControl() {
//        jButton_Author.setEnabled(true);
//        jButton_BestPaperAward.setEnabled(true);
//        jButton_Conference.setEnabled(true);
//        jButton_PC.setEnabled(true);
//        jButton_Paper.setEnabled(true);
//        jButton_Review.setEnabled(true);
//        jButton_Submission.setEnabled(true);
//        jButton_Track.setEnabled(true);
        jTabbedPane_allTables.setEnabled(true);

        jButton_disconnect.setEnabled(true);
    }

//    private Connection connA = null;
//    private Connection connB = null;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton_connect = new javax.swing.JButton();
        jButton_disconnect = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel_connectionState = new javax.swing.JLabel();
        jTabbedPane_allTables = new javax.swing.JTabbedPane();
        jPanel_Conference = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        conferenceTable = new javax.swing.JTable();
        jButton_viewConference = new javax.swing.JButton();
        jButton_deleteConference = new javax.swing.JButton();
        jButton_updateConference = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        insertConferenceTable = new javax.swing.JTable();
        jButton_insertConference = new javax.swing.JButton();
        jButton_clearConference = new javax.swing.JButton();
        jButton_searchConference = new javax.swing.JButton();
        jPanel_Track = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        trackTable = new javax.swing.JTable();
        jButton_viewTRACK = new javax.swing.JButton();
        jButton_deleteTrack = new javax.swing.JButton();
        jButton_updateTrack = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        insertTrackTable = new javax.swing.JTable();
        jButton_insertTrack = new javax.swing.JButton();
        jButton_clearTrack = new javax.swing.JButton();
        jButton_searchTrack = new javax.swing.JButton();
        jPanel_Author = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        authorTable = new javax.swing.JTable();
        jButton_viewAuthor = new javax.swing.JButton();
        jButton_deleteAuthor = new javax.swing.JButton();
        jButton_updateAuthor = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        insertAuthorTable = new javax.swing.JTable();
        jButton_insertAuthor = new javax.swing.JButton();
        jButton_clearAuthor = new javax.swing.JButton();
        jButton_searchAuthor = new javax.swing.JButton();
        jPanel_PCMember = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        pcMemberTable = new javax.swing.JTable();
        jButton_viewPCM = new javax.swing.JButton();
        jButton_deletePCM = new javax.swing.JButton();
        jButton_updatePCM = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        insertPCMemberTable = new javax.swing.JTable();
        jButton_insertPCM = new javax.swing.JButton();
        jButton_clearPCM = new javax.swing.JButton();
        jButton_searchPCM = new javax.swing.JButton();
        jPanel_Review = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        reviewTable = new javax.swing.JTable();
        jButton_viewReview = new javax.swing.JButton();
        jButton_deleteReview = new javax.swing.JButton();
        jButton_updateReview = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        insertReviewTable = new javax.swing.JTable();
        jButton_insertReview = new javax.swing.JButton();
        jButton_clearReview = new javax.swing.JButton();
        jButton_searchReview = new javax.swing.JButton();
        jPanel_Submission = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        submissionTable = new javax.swing.JTable();
        jButton_viewSubmission = new javax.swing.JButton();
        jButton_deleteSubmission = new javax.swing.JButton();
        jButton_updateSubmission = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        insertSubmissionTable = new javax.swing.JTable();
        jButton_insertSubmission = new javax.swing.JButton();
        jButton_clearSubmission = new javax.swing.JButton();
        jButton_searchSubmission = new javax.swing.JButton();
        jTextField_searchSubByConf = new javax.swing.JTextField();
        jPanel_Paper = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        paperTable = new javax.swing.JTable();
        jButton_viewPaper = new javax.swing.JButton();
        jButton_deletePaper = new javax.swing.JButton();
        jButton_updatePaper = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        insertPaperTable = new javax.swing.JTable();
        jButton_insertPaper = new javax.swing.JButton();
        jButton_clearPaper = new javax.swing.JButton();
        jButton_searchPaper = new javax.swing.JButton();
        jTextField_searchPaperByConf = new javax.swing.JTextField();
        jPanel_BestPaper = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        bestPaperTable = new javax.swing.JTable();
        jButton_viewBestPaper = new javax.swing.JButton();
        jButton_deleteBestPaper = new javax.swing.JButton();
        jButton_updateBestPaper = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        insertBestPaperTable = new javax.swing.JTable();
        jButton_insertBestPaper = new javax.swing.JButton();
        jButton_clearBp = new javax.swing.JButton();
        jButton_searchBestPaper = new javax.swing.JButton();
        jTextField_searchBPByConf = new javax.swing.JTextField();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDataConnection(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Monash Conference Query System");

        jButton_connect.setText("Connect");
        jButton_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectActionPerformed(evt);
            }
        });

        jButton_disconnect.setText("DIsconnect");
        jButton_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_disconnectActionPerformed(evt);
            }
        });

        jLabel2.setText("Connection state:");

        jLabel_connectionState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_connectionState.setText("Disconnected");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        conferenceTable.setModel(dtmCONFERENCE);
        jScrollPane1.setViewportView(conferenceTable);

        jButton_viewConference.setText("VIEW");
        jButton_viewConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewConferenceActionPerformed(evt);
            }
        });

        jButton_deleteConference.setText("DELETE");
        jButton_deleteConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteConferenceActionPerformed(evt);
            }
        });

        jButton_updateConference.setText("UPDATE");
        jButton_updateConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateConferenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton_viewConference, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteConference)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateConference)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewConference)
                    .addComponent(jButton_deleteConference)
                    .addComponent(jButton_updateConference))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertConferenceTable.setModel(dtm_insertCONFERENCE);
        jScrollPane2.setViewportView(insertConferenceTable);

        jButton_insertConference.setText("INSERT");
        jButton_insertConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertConferenceActionPerformed(evt);
            }
        });

        jButton_clearConference.setText("Clear");
        jButton_clearConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearConferenceActionPerformed(evt);
            }
        });

        jButton_searchConference.setText("Search");
        jButton_searchConference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchConferenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton_insertConference)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_searchConference)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_clearConference, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertConference)
                    .addComponent(jButton_clearConference)
                    .addComponent(jButton_searchConference)))
        );

        javax.swing.GroupLayout jPanel_ConferenceLayout = new javax.swing.GroupLayout(jPanel_Conference);
        jPanel_Conference.setLayout(jPanel_ConferenceLayout);
        jPanel_ConferenceLayout.setHorizontalGroup(
            jPanel_ConferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ConferenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ConferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_ConferenceLayout.setVerticalGroup(
            jPanel_ConferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ConferenceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Conference", jPanel_Conference);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        trackTable.setModel(dtmTRACK);
        jScrollPane5.setViewportView(trackTable);
        trackTable.getAccessibleContext().setAccessibleName("");

        jButton_viewTRACK.setText("VIEW");
        jButton_viewTRACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewTRACKActionPerformed(evt);
            }
        });

        jButton_deleteTrack.setText("DELETE");
        jButton_deleteTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteTrackActionPerformed(evt);
            }
        });

        jButton_updateTrack.setText("UPDATE");
        jButton_updateTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateTrackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton_viewTRACK, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteTrack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateTrack)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewTRACK)
                    .addComponent(jButton_deleteTrack)
                    .addComponent(jButton_updateTrack))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertTrackTable.setModel(dtm_insertTRACK);
        jScrollPane6.setViewportView(insertTrackTable);

        jButton_insertTrack.setText("INSERT");
        jButton_insertTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertTrackActionPerformed(evt);
            }
        });

        jButton_clearTrack.setText("Clear");
        jButton_clearTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearTrackActionPerformed(evt);
            }
        });

        jButton_searchTrack.setText("Search");
        jButton_searchTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchTrackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton_insertTrack)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_searchTrack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_clearTrack, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertTrack)
                    .addComponent(jButton_clearTrack)
                    .addComponent(jButton_searchTrack)))
        );

        javax.swing.GroupLayout jPanel_TrackLayout = new javax.swing.GroupLayout(jPanel_Track);
        jPanel_Track.setLayout(jPanel_TrackLayout);
        jPanel_TrackLayout.setHorizontalGroup(
            jPanel_TrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TrackLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_TrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_TrackLayout.setVerticalGroup(
            jPanel_TrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TrackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Track", jPanel_Track);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        authorTable.setModel(dtmAUTHOR);
        jScrollPane7.setViewportView(authorTable);

        jButton_viewAuthor.setText("VIEW");
        jButton_viewAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewAuthorActionPerformed(evt);
            }
        });

        jButton_deleteAuthor.setText("DELETE");
        jButton_deleteAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteAuthorActionPerformed(evt);
            }
        });

        jButton_updateAuthor.setText("UPDATE");
        jButton_updateAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateAuthorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton_viewAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteAuthor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateAuthor)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewAuthor)
                    .addComponent(jButton_deleteAuthor)
                    .addComponent(jButton_updateAuthor))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertAuthorTable.setModel(dtm_insertAUTHOR);
        jScrollPane8.setViewportView(insertAuthorTable);

        jButton_insertAuthor.setText("INSERT");
        jButton_insertAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertAuthorActionPerformed(evt);
            }
        });

        jButton_clearAuthor.setText("Clear");
        jButton_clearAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearAuthorActionPerformed(evt);
            }
        });

        jButton_searchAuthor.setText("Search");
        jButton_searchAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchAuthorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton_insertAuthor)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_searchAuthor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_clearAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertAuthor)
                    .addComponent(jButton_clearAuthor)
                    .addComponent(jButton_searchAuthor)))
        );

        javax.swing.GroupLayout jPanel_AuthorLayout = new javax.swing.GroupLayout(jPanel_Author);
        jPanel_Author.setLayout(jPanel_AuthorLayout);
        jPanel_AuthorLayout.setHorizontalGroup(
            jPanel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AuthorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_AuthorLayout.setVerticalGroup(
            jPanel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AuthorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Author", jPanel_Author);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        pcMemberTable.setModel(dtmPC_MEMEBR);
        jScrollPane9.setViewportView(pcMemberTable);

        jButton_viewPCM.setText("VIEW");
        jButton_viewPCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewPCMActionPerformed(evt);
            }
        });

        jButton_deletePCM.setText("DELETE");
        jButton_deletePCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deletePCMActionPerformed(evt);
            }
        });

        jButton_updatePCM.setText("UPDATE");
        jButton_updatePCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updatePCMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton_viewPCM, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deletePCM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updatePCM)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewPCM)
                    .addComponent(jButton_deletePCM)
                    .addComponent(jButton_updatePCM))
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertPCMemberTable.setModel(dtm_searchFieldPC_MEMEBR);
        jScrollPane10.setViewportView(insertPCMemberTable);

        jButton_insertPCM.setText("INSERT");
        jButton_insertPCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertPCMActionPerformed(evt);
            }
        });

        jButton_clearPCM.setText("Clear");
        jButton_clearPCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearPCMActionPerformed(evt);
            }
        });

        jButton_searchPCM.setText("Search");
        jButton_searchPCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchPCMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane10)
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jButton_insertPCM)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_searchPCM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_clearPCM, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertPCM)
                    .addComponent(jButton_clearPCM)
                    .addComponent(jButton_searchPCM)))
        );

        javax.swing.GroupLayout jPanel_PCMemberLayout = new javax.swing.GroupLayout(jPanel_PCMember);
        jPanel_PCMember.setLayout(jPanel_PCMemberLayout);
        jPanel_PCMemberLayout.setHorizontalGroup(
            jPanel_PCMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PCMemberLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_PCMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_PCMemberLayout.setVerticalGroup(
            jPanel_PCMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PCMemberLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("PC Member", jPanel_PCMember);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        reviewTable.setModel(dtmREVIEW);
        jScrollPane11.setViewportView(reviewTable);

        jButton_viewReview.setText("VIEW");
        jButton_viewReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewReviewActionPerformed(evt);
            }
        });

        jButton_deleteReview.setText("DELETE");
        jButton_deleteReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteReviewActionPerformed(evt);
            }
        });

        jButton_updateReview.setText("UPDATE");
        jButton_updateReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateReviewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jButton_viewReview, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteReview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateReview)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewReview)
                    .addComponent(jButton_deleteReview)
                    .addComponent(jButton_updateReview))
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertReviewTable.setModel(dtm_searchFieldREVIEW);
        jScrollPane12.setViewportView(insertReviewTable);

        jButton_insertReview.setText("INSERT");
        jButton_insertReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertReviewActionPerformed(evt);
            }
        });

        jButton_clearReview.setText("Clear");
        jButton_clearReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearReviewActionPerformed(evt);
            }
        });

        jButton_searchReview.setText("Search");
        jButton_searchReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchReviewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane12)
                        .addContainerGap())
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButton_insertReview)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_searchReview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_clearReview, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertReview)
                    .addComponent(jButton_clearReview)
                    .addComponent(jButton_searchReview)))
        );

        javax.swing.GroupLayout jPanel_ReviewLayout = new javax.swing.GroupLayout(jPanel_Review);
        jPanel_Review.setLayout(jPanel_ReviewLayout);
        jPanel_ReviewLayout.setHorizontalGroup(
            jPanel_ReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ReviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_ReviewLayout.setVerticalGroup(
            jPanel_ReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ReviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Review", jPanel_Review);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        submissionTable.setModel(dtmSUBMISSION);
        jScrollPane13.setViewportView(submissionTable);

        jButton_viewSubmission.setText("VIEW");
        jButton_viewSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewSubmissionActionPerformed(evt);
            }
        });

        jButton_deleteSubmission.setText("DELETE");
        jButton_deleteSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteSubmissionActionPerformed(evt);
            }
        });

        jButton_updateSubmission.setText("UPDATE");
        jButton_updateSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateSubmissionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jButton_viewSubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteSubmission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateSubmission)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewSubmission)
                    .addComponent(jButton_deleteSubmission)
                    .addComponent(jButton_updateSubmission))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertSubmissionTable.setModel(dtm_searchFieldSUBMISSION);
        jScrollPane14.setViewportView(insertSubmissionTable);

        jButton_insertSubmission.setText("INSERT");
        jButton_insertSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertSubmissionActionPerformed(evt);
            }
        });

        jButton_clearSubmission.setText("Clear");
        jButton_clearSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearSubmissionActionPerformed(evt);
            }
        });

        jButton_searchSubmission.setText("SEARCH BY CONFERENCE:");
        jButton_searchSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchSubmissionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jButton_insertSubmission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_clearSubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_searchSubmission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_searchSubByConf, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertSubmission)
                    .addComponent(jButton_clearSubmission)
                    .addComponent(jButton_searchSubmission)
                    .addComponent(jTextField_searchSubByConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel_SubmissionLayout = new javax.swing.GroupLayout(jPanel_Submission);
        jPanel_Submission.setLayout(jPanel_SubmissionLayout);
        jPanel_SubmissionLayout.setHorizontalGroup(
            jPanel_SubmissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SubmissionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_SubmissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_SubmissionLayout.setVerticalGroup(
            jPanel_SubmissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SubmissionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Submission", jPanel_Submission);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        paperTable.setModel(dtmPAPER);
        jScrollPane15.setViewportView(paperTable);

        jButton_viewPaper.setText("VIEW");
        jButton_viewPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewPaperActionPerformed(evt);
            }
        });

        jButton_deletePaper.setText("DELETE");
        jButton_deletePaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deletePaperActionPerformed(evt);
            }
        });

        jButton_updatePaper.setText("UPDATE");
        jButton_updatePaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updatePaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jButton_viewPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deletePaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updatePaper)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewPaper)
                    .addComponent(jButton_deletePaper)
                    .addComponent(jButton_updatePaper))
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertPaperTable.setModel(dtm_searchFieldPAPER);
        jScrollPane16.setViewportView(insertPaperTable);

        jButton_insertPaper.setText("INSERT");
        jButton_insertPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertPaperActionPerformed(evt);
            }
        });

        jButton_clearPaper.setText("Clear");
        jButton_clearPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearPaperActionPerformed(evt);
            }
        });

        jButton_searchPaper.setText("SEARCH BY CONFERENCE:");
        jButton_searchPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchPaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jButton_insertPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_clearPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_searchPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_searchPaperByConf, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertPaper)
                    .addComponent(jButton_clearPaper)
                    .addComponent(jTextField_searchPaperByConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_searchPaper)))
        );

        javax.swing.GroupLayout jPanel_PaperLayout = new javax.swing.GroupLayout(jPanel_Paper);
        jPanel_Paper.setLayout(jPanel_PaperLayout);
        jPanel_PaperLayout.setHorizontalGroup(
            jPanel_PaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PaperLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_PaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_PaperLayout.setVerticalGroup(
            jPanel_PaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PaperLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Paper", jPanel_Paper);

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Result"));

        bestPaperTable.setModel(dtmBESTPAPER);
        jScrollPane17.setViewportView(bestPaperTable);

        jButton_viewBestPaper.setText("VIEW");
        jButton_viewBestPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewBestPaperActionPerformed(evt);
            }
        });

        jButton_deleteBestPaper.setText("DELETE");
        jButton_deleteBestPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteBestPaperActionPerformed(evt);
            }
        });

        jButton_updateBestPaper.setText("UPDATE");
        jButton_updateBestPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateBestPaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jButton_viewBestPaper, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_deleteBestPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_updateBestPaper)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_viewBestPaper)
                    .addComponent(jButton_deleteBestPaper)
                    .addComponent(jButton_updateBestPaper))
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert & Search"));

        insertBestPaperTable.setModel(dtm_searchFieldBESTPAPER);
        jScrollPane18.setViewportView(insertBestPaperTable);

        jButton_insertBestPaper.setText("INSERT");
        jButton_insertBestPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertBestPaperActionPerformed(evt);
            }
        });

        jButton_clearBp.setText("Clear");
        jButton_clearBp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearBpActionPerformed(evt);
            }
        });

        jButton_searchBestPaper.setText("SEARCH BY CONFERENCE:");
        jButton_searchBestPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchBestPaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jButton_insertBestPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_clearBp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_searchBestPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_searchBPByConf, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertBestPaper)
                    .addComponent(jButton_clearBp)
                    .addComponent(jTextField_searchBPByConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_searchBestPaper)))
        );

        javax.swing.GroupLayout jPanel_BestPaperLayout = new javax.swing.GroupLayout(jPanel_BestPaper);
        jPanel_BestPaper.setLayout(jPanel_BestPaperLayout);
        jPanel_BestPaperLayout.setHorizontalGroup(
            jPanel_BestPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BestPaperLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_BestPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_BestPaperLayout.setVerticalGroup(
            jPanel_BestPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BestPaperLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_allTables.addTab("Best Paper", jPanel_BestPaper);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane_allTables)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_connectionState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_connect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_disconnect, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_connect)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel_connectionState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton_disconnect)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane_allTables, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_allTables.getAccessibleContext().setAccessibleName("tab");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connect() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            connA = DriverManager.getConnection(
                    "jdbc:oracle:thin:@hippo.its.monash.edu.au:1521:FIT5148a",
                    "S26575809", "student");
            System.out.println("Connected to FIT5043a");
            connB = DriverManager.getConnection(
                    "jdbc:oracle:thin:@hippo.its.monash.edu.au:1521:FIT5148b",
                    "S26575809", "student");
            System.out.println("Connected to FIT5043b");
            printInLable();
            ebleTableControl();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
        }

    }

    private void jButton_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectActionPerformed
        connect();
        printInLable();
    }//GEN-LAST:event_jButton_connectActionPerformed

    private void jButton_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_disconnectActionPerformed
        try {
            connA.close();
            System.out.println("ConnectionA closed");
            connB.close();
            System.out.println("ConnectionB closed");
            disableTableControl();
        } catch (SQLException ex) {
            System.out.println("error in closing connection");
        }
        printInLable();
    }//GEN-LAST:event_jButton_disconnectActionPerformed

    private void printInLable() {
        try {
            if (!connA.isClosed() && !connB.isClosed()) {
                jLabel_connectionState.setText("<html>Connected to database FIT5148A and FIT5148B</html>");
            } else if (!connA.isClosed() && connB.isClosed()) {
                jLabel_connectionState.setText("<html>Connected to database FIT5148A and disconnect to FIT5148B</html>");
            } else if (connA.isClosed() && !connB.isClosed()) {
                jLabel_connectionState.setText("<html>Disconnect to database FIT5148A and connected to FIT5148B</html>");
            } else {
                jLabel_connectionState.setText("<html>Disconnect to database FIT5148A and disconnect to FIT5148B</html>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void closeDataConnection(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDataConnection
        try {
            if (connA != null && !connA.isClosed()) {
                connA.close();
                System.out.println("Connection A closed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (connB != null && !connB.isClosed()) {
                connB.close();
                System.out.println("Connection B closed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.exit(0);
        }
    }//GEN-LAST:event_closeDataConnection

    private void stopEdit(JTable table) {
        CellEditor cellEditor = table.getCellEditor();
        if (cellEditor != null) {
            cellEditor.stopCellEditing();
        }
    }

    private void jButton_viewConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewConferenceActionPerformed
        stopEdit(conferenceTable);
        viewConference();
    }//GEN-LAST:event_jButton_viewConferenceActionPerformed

    private void viewConference() {
        conferenceC.setConnection(connA);
        rset = conferenceC.viewAll();
        populateTable(dtmCONFERENCE);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populate table with attribute Rset
     *
     * @param dtm
     */
    private void populateTable(DefaultTableModel dtm) {
        try {
            if (rset != null) {
                if (!rset.isClosed()) {
                    ResultSetMetaData mdata = rset.getMetaData();
                    int numberOfColumns = mdata.getColumnCount();

                    //Remove rows one by one from the end of the table
                    int rowCount = dtm.getRowCount();
                    for (int i = rowCount - 1; i >= 0; i--) {
                        dtm.removeRow(i);
                    }

                    //repopulate table model
                    while (rset.next()) {
                        Object[] rowData = new Object[numberOfColumns];
                        for (int i = 0; i < rowData.length; i++) {
                            rowData[i] = rset.getObject(i + 1);
                        }
                        dtm.addRow(rowData);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void getAllData() {
//        int rowCount = dtmCONFERENCE.getRowCount();
//        int colCount = dtmCONFERENCE.getColumnCount();
//
//        Object[] dataBackup = new Object[rowCount];
//        for (int row = 0; row < rowCount; row++) {
//            String[] rowUnit = new String[colCount];
//            for (int col = 0; col < colCount; col++) {
//                rowUnit[col] = dtmCONFERENCE.getValueAt(row, col).toString();
//            }
//            dataBackup[row] = rowUnit;
//        }
//        this.dataBackup = dataBackup;
//
//    }

    private void jButton_deleteConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteConferenceActionPerformed
        stopEdit(conferenceTable);
        int selectedRow = conferenceTable.getSelectedRow();
        int chosenID = Integer.parseInt(conferenceTable.getValueAt(selectedRow, 0).toString());

        int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (response == JOptionPane.YES_OPTION) {
            if (selectedRow == -1) {
                JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
            } else {
                conferenceC.setConnection(connA);
                conferenceC.deleteRecord(chosenID);
                viewConference();
            }
        }

    }//GEN-LAST:event_jButton_deleteConferenceActionPerformed

    private void jButton_updateConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateConferenceActionPerformed
        stopEdit(conferenceTable);
        int[] rows = conferenceTable.getSelectedRows();
        ArrayList<Conference> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Conference(
                    conferenceTable.getValueAt(rows[i], 0).toString(),
                    conferenceTable.getValueAt(rows[i], 1).toString(),
                    conferenceTable.getValueAt(rows[i], 2).toString(),
                    conferenceTable.getValueAt(rows[i], 3).toString(),
                    conferenceTable.getValueAt(rows[i], 4).toString(),
                    conferenceTable.getValueAt(rows[i], 5).toString(),
                    conferenceTable.getValueAt(rows[i], 6).toString(),
                    conferenceTable.getValueAt(rows[i], 7).toString(),
                    conferenceTable.getValueAt(rows[i], 8).toString()
            ));
        }

        conferenceC.update(data);
    }//GEN-LAST:event_jButton_updateConferenceActionPerformed

//    private ArrayList<Conference> getEditedRows() {
//        Object[] before = this.dataBackup;
//        getAllData();//update dataBackup
//
//        int rowCount = dtmCONFERENCE.getRowCount();
//        int colCount = dtmCONFERENCE.getColumnCount();
//        boolean isModified = false;
//        ArrayList<Conference> newCs = new ArrayList<>();
//        for (int i = 0; i < rowCount; i++) {
//            String[] newRow = (String[]) dataBackup[i];
//            String[] oldRow = (String[]) before[i];
//            Conference c = new Conference();
//
//            for (int m = 0; m < colCount; m++) {//compare starting with value next of ID
//                if (!newRow[m].equals(oldRow[m])) {//find the changed value
//
////                    mapAttr(m, newRow[m], c);
//                    System.out.println(newRow[0]);
//                    isModified = true;
//                    break;
//                }
//            }
//            if (isModified) {
//                for (int col = 0; col < colCount; col++) {
//                    mapAttr(col, newRow[col], c);
//                }
//                newCs.add(c);
//                isModified = false;
//            }
//        }
//        System.out.println(newCs.size());
//        return newCs;
//    }
    private void mapAttr(int index, String content, Conference c) {
        switch (index) {
            case 0:
                c.setConference_ID(content);
                break;
            case 1:
                c.setName(content);
                break;
            case 2:
                c.setStartingDate(content);
                break;
            case 3:
                c.setEndingDate(content);
                break;
            case 4:
                c.setCountry(content);
                break;
            case 5:
                c.setCity(content);
                break;
            case 6:
                c.setVenue(content);
                break;
            case 7:
                c.setContactMail(content);
                break;
            case 8:
                c.setYear(content);
                break;
        }
    }


    private void jButton_insertConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertConferenceActionPerformed
        stopEdit(insertConferenceTable);
        Conference t = new Conference(
                insertConferenceTable.getValueAt(0, 0).toString(),
                insertConferenceTable.getValueAt(0, 1).toString(),
                insertConferenceTable.getValueAt(0, 2).toString(),
                insertConferenceTable.getValueAt(0, 3).toString(),
                insertConferenceTable.getValueAt(0, 4).toString(),
                insertConferenceTable.getValueAt(0, 5).toString(),
                insertConferenceTable.getValueAt(0, 6).toString(),
                insertConferenceTable.getValueAt(0, 7).toString()
        );
        conferenceC.setConnection(connA);
        conferenceC.insertRecord(t);
        viewConference();
    }//GEN-LAST:event_jButton_insertConferenceActionPerformed

    private void jButton_clearConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearConferenceActionPerformed
        stopEdit(insertConferenceTable);
        clearTable(dtm_insertCONFERENCE);
    }//GEN-LAST:event_jButton_clearConferenceActionPerformed

    private void clearTable(DefaultTableModel dtm) {
        int rowCount = dtm.getRowCount();
        int colCount = dtm.getColumnCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            for (int col = 0; col < colCount; col++) {

                dtm.setValueAt(null, i, col);
            }
        }
    }
    private void jButton_searchConferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchConferenceActionPerformed
        stopEdit(insertConferenceTable);
        String city = insertConferenceTable.getValueAt(0, 4).toString();
        conferenceC.setConnection(connA);
        rset = conferenceC.searchConference(city);
        populateTable(dtmCONFERENCE);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchConferenceActionPerformed


    private void jButton_viewTRACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewTRACKActionPerformed
        stopEdit(trackTable);
        viewTrack();
//        getAllData();

    }//GEN-LAST:event_jButton_viewTRACKActionPerformed

    private void viewTrack() {
        trackC.setConnection(connB);
        rset = trackC.viewAll();
        populateTable(dtmTRACK);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton_deleteTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteTrackActionPerformed
        stopEdit(trackTable);
        int selectedRow = trackTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(trackTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                trackC.setConnection(connB);
                trackC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }

    }//GEN-LAST:event_jButton_deleteTrackActionPerformed

    private void jButton_updateTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateTrackActionPerformed
        stopEdit(trackTable);
        int[] rows = trackTable.getSelectedRows();
        ArrayList<Track> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Track(
                    trackTable.getValueAt(rows[i], 0).toString(),
                    trackTable.getValueAt(rows[i], 1).toString(),
                    trackTable.getValueAt(rows[i], 2).toString(),
                    trackTable.getValueAt(rows[i], 3).toString()
            ));
        }

        trackC.update(data);
    }//GEN-LAST:event_jButton_updateTrackActionPerformed

    private void jButton_insertTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertTrackActionPerformed
        stopEdit(insertTrackTable);
        Track t = new Track(insertTrackTable.getValueAt(0, 0).toString(),
                insertTrackTable.getValueAt(0, 1).toString(),
                insertTrackTable.getValueAt(0, 2).toString()
        );
        trackC.setConnection(connB);
        trackC.insertRecord(t);
        viewTrack();
    }//GEN-LAST:event_jButton_insertTrackActionPerformed

    private void jButton_clearTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearTrackActionPerformed
        stopEdit(insertTrackTable);
        clearTable(dtm_insertTRACK);
    }//GEN-LAST:event_jButton_clearTrackActionPerformed

    private void jButton_searchTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchTrackActionPerformed
        stopEdit(insertTrackTable);
        String conference = insertTrackTable.getValueAt(0, 2).toString();
        trackC.setConnection(connB);
        rset = trackC.searchTrack(conference);
        populateTable(dtmTRACK);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchTrackActionPerformed

    private void jButton_viewAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewAuthorActionPerformed
        stopEdit(authorTable);
        viewAuthor();
    }//GEN-LAST:event_jButton_viewAuthorActionPerformed

    private void viewAuthor() {
        authorC.setConnection(connB);
        rset = authorC.viewAll();
        populateTable(dtmAUTHOR);
//        getAllData();
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deleteAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteAuthorActionPerformed
        stopEdit(authorTable);
        int selectedRow = authorTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(authorTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                authorC.setConnection(connB);
                authorC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deleteAuthorActionPerformed

    private void jButton_updateAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateAuthorActionPerformed
        stopEdit(authorTable);
        int[] rows = authorTable.getSelectedRows();
        ArrayList<Author> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Author(
                    authorTable.getValueAt(rows[i], 0).toString(),
                    authorTable.getValueAt(rows[i], 1).toString(),
                    authorTable.getValueAt(rows[i], 2).toString(),
                    authorTable.getValueAt(rows[i], 3).toString(),
                    authorTable.getValueAt(rows[i], 4).toString(),
                    authorTable.getValueAt(rows[i], 5).toString(),
                    authorTable.getValueAt(rows[i], 6).toString()
            ));
        }

        authorC.update(data);
    }//GEN-LAST:event_jButton_updateAuthorActionPerformed

    private void jButton_insertAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertAuthorActionPerformed
        stopEdit(insertAuthorTable);
        Author t = new Author(insertAuthorTable.getValueAt(0, 0).toString(),
                insertAuthorTable.getValueAt(0, 1).toString(),
                insertAuthorTable.getValueAt(0, 2).toString(),
                insertAuthorTable.getValueAt(0, 3).toString(),
                insertAuthorTable.getValueAt(0, 4).toString(),
                insertAuthorTable.getValueAt(0, 5).toString());

        authorC.insertRecord(t);
    }//GEN-LAST:event_jButton_insertAuthorActionPerformed

    private void jButton_clearAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearAuthorActionPerformed
        stopEdit(insertAuthorTable);
        clearTable(dtm_insertAUTHOR);
    }//GEN-LAST:event_jButton_clearAuthorActionPerformed

    private void jButton_searchAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchAuthorActionPerformed
        stopEdit(insertAuthorTable);
        String country = insertAuthorTable.getValueAt(0, 3).toString();
        trackC.setConnection(connB);
        rset = authorC.searchAuthor(country);
        populateTable(dtmAUTHOR);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchAuthorActionPerformed

    private void jButton_viewPCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewPCMActionPerformed
        stopEdit(pcMemberTable);
        viewPCM();
    }//GEN-LAST:event_jButton_viewPCMActionPerformed

    private void viewPCM() {
        pcmC.setConnection(connB);
        rset = pcmC.viewAll();
        populateTable(dtmPC_MEMEBR);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deletePCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deletePCMActionPerformed
        stopEdit(pcMemberTable);
        int selectedRow = pcMemberTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(pcMemberTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                pcmC.setConnection(connB);
                pcmC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deletePCMActionPerformed

    private void jButton_updatePCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updatePCMActionPerformed
        stopEdit(pcMemberTable);
        int[] rows = pcMemberTable.getSelectedRows();
        ArrayList<PCM> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new PCM(
                    pcMemberTable.getValueAt(rows[i], 0).toString(),
                    pcMemberTable.getValueAt(rows[i], 1).toString(),
                    pcMemberTable.getValueAt(rows[i], 2).toString(),
                    pcMemberTable.getValueAt(rows[i], 3).toString(),
                    pcMemberTable.getValueAt(rows[i], 4).toString(),
                    pcMemberTable.getValueAt(rows[i], 5).toString(),
                    pcMemberTable.getValueAt(rows[i], 6).toString(),
                    pcMemberTable.getValueAt(rows[i], 7).toString()
            ));
        }

        pcmC.update(data);
    }//GEN-LAST:event_jButton_updatePCMActionPerformed

    private void jButton_insertPCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertPCMActionPerformed
        stopEdit(insertPCMemberTable);
        PCM t = new PCM(insertPCMemberTable.getValueAt(0, 0).toString(),
                insertPCMemberTable.getValueAt(0, 1).toString(),
                insertPCMemberTable.getValueAt(0, 2).toString(),
                insertPCMemberTable.getValueAt(0, 3).toString(),
                insertPCMemberTable.getValueAt(0, 4).toString(),
                insertPCMemberTable.getValueAt(0, 5).toString(),
                insertPCMemberTable.getValueAt(0, 6).toString());
        pcmC.insertRecord(t);

    }//GEN-LAST:event_jButton_insertPCMActionPerformed

    private void jButton_clearPCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearPCMActionPerformed
        stopEdit(insertPCMemberTable);
        clearTable(dtm_searchFieldPC_MEMEBR);
    }//GEN-LAST:event_jButton_clearPCMActionPerformed

    private void jButton_searchPCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchPCMActionPerformed
        stopEdit(insertPCMemberTable);
        String affiliation = insertPCMemberTable.getValueAt(0, 4).toString();
        pcmC.setConnection(connB);
        rset = pcmC.searchPCM(affiliation);
        populateTable(dtmTRACK);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchPCMActionPerformed

    private void jButton_viewReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewReviewActionPerformed
        stopEdit(reviewTable);
        viewReview();
    }//GEN-LAST:event_jButton_viewReviewActionPerformed

    private void viewReview() {
        reviewC.setConnection(connB);
        rset = reviewC.viewAll();
        populateTable(dtmREVIEW);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deleteReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteReviewActionPerformed
        stopEdit(reviewTable);
        int selectedRow = reviewTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(reviewTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                reviewC.setConnection(connB);
                reviewC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deleteReviewActionPerformed

    private void jButton_updateReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateReviewActionPerformed
        stopEdit(reviewTable);
        int[] rows = reviewTable.getSelectedRows();
        ArrayList<Review> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Review(
                    reviewTable.getValueAt(rows[i], 0).toString(),
                    reviewTable.getValueAt(rows[i], 1).toString(),
                    reviewTable.getValueAt(rows[i], 2).toString(),
                    reviewTable.getValueAt(rows[i], 3).toString(),
                    reviewTable.getValueAt(rows[i], 4).toString(),
                    reviewTable.getValueAt(rows[i], 5).toString(),
                    reviewTable.getValueAt(rows[i], 6).toString(),
                    reviewTable.getValueAt(rows[i], 7).toString(),
                    reviewTable.getValueAt(rows[i], 8).toString()
            ));
        }

        reviewC.update(data);
    }//GEN-LAST:event_jButton_updateReviewActionPerformed

    private void jButton_insertReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertReviewActionPerformed
        stopEdit(insertReviewTable);

        Review t = new Review(
                insertReviewTable.getValueAt(0, 0).toString(),
                insertReviewTable.getValueAt(0, 1).toString(),
                insertReviewTable.getValueAt(0, 2).toString(),
                insertReviewTable.getValueAt(0, 3).toString(),
                insertReviewTable.getValueAt(0, 4).toString(),
                insertReviewTable.getValueAt(0, 5).toString(),
                insertReviewTable.getValueAt(0, 6).toString(),
                insertReviewTable.getValueAt(0, 7).toString());
        reviewC.insertRecord(t);
    }//GEN-LAST:event_jButton_insertReviewActionPerformed

    private void jButton_clearReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearReviewActionPerformed
        stopEdit(insertReviewTable);
        clearTable(dtm_searchFieldREVIEW);
    }//GEN-LAST:event_jButton_clearReviewActionPerformed

    private void jButton_searchReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchReviewActionPerformed
        stopEdit(insertReviewTable);
        String recommendation = insertReviewTable.getValueAt(0, 3).toString();
        reviewC.setConnection(connB);
        rset = reviewC.searchReview(recommendation);
        populateTable(dtmREVIEW);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchReviewActionPerformed

    private void jButton_viewSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewSubmissionActionPerformed
        stopEdit(submissionTable);
        viewSubmission();
    }//GEN-LAST:event_jButton_viewSubmissionActionPerformed

    private void viewSubmission() {
        submissionC.setConnection(connB);
        rset = submissionC.viewAll();
        populateTable(dtmSUBMISSION);
//        getAllData();
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deleteSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteSubmissionActionPerformed
        stopEdit(submissionTable);
        int selectedRow = submissionTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(submissionTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                submissionC.setConnection(connB);
                submissionC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deleteSubmissionActionPerformed

    private void jButton_updateSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateSubmissionActionPerformed
        stopEdit(submissionTable);
        int[] rows = submissionTable.getSelectedRows();
        ArrayList<Submission> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Submission(
                    submissionTable.getValueAt(rows[i], 0).toString(),
                    submissionTable.getValueAt(rows[i], 1).toString(),
                    submissionTable.getValueAt(rows[i], 2).toString(),
                    submissionTable.getValueAt(rows[i], 3).toString(),
                    submissionTable.getValueAt(rows[i], 4).toString()
            ));
        }

        submissionC.update(data);
    }//GEN-LAST:event_jButton_updateSubmissionActionPerformed

    private void jButton_insertSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertSubmissionActionPerformed
        stopEdit(insertSubmissionTable);
        Submission t = new Submission(
                insertSubmissionTable.getValueAt(0, 0).toString(),
                insertSubmissionTable.getValueAt(0, 1).toString(),
                insertSubmissionTable.getValueAt(0, 2).toString(),
                insertSubmissionTable.getValueAt(0, 3).toString());
        submissionC.insertRecord(t);
    }//GEN-LAST:event_jButton_insertSubmissionActionPerformed

    private void jButton_clearSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearSubmissionActionPerformed
        stopEdit(insertSubmissionTable);
        clearTable(dtm_searchFieldSUBMISSION);
    }//GEN-LAST:event_jButton_clearSubmissionActionPerformed

    private void jButton_searchSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchSubmissionActionPerformed
        String conference = jTextField_searchSubByConf.getText();
        submissionC.setConnection(connB);
        rset = submissionC.searchSubmission(conference);
        populateTable(dtmSUBMISSION);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchSubmissionActionPerformed

    private void jButton_viewPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewPaperActionPerformed
        stopEdit(paperTable);
        viewPaper();
    }//GEN-LAST:event_jButton_viewPaperActionPerformed

    private void viewPaper() {
        paperC.setConnection(connB);
        rset = paperC.viewAll();
        populateTable(dtmPAPER);
//        getAllData();
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deletePaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deletePaperActionPerformed
        stopEdit(paperTable);
        int selectedRow = paperTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(paperTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                paperC.setConnection(connB);
                paperC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deletePaperActionPerformed

    private void jButton_updatePaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updatePaperActionPerformed
        stopEdit(paperTable);
        int[] rows = paperTable.getSelectedRows();
        ArrayList<Paper> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new Paper(
                    paperTable.getValueAt(rows[i], 0).toString(),
                    paperTable.getValueAt(rows[i], 1).toString(),
                    paperTable.getValueAt(rows[i], 2).toString(),
                    paperTable.getValueAt(rows[i], 3).toString(),
                    paperTable.getValueAt(rows[i], 4).toString(),
                    paperTable.getValueAt(rows[i], 5).toString()
            ));
        }

        paperC.update(data);
    }//GEN-LAST:event_jButton_updatePaperActionPerformed

    private void jButton_insertPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertPaperActionPerformed
        stopEdit(insertPaperTable);
        Paper t = new Paper(
                insertPaperTable.getValueAt(0, 0).toString(),
                insertPaperTable.getValueAt(0, 1).toString(),
                insertPaperTable.getValueAt(0, 2).toString(),
                insertPaperTable.getValueAt(0, 3).toString(),
                insertPaperTable.getValueAt(0, 4).toString());
        paperC.insertRecord(t);

    }//GEN-LAST:event_jButton_insertPaperActionPerformed

    private void jButton_clearPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearPaperActionPerformed
        stopEdit(insertPaperTable);
        clearTable(dtm_searchFieldPAPER);
    }//GEN-LAST:event_jButton_clearPaperActionPerformed

    private void jButton_searchPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchPaperActionPerformed
        String conference = jTextField_searchPaperByConf.getText();
        paperC.setConnection(connB);
        rset = paperC.searchPaper(conference);
        populateTable(dtmPAPER);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchPaperActionPerformed

    private void jButton_viewBestPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewBestPaperActionPerformed
        stopEdit(bestPaperTable);
        viewBestPaper();
    }//GEN-LAST:event_jButton_viewBestPaperActionPerformed
    private void viewBestPaper() {
        bestPaperC.setConnection(connB);
        rset = bestPaperC.viewAll();
        populateTable(dtmBESTPAPER);
//        getAllData();
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_deleteBestPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteBestPaperActionPerformed
        stopEdit(bestPaperTable);
        int selectedRow = bestPaperTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(null, "Not Selecting", "Exception", JOptionPane.OK_OPTION);
        } else {

            int chosenID = Integer.parseInt(bestPaperTable.getValueAt(selectedRow, 0).toString());
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete ID: " + chosenID + " data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                bestPaperC.setConnection(connB);
                bestPaperC.deleteRecord(chosenID);
//                jButton_viewActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButton_deleteBestPaperActionPerformed

    private void jButton_updateBestPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateBestPaperActionPerformed
        stopEdit(bestPaperTable);
        int[] rows = bestPaperTable.getSelectedRows();
        ArrayList<BestPaper> data = new ArrayList<>();

        for (int i = 0; i < rows.length; i++) {
            data.add(new BestPaper(
                    bestPaperTable.getValueAt(rows[i], 0).toString(),
                    bestPaperTable.getValueAt(rows[i], 1).toString(),
                    bestPaperTable.getValueAt(rows[i], 2).toString(),
                    bestPaperTable.getValueAt(rows[i], 3).toString(),
                    bestPaperTable.getValueAt(rows[i], 4).toString()
            ));
        }

        bestPaperC.update(data);
    }//GEN-LAST:event_jButton_updateBestPaperActionPerformed

    private void jButton_insertBestPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertBestPaperActionPerformed
        stopEdit(insertBestPaperTable);
        BestPaper t = new BestPaper(insertBestPaperTable.getValueAt(0, 0).toString(),
                insertBestPaperTable.getValueAt(0, 1).toString(),
                insertBestPaperTable.getValueAt(0, 2).toString(),
                insertBestPaperTable.getValueAt(0, 3).toString()
        );
        bestPaperC.insertRecord(t);
    }//GEN-LAST:event_jButton_insertBestPaperActionPerformed

    private void jButton_clearBpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearBpActionPerformed
        stopEdit(insertBestPaperTable);
        clearTable(dtm_searchFieldBESTPAPER);
    }//GEN-LAST:event_jButton_clearBpActionPerformed

    private void jButton_searchBestPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchBestPaperActionPerformed
        stopEdit(insertTrackTable);
        String conference = jTextField_searchBPByConf.getText();
        bestPaperC.setConnection(connB);
        rset = bestPaperC.searchBp(conference);
        populateTable(dtmBESTPAPER);
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_searchBestPaperActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("MACOS".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable authorTable;
    private javax.swing.JTable bestPaperTable;
    private javax.swing.JTable conferenceTable;
    private javax.swing.JTable insertAuthorTable;
    private javax.swing.JTable insertBestPaperTable;
    private javax.swing.JTable insertConferenceTable;
    private javax.swing.JTable insertPCMemberTable;
    private javax.swing.JTable insertPaperTable;
    private javax.swing.JTable insertReviewTable;
    private javax.swing.JTable insertSubmissionTable;
    private javax.swing.JTable insertTrackTable;
    private javax.swing.JButton jButton_clearAuthor;
    private javax.swing.JButton jButton_clearBp;
    private javax.swing.JButton jButton_clearConference;
    private javax.swing.JButton jButton_clearPCM;
    private javax.swing.JButton jButton_clearPaper;
    private javax.swing.JButton jButton_clearReview;
    private javax.swing.JButton jButton_clearSubmission;
    private javax.swing.JButton jButton_clearTrack;
    private javax.swing.JButton jButton_connect;
    private javax.swing.JButton jButton_deleteAuthor;
    private javax.swing.JButton jButton_deleteBestPaper;
    private javax.swing.JButton jButton_deleteConference;
    private javax.swing.JButton jButton_deletePCM;
    private javax.swing.JButton jButton_deletePaper;
    private javax.swing.JButton jButton_deleteReview;
    private javax.swing.JButton jButton_deleteSubmission;
    private javax.swing.JButton jButton_deleteTrack;
    private javax.swing.JButton jButton_disconnect;
    private javax.swing.JButton jButton_insertAuthor;
    private javax.swing.JButton jButton_insertBestPaper;
    private javax.swing.JButton jButton_insertConference;
    private javax.swing.JButton jButton_insertPCM;
    private javax.swing.JButton jButton_insertPaper;
    private javax.swing.JButton jButton_insertReview;
    private javax.swing.JButton jButton_insertSubmission;
    private javax.swing.JButton jButton_insertTrack;
    private javax.swing.JButton jButton_searchAuthor;
    private javax.swing.JButton jButton_searchBestPaper;
    private javax.swing.JButton jButton_searchConference;
    private javax.swing.JButton jButton_searchPCM;
    private javax.swing.JButton jButton_searchPaper;
    private javax.swing.JButton jButton_searchReview;
    private javax.swing.JButton jButton_searchSubmission;
    private javax.swing.JButton jButton_searchTrack;
    private javax.swing.JButton jButton_updateAuthor;
    private javax.swing.JButton jButton_updateBestPaper;
    private javax.swing.JButton jButton_updateConference;
    private javax.swing.JButton jButton_updatePCM;
    private javax.swing.JButton jButton_updatePaper;
    private javax.swing.JButton jButton_updateReview;
    private javax.swing.JButton jButton_updateSubmission;
    private javax.swing.JButton jButton_updateTrack;
    private javax.swing.JButton jButton_viewAuthor;
    private javax.swing.JButton jButton_viewBestPaper;
    private javax.swing.JButton jButton_viewConference;
    private javax.swing.JButton jButton_viewPCM;
    private javax.swing.JButton jButton_viewPaper;
    private javax.swing.JButton jButton_viewReview;
    private javax.swing.JButton jButton_viewSubmission;
    private javax.swing.JButton jButton_viewTRACK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_connectionState;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Author;
    private javax.swing.JPanel jPanel_BestPaper;
    private javax.swing.JPanel jPanel_Conference;
    private javax.swing.JPanel jPanel_PCMember;
    private javax.swing.JPanel jPanel_Paper;
    private javax.swing.JPanel jPanel_Review;
    private javax.swing.JPanel jPanel_Submission;
    private javax.swing.JPanel jPanel_Track;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane_allTables;
    private javax.swing.JTextField jTextField_searchBPByConf;
    private javax.swing.JTextField jTextField_searchPaperByConf;
    private javax.swing.JTextField jTextField_searchSubByConf;
    private javax.swing.JTable paperTable;
    private javax.swing.JTable pcMemberTable;
    private javax.swing.JTable reviewTable;
    private javax.swing.JTable submissionTable;
    private javax.swing.JTable trackTable;
    // End of variables declaration//GEN-END:variables
}
