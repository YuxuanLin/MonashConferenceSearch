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
public class PCM {
    
    private String pc_id;

    public PCM(String fname, String sname, String title, String position, String affliation, String email, String track_id) {
        this.fname = fname;
        this.sname = sname;
        this.title = title;
        this.position = position;
        this.affliation = affliation;
        this.email = email;
        this.track_id = track_id;
    }

    /**
     * Get the value of pc_id
     *
     * @return the value of pc_id
     */
    public String getPc_id() {
        return pc_id;
    }

    /**
     * Set the value of pc_id
     *
     * @param pc_id new value of pc_id
     */
    public void setPc_id(String pc_id) {
        this.pc_id = pc_id;
    }

    private String fname;

    /**
     * Get the value of fname
     *
     * @return the value of fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Set the value of fname
     *
     * @param fname new value of fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    private String sname;

    /**
     * Get the value of sname
     *
     * @return the value of sname
     */
    public String getSname() {
        return sname;
    }

    /**
     * Set the value of sname
     *
     * @param sname new value of sname
     */
    public void setSname(String sname) {
        this.sname = sname;
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

    private String position;

    /**
     * Get the value of position
     *
     * @return the value of position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set the value of position
     *
     * @param position new value of position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    private String affliation;

    /**
     * Get the value of affliation
     *
     * @return the value of affliation
     */
    public String getAffliation() {
        return affliation;
    }

    /**
     * Set the value of affliation
     *
     * @param affliation new value of affliation
     */
    public void setAffliation(String affliation) {
        this.affliation = affliation;
    }

    private String email;

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String track_id;

    /**
     * Get the value of track_id
     *
     * @return the value of track_id
     */
    public String getTrack_id() {
        return track_id;
    }

    /**
     * Set the value of track_id
     *
     * @param track_id new value of track_id
     */
    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public PCM(String pc_id, String fname, String sname, String title, String position, String affliation, String email, String track_id) {
        this.pc_id = pc_id;
        this.fname = fname;
        this.sname = sname;
        this.title = title;
        this.position = position;
        this.affliation = affliation;
        this.email = email;
        this.track_id = track_id;
    }

}
