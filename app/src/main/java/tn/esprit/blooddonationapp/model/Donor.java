package tn.esprit.blooddonationapp.model;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Donor implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String bloodGroup;
    private String urlImage;
    private String number;
    private int answer;
    private int request;
    private int rate;

    public Donor() {
    }


    public Donor(String id, String firstName, String lastName, String email, String gender, String bloodGroup, String urlImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.urlImage = urlImage;

    }


    public Donor(String firstName, String lastName, String email, String gender, String bloodGroup , String urlImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donor)) return false;
        Donor donor = (Donor) o;
        return id == donor.id;
    }


    @Override
    public String toString() {
        return "Donor{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", number='" + number + '\'' +
                ", answer=" + answer +
                ", request=" + request +
                ", rate=" + rate +
                '}';
    }
}
