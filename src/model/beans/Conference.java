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
public class Conference {

    public Conference(String name, String startingDate, String endingDate, String country, String city, String venue, String contactMail, String year) {
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.country = country;
        this.city = city;
        this.venue = venue;
        this.contactMail = contactMail;
        this.year = year;
    }

    
    
    
    
    public Conference(){
        
    }
    public Conference(String conferenceID, String name, String startingDate, String endingDate,
            String country, String city, String venue, String contactEmail, String year) {
        this.conferenceID = conferenceID;
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.country = country;
        this.city = city;
        this.venue = venue;
        this.contactMail = contactEmail;
        this.year = year;
    }

    private String conferenceID;

    /**
     * Get the value of Conference_ID
     *
     * @return the value of Conference_ID
     */
    public String getConference_ID() {
        return conferenceID;
    }

    /**
     * Set the value of Conference_ID
     *
     * @param Conference_ID new value of Conference_ID
     */
    public void setConference_ID(String Conference_ID) {
        this.conferenceID = Conference_ID;
    }

    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String startingDate;

    /**
     * Get the value of startingDate
     *
     * @return the value of startingDate
     */
    public String getStartingDate() {
        return startingDate;
    }

    /**
     * Set the value of startingDate
     *
     * @param startingDate new value of startingDate
     */
    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    private String endingDate;

    /**
     * Get the value of endingDate
     *
     * @return the value of endingDate
     */
    public String getEndingDate() {
        return endingDate;
    }

    /**
     * Set the value of endingDate
     *
     * @param endingDate new value of endingDate
     */
    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
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

    private String city;

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    private String venue;

    /**
     * Get the value of venue
     *
     * @return the value of venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Set the value of venue
     *
     * @param venue new value of venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    private String contactMail;

    /**
     * Get the value of contactMail
     *
     * @return the value of contactMail
     */
    public String getContactMail() {
        return contactMail;
    }

    /**
     * Set the value of contactMail
     *
     * @param contactMail new value of contactMail
     */
    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    private String year;

    /**
     * Get the value of year
     *
     * @return the value of year
     */
    public String getYear() {
        return year;
    }

    /**
     * Set the value of year
     *
     * @param year new value of year
     */
    public void setYear(String year) {
        this.year = year;
    }

}
