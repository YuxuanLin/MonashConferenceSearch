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
public class Track {

    public Track(String TRACK_ID,String TRACK_NAME, String DESCRIPTION, String CONFERENCE_ID) {
        this.TRACK_NAME = TRACK_NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.CONFERENCE_ID = CONFERENCE_ID;
        this.TRACK_ID = TRACK_ID;
    }

    public Track(String TRACK_NAME, String DESCRIPTION, String CONFERENCE_ID) {
        this.TRACK_NAME = TRACK_NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.CONFERENCE_ID = CONFERENCE_ID;
    }
    
    
    
    
    private String TRACK_NAME;

    /**
     * Get the value of TRACK_NAME
     *
     * @return the value of TRACK_NAME
     */
    public String getTRACK_NAME() {
        return TRACK_NAME;
    }

    /**
     * Set the value of TRACK_NAME
     *
     * @param TRACK_NAME new value of TRACK_NAME
     */
    public void setTRACK_NAME(String TRACK_NAME) {
        this.TRACK_NAME = TRACK_NAME;
    }

    private String DESCRIPTION;

    /**
     * Get the value of DESCRIPTION
     *
     * @return the value of DESCRIPTION
     */
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * Set the value of DESCRIPTION
     *
     * @param DESCRIPTION new value of DESCRIPTION
     */
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    private String CONFERENCE_ID;

    /**
     * Get the value of CONFERENCE_ID
     *
     * @return the value of CONFERENCE_ID
     */
    public String getCONFERENCE_ID() {
        return CONFERENCE_ID;
    }

    /**
     * Set the value of CONFERENCE_ID
     *
     * @param CONFERENCE_ID new value of CONFERENCE_ID
     */
    public void setCONFERENCE_ID(String CONFERENCE_ID) {
        this.CONFERENCE_ID = CONFERENCE_ID;
    }

    private String TRACK_ID;

    /**
     * Get the value of TRACK_ID
     *
     * @return the value of TRACK_ID
     */
    public String getTRACK_ID() {
        return TRACK_ID;
    }

    /**
     * Set the value of TRACK_ID
     *
     * @param TRACK_ID new value of TRACK_ID
     */
    public void setTRACK_ID(String TRACK_ID) {
        this.TRACK_ID = TRACK_ID;
    }

}
