package com.streaming.khendo;

public class ShowsModel {
    private String show;
    private String about;
    private String presenters;
    private String timestart;
    private String timestop;
    private String day;


    public ShowsModel(String show, String about, String presenters,  String timestart, String timestop, String day) {
        this.show = show;
        this.about= about;
        this.presenters = presenters;
        this.timestart = timestart;
        this.timestop = timestop;
        this.day = day;
    }

    public String getShow() {
        return show;
    }



    public String getAbout() {
        return about;
    }


    public String getPresenters() {
        return presenters;
    }


    public String getTimestart() {
        return timestart;
    }


    public String getTimestop() {
        return timestop;
    }


    public String getDay() {
        return day;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public ShowsModel() {
    }


}
