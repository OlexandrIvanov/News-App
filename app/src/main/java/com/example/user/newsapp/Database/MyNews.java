package com.example.user.newsapp.Database;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyNews extends RealmObject {

    @Required
    private String sourceName;

    @Required
    private String sourceDescription;

    @Required
    private String time;


    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
