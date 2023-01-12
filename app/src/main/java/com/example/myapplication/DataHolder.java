package com.example.myapplication;

public class DataHolder {
    //design pattern to share arguments between fragments and activities
    //to save item use DataHolder.getInstance().setID(selectedItem); (ID already been saved in loginactivity)
    //to get student ID use DataHolder.getInstance().getID();
    private static DataHolder dataHolder = null;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }


    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
