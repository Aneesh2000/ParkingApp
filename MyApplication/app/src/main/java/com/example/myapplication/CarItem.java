package com.example.myapplication;

import android.widget.ImageView;

public abstract class CarItem {

    public static final int TYPE_Normal = 0;
    //public static final int TYPE_Empty = 1;
    private String label;


    public CarItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    abstract public int getType();
}
