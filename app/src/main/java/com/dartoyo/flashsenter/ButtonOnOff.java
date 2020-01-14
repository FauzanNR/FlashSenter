package com.dartoyo.flashsenter;

public class ButtonOnOff {
    public int select;

    public void setSelect(int select) {
        this.select = select;
    }

    public int getSelect() {
        return select;
    }
    int OnOff() {
        if (getSelect()  == 1) {
            setSelect(2);
        } else if (getSelect() == 2){
            setSelect(1);
        }
        return getSelect();
    }
}
