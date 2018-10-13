package com.moratuwa.exhibition.Entities;

/**
 * Created by sajeer_lap on 4/25/2017.
 */
public class Rating {

    String email;
    String comment;
    Float rating;

    public Rating() {
    }

    public Rating( String email, String comment, Float rating) {
        this.email = email;
        this.comment = comment;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
