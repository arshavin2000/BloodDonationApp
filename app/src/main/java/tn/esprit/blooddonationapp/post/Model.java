package tn.esprit.blooddonationapp.post;

public class Model {


    public int type;
    public int data;
    public String text;

    public Model(int type, String text, int data)
    {
        this.type=type;
        this.data=data;
        this.text=text;
    }
}