package tn.esprit.blooddonationapp.model;

public class User {

    private String id_user;
    private String firstname;
    private String lastname;

    public User(String id_user, String firstname, String lastname) {
        this.id_user = id_user;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
