package tn.esprit.blooddonationapp.model;

public class Receiver {

    private String type;
    private String position;
    private String number;
    private String time;

    public Receiver(String type, String position, String number, String time) {
        this.type = type;
        this.position = position;
        this.number = number;
        this.time = time;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
