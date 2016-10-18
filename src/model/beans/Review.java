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
public class Review {
    
    private String review_id;

    public Review(String paper_id, String due_date, String review_date, String recommemdation, String comments, String pc_id_a, String pc_id_b, String pc_id_c) {
        this.paper_id = paper_id;
        this.due_date = due_date;
        this.review_date = review_date;
        this.recommemdation = recommemdation;
        this.comments = comments;
        this.pc_id_a = pc_id_a;
        this.pc_id_b = pc_id_b;
        this.pc_id_c = pc_id_c;
    }

    /**
     * Get the value of review_id
     *
     * @return the value of review_id
     */
    public String getReview_id() {
        return review_id;
    }

    /**
     * Set the value of review_id
     *
     * @param review_id new value of review_id
     */
    public void setReview_id(String review_id) {
        this.review_id = review_id;
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

    private String due_date;

    /**
     * Get the value of due_date
     *
     * @return the value of due_date
     */
    public String getDue_date() {
        return due_date;
    }

    /**
     * Set the value of due_date
     *
     * @param due_date new value of due_date
     */
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    private String review_date;

    /**
     * Get the value of review_date
     *
     * @return the value of review_date
     */
    public String getReview_date() {
        return review_date;
    }

    /**
     * Set the value of review_date
     *
     * @param review_date new value of review_date
     */
    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    private String recommemdation;

    /**
     * Get the value of recommemdation
     *
     * @return the value of recommemdation
     */
    public String getRecommemdation() {
        return recommemdation;
    }

    /**
     * Set the value of recommemdation
     *
     * @param recommemdation new value of recommemdation
     */
    public void setRecommemdation(String recommemdation) {
        this.recommemdation = recommemdation;
    }

    private String comments;

    /**
     * Get the value of comments
     *
     * @return the value of comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the value of comments
     *
     * @param comments new value of comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    private String pc_id_a;

    /**
     * Get the value of pc_id_a
     *
     * @return the value of pc_id_a
     */
    public String getPc_id_a() {
        return pc_id_a;
    }

    /**
     * Set the value of pc_id_a
     *
     * @param pc_id_a new value of pc_id_a
     */
    public void setPc_id_a(String pc_id_a) {
        this.pc_id_a = pc_id_a;
    }

    private String pc_id_b;

    /**
     * Get the value of pc_id_b
     *
     * @return the value of pc_id_b
     */
    public String getPc_id_b() {
        return pc_id_b;
    }

    /**
     * Set the value of pc_id_b
     *
     * @param pc_id_b new value of pc_id_b
     */
    public void setPc_id_b(String pc_id_b) {
        this.pc_id_b = pc_id_b;
    }

    private String pc_id_c;

    /**
     * Get the value of pc_id_c
     *
     * @return the value of pc_id_c
     */
    public String getPc_id_c() {
        return pc_id_c;
    }

    /**
     * Set the value of pc_id_c
     *
     * @param pc_id_c new value of pc_id_c
     */
    public void setPc_id_c(String pc_id_c) {
        this.pc_id_c = pc_id_c;
    }

    public Review(String review_id, String paper_id, String due_date, String review_date, String recommemdation, String comments, String pc_id_a, String pc_id_b, String pc_id_c) {
        this.review_id = review_id;
        this.paper_id = paper_id;
        this.due_date = due_date;
        this.review_date = review_date;
        this.recommemdation = recommemdation;
        this.comments = comments;
        this.pc_id_a = pc_id_a;
        this.pc_id_b = pc_id_b;
        this.pc_id_c = pc_id_c;
    }

}
