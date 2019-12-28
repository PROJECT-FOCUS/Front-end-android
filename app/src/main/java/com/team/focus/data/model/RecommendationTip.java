package com.team.focus.data.model;

import android.graphics.drawable.Drawable;

public class RecommendationTip {

    private String tip;
    private Drawable type;


    public RecommendationTip(String tip, Drawable type) {
        this.tip = tip;
        this.type = type;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Drawable getType() {
        return type;
    }

    public void setType(Drawable type) {
        this.type = type;
    }
}
