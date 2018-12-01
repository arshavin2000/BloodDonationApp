package tn.esprit.blooddonationapp.model;

public class Comment {

    private String username;
    private String textComment;
    private String time;

    public Comment(String username, String textComment, String time) {
        this.username = username;
        this.textComment = textComment;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
