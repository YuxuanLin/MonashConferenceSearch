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
public class Submission {

    public Submission(String paper_id, String author_id, String authorOrder, String correspondingOrNot) {
        this.paper_id = paper_id;
        this.author_id = author_id;
        this.authorOrder = authorOrder;
        this.correspondingOrNot = correspondingOrNot;
    }
    
    private String submissionid;

    /**
     * Get the value of Submissionid
     *
     * @return the value of Submissionid
     */
    public String getSubmissionid() {
        return submissionid;
    }

    /**
     * Set the value of Submissionid
     *
     * @param Submissionid new value of Submissionid
     */
    public void setSubmissionid(String Submissionid) {
        this.submissionid = Submissionid;
    }

    private String paper_id;

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

    private String authorOrder;

    /**
     * Get the value of authorOrder
     *
     * @return the value of authorOrder
     */
    public String getAuthorOrder() {
        return authorOrder;
    }

    /**
     * Set the value of authorOrder
     *
     * @param authorOrder new value of authorOrder
     */
    public void setAuthorOrder(String authorOrder) {
        this.authorOrder = authorOrder;
    }

    private String correspondingOrNot;

    /**
     * Get the value of correspondingOrNot
     *
     * @return the value of correspondingOrNot
     */
    public String getCorrespondingOrNot() {
        return correspondingOrNot;
    }

    /**
     * Set the value of correspondingOrNot
     *
     * @param correspondingOrNot new value of correspondingOrNot
     */
    public void setCorrespondingOrNot(String correspondingOrNot) {
        this.correspondingOrNot = correspondingOrNot;
    }

    public Submission(String submissionid, String paper_id, String author_id, String authorOrder, String correspondingOrNot) {
        this.submissionid = submissionid;
        this.paper_id = paper_id;
        this.author_id = author_id;
        this.authorOrder = authorOrder;
        this.correspondingOrNot = correspondingOrNot;
    }

}
