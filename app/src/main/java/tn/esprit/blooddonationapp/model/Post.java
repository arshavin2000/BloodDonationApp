package tn.esprit.blooddonationapp.model;

import java.util.ArrayList;

public class Post {
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public static final int AUDIO_TYPE=2;

    public int type;
    private String postImage;
    private String postText;
    private User user;
    private String timePost;
    private int numberLikes;
    private int NumberComments;
    private ArrayList<Comment> comments = new ArrayList<>();



    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUsername(User user) {
        this.user = user;
    }

    public Post(int type, String postImage, String postText,User user, String timePost, int numberLikes, int numberComments) {
        this.postImage = postImage;
        this.postText = postText;
        this.timePost = timePost;
        this.numberLikes = numberLikes;
        this.NumberComments = numberComments;
        this.user=user;
        this.type=1;


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
