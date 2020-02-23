package com.venkat.ktv;

import java.util.List;

public class coordModel {
    List<Double> latList;
    List<Double> lonList;

    public coordModel(List<Double> latList, List<Double> lonList) {
        this.latList = latList;
        this.lonList = lonList;
    }

    public List<Double> getLatList() {
        return latList;
    }

    public void setLatList(List<Double> latList) {
        this.latList = latList;
    }

    public List<Double> getLonList() {
        return lonList;
    }

    public void setLonList(List<Double> lonList) {
        this.lonList = lonList;
    }

    public coordModel(){

    }

}
