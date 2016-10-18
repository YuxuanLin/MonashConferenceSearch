/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.beans;

/**
 *
 * @author linyuxuan
 */
public class Paper {
    
    private String paper_id;

    public Paper(String abs, String paperType, String submissionDate, String trackId, String title) {
        this.abs = abs;
        this.paperType = paperType;
        this.submissionDate = submissionDate;
        this.trackId = trackId;
        this.title = title;
    }

    
    /**
     * Get the value of paper_id
     *
     * @return the value of paper_id
     */
    public String getPaper_id() {
        return paper_id;
    }

    /**
     * Set the value of paper_id
     *
     * @param paper_id new value of paper_id
     */
    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    private String abs;

    /**
     * Get the value of abs
     *
     * @return the value of abs
     */
    public String getAbs() {
        return abs;
    }

    /**
     * Set the value of abs
     *
     * @param abs new value of abs
     */
    public void setAbs(String abs) {
        this.abs = abs;
    }

    private String paperType;

    /**
     * Get the value of paperType
     *
     * @return the value of paperType
     */
    public String getPaperType() {
        return paperType;
    }

    /**
     * Set the value of paperType
     *
     * @param paperType new value of paperType
     */
    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    private String submissionDate;

    /**
     * Get the value of submissionDate
     *
     * @return the value of submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Set the value of submissionDate
     *
     * @param submissionDate new value of submissionDate
     */
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    private String trackId;

    /**
     * Get the value of trackId
     *
     * @return the value of trackId
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * Set the value of trackId
     *
     * @param trackId new value of trackId
     */
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

        private String title;

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Paper(String paper_id, String abs, String paperType, String submissionDate, String trackId, String title) {
        this.paper_id = paper_id;
        this.abs = abs;
        this.paperType = paperType;
        this.submissionDate = submissionDate;
        this.trackId = trackId;
        this.title = title;
    }

}
