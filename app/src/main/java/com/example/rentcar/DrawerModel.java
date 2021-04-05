package com.example.rentcar;

public class DrawerModel {

    public int icon;
    public String name;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
//
//    // Constructor.
//    public DrawerModel(int icon, String name) {
//
//        this.icon = icon;
//        this.name = name;
//    }
}
