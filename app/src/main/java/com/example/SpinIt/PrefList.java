package com.example.SpinIt;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
public class PrefList implements Parcelable {
    /**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStringList(choices);
    }

    public static final Parcelable.Creator<PrefList> CREATOR = new Parcelable.Creator<PrefList>() {
        public PrefList createFromParcel(Parcel in) {
            return new PrefList(in);
        }

        public PrefList[] newArray(int size) {
            return new PrefList[size];
        }
    };

    private PrefList(Parcel in) {
        in.readStringList(choices);
    }
    /************************************************/
    private ArrayList<String> choices = new ArrayList<>();
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

