package tn.esprit.blooddonationapp.model;

import java.time.LocalDateTime;

public class Post {
    String postImage;
    String postText;
    String username;
    String timePost;
    int numberLikes;
    int NumberComments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Post(String postImage, String postText,String username, String timePost, int numberLikes, int numberComments) {
        this.postImage = postImage;
        this.postText = postText;
        this.timePost = timePost;
        this.numberLikes = numberLikes;
        this.NumberComments = numberComments;
        this.username=username;

    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public int getNumberLikes() {
        return numberLikes;
    }

    public void setNumberLikes(int numberLikes) {
        this.numberLikes = numberLikes;
    }

    public int getNumberComments() {
        return NumberComments;
    }

    public void setNumberComments(int numberComments) {
        NumberComments = numberComments;
    }
}
