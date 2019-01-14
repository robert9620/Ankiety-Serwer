package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SurveyModel implements Serializable {
    private int id;
    private String name;
    private boolean completed = false;
    List<UserModel> completedBy;
    private Date completedDate;

    public SurveyModel(){
        this.completedBy = new LinkedList<UserModel>();
    }

    public SurveyModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setCompletedDate(String completedDate) {
        try {
            this.completedDate = new SimpleDateFormat("yyyy-mm-dd").parse(completedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void addCompletedBy(UserModel user){
        this.completedBy.add(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] toTable(){
        String[] surveyInfo = new String[3];
        surveyInfo[0] = "1";
        surveyInfo[1] = String.valueOf(this.name);
        return surveyInfo;
    }
}
