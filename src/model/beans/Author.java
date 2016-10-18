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
public class Author {

    public Author(String fname, String sname, String affliation, String country, String email, String contact_number) {
        this.fname = fname;
        this.sname = sname;
        this.affliation = affliation;
        this.country = country;
        this.email = email;
        this.contact_number = contact_number;
    }

    
    private String author_id;

    /**
     * Get the value of author_id
     *
     * @return the value of author_id
     */
    public String getAuthor_id() {
        return author_id;
    }

    /**
     * Set the value of author_id
     *
     * @param author_id new value of author_id
     */
    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
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

    private String country;

    /**
     * Get the value of country
     *
     * @return the value of country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the value of country
     *
     * @param country new value of country
     */
    public void setCountry(String country) {
        this.country = country;
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

    private String contact_number;

    /**
     * Get the value of contact_number
     *
     * @return the value of contact_number
     */
    public String getContact_number() {
        return contact_number;
    }

    /**
     * Set the value of contact_number
     *
     * @param contact_number new value of contact_number
     */
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Author(String author_id, String fname, String sname, String affliation, String country, String email, String contact_number) {
        this.author_id = author_id;
        this.fname = fname;
        this.sname = sname;
        this.affliation = affliation;
        this.country = country;
        this.email = email;
        this.contact_number = contact_number;
    }

    
}
