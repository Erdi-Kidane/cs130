package com.example.spinit;

import java.util.ArrayList;

public class PrefList {
    private ArrayList<String> choices;
    PrefList()
    {
        choices = new ArrayList<String>();
    }

    public void createList(ArrayList<String> list)
    {
        this.choices = list;
    }

    public ArrayList<String> getPrefList()
    {
        return this.choices;
    }

}
