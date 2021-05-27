package com.example.myapplication;

public class NormalItem extends CarItem {

    public NormalItem(String label) {
        super(label);
    }


    @Override
    public int getType() {
        return TYPE_Normal;
    }

}
