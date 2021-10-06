package project2;

import java.util.ArrayList;

//ReviewInputs is a class that sets up the data structure to store the review type JSON data.
public class ReviewInputs {
    private String reviewerID;
    private String asin;
    private String reviewerName;
    private ArrayList<Integer> helpful;
    private String reviewText;
    private String overall;
    private String summary;
    private String unixReviewTime;
    private String reviewTime;

    // Constructor for ReviewInputs with all the fields in the Reviews JSON file.
    public ReviewInputs(String reviewerID, String asin, String reviewerName, ArrayList helpful, String reviewText, String overall, String summary, String unixReviewTime, String reviewTime) {
        this.reviewerID = reviewerID;
        this.asin = asin;
        this.reviewerName = reviewerName;
        this.helpful = helpful;
        this.reviewText = reviewText;
        this.overall = overall;
        this.summary = summary;
        this.unixReviewTime = unixReviewTime;
        this.reviewTime = reviewTime;
    }

    // Getter method for the reviewID field.
    public String getReviewerID() {
        return reviewerID;
    }

    // Setter method for the reviewID field.
    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    // Getter method for the asin field.
    public String getAsin() {
        return asin;
    }

    // Setter method for the asin field.
    public void setAsin(String asin) {
        this.asin = asin;
    }

    // Getter method for the reviewerName field.
    public String getReviewerName() {
        return reviewerName;
    }

    // Setter method for the reviewerName field.
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    // Getter method for the helpful field.
    public ArrayList<Integer> getHelpful() {
        return helpful;
    }

    // Setter method for the helpful field.
    public void setHelpful(ArrayList helpful) {
        this.helpful = helpful;
    }

    // Getter method for the reviewText field.
    public String getReviewText() {
        return reviewText;
    }

    // Setter method for the reviewText field.
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    // Getter method for the overall field.
    public String getOverall() {
        return overall;
    }

    // Setter method for the overall field.
    public void setOverall(String overall) {
        this.overall = overall;
    }

    // Getter method for the summary field.
    public String getSummary() {
        return summary;
    }

    // Setter method for the summary field.
    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Getter method for the unixReviewTime field.
    public String getUnixReviewTime() {
        return unixReviewTime;
    }

    // Setter method for the unixReviewTime field.
    public void setUnixReviewTime(String unixReviewTime) {
        this.unixReviewTime = unixReviewTime;
    }

    // Getter method for the reviewTime field.
    public String getReviewTime() {
        return reviewTime;
    }

    // Setter method for the reviewTime field.
    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    // To string method for all the fields in the Reviews JSON file.
    @Override
    public String toString() {
        return "JSONInputs{" +
            "reviewerID='" + reviewerID + '\'' +
            ", asin='" + asin + '\'' +
            ", reviewerName='" + reviewerName + '\'' +
            ", helpful='" + helpful + '\'' +
            ", reviewText='" + reviewText + '\'' +
            ", overall='" + overall + '\'' +
            ", summary='" + summary + '\'' +
            ", unixReviewTime='" + unixReviewTime + '\'' +
            ", reviewTime='" + reviewTime + '\'' +
            '}';
    }
}

