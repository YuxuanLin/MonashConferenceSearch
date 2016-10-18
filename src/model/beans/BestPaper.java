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
public class BestPaper {
    
    private String awardID;

    public BestPaper(String trackId, String paperID, String awardPrice, String paperTitle) {
        this.trackId = trackId;
        this.paperID = paperID;
        this.awardPrice = awardPrice;
        this.paperTitle = paperTitle;
    }

    /**
     * Get the value of awardID
     *
     * @return the value of awardID
     */
    public String getAwardID() {
        return awardID;
    }

    /**
     * Set the value of awardID
     *
     * @param awardID new value of awardID
     */
    public void setAwardID(String awardID) {
        this.awardID = awardID;
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

    private String paperID;

    /**
     * Get the value of paperID
     *
     * @return the value of paperID
     */
    public String getPaperID() {
        return paperID;
    }

    /**
     * Set the value of paperID
     *
     * @param paperID new value of paperID
     */
    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

        private String awardPrice;

    /**
     * Get the value of awardPrice
     *
     * @return the value of awardPrice
     */
    public String getAwardPrice() {
        return awardPrice;
    }

    /**
     * Set the value of awardPrice
     *
     * @param awardPrice new value of awardPrice
     */
    public void setAwardPrice(String awardPrice) {
        this.awardPrice = awardPrice;
    }

    private String paperTitle;

    /**
     * Get the value of paperTitle
     *
     * @return the value of paperTitle
     */
    public String getPaperTitle() {
        return paperTitle;
    }

    /**
     * Set the value of paperTitle
     *
     * @param paperTitle new value of paperTitle
     */
    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public BestPaper(String awardID, String trackId, String paperID, String awardPrice, String paperTitle) {
        this.awardID = awardID;
        this.trackId = trackId;
        this.paperID = paperID;
        this.awardPrice = awardPrice;
        this.paperTitle = paperTitle;
    }

}
